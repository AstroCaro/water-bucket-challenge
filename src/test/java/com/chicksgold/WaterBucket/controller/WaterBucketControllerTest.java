package com.chicksgold.WaterBucket.controller;

import static org.assertj.core.api.Assertions.assertThat;
import com.chicksgold.WaterBucket.enumeration.Action;
import com.chicksgold.WaterBucket.enumeration.Status;
import com.chicksgold.WaterBucket.exception.NoSolutionPossibleException;
import com.chicksgold.WaterBucket.model.Step;
import com.chicksgold.WaterBucket.request.RiddleRequest;
import com.chicksgold.WaterBucket.response.RiddleResponse;
import com.chicksgold.WaterBucket.service.WaterBucketService;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class WaterBucketControllerTest {


    @InjectMocks
    private WaterBucketController waterBucketController;

    @Mock
    private WaterBucketService waterBucketService;

    @Test
    public void testGetSolutionRiddle_Success() throws Exception {

        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        RiddleRequest riddleRequest = new RiddleRequest(3, 5, 2);
        RiddleResponse response = new RiddleResponse(Arrays.asList(
                new Step(1, 0, 5, "Fill bucket Y", "In progress" ),
                new Step(2, 3, 2, "Transfer from bucket Y to X", "Solved")
        ));

        when(waterBucketService.getSolution(riddleRequest)).thenReturn(response);
        ResponseEntity<RiddleResponse> responseEntity = waterBucketController.getSolutionRiddle(riddleRequest);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        RiddleResponse responseBody = responseEntity.getBody();
        assertThat(responseBody).isNotNull();

        assertThat(responseBody.getSolution()).hasSize(2);

        Step firstStep = responseBody.getSolution().get(0);
        assertThat(firstStep.getStep()).isEqualTo(1);
        assertThat(firstStep.getBucketX()).isEqualTo(0);
        assertThat(firstStep.getBucketY()).isEqualTo(5);
        assertThat(firstStep.getAction()).isEqualTo("Fill bucket Y");
        assertThat(firstStep.getStatus()).isEqualTo("In progress");

        Step secondStep = responseBody.getSolution().get(1);
        assertThat(secondStep.getStep()).isEqualTo(2);
        assertThat(secondStep.getBucketX()).isEqualTo(3);
        assertThat(secondStep.getBucketY()).isEqualTo(2);
        assertThat(secondStep.getAction()).isEqualTo("Transfer from bucket Y to X");
        assertThat(secondStep.getStatus()).isEqualTo("Solved");
    }


    @Test
    public void testGetSolutionRiddle_Conflict() {
        RiddleRequest riddleRequest = new RiddleRequest(2, 6, 3);

        when(waterBucketService.getSolution(riddleRequest)).thenThrow(new NoSolutionPossibleException("No possible solution"));

        try {
            ResponseEntity<RiddleResponse> responseEntity = waterBucketController.getSolutionRiddle(riddleRequest);
        } catch (NoSolutionPossibleException e) {
            assertThat(e.getMessage()).isEqualTo("No possible solution");

        }
    }

    @Test
    public void testGetSolutionRiddle_ValidationError() {
        RiddleRequest riddleRequest = new RiddleRequest(-1, 5, -2);

        doThrow(new ConstraintViolationException("Invalid value", null)).when(waterBucketService).getSolution(riddleRequest);

        try {
            ResponseEntity<RiddleResponse> responseEntity = waterBucketController.getSolutionRiddle(riddleRequest);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ConstraintViolationException.class);
            assertThat(e.getMessage()).contains("Invalid value");
        }
    }

    @Test
    public void testGetSolutionRiddle_ZeroValue() {
        RiddleRequest riddleRequest = new RiddleRequest(0, 5, 2);

        doThrow(new ConstraintViolationException("Bucket X must be a positive integer", null)).when(waterBucketService).getSolution(riddleRequest);

        try {
            ResponseEntity<RiddleResponse> responseEntity = waterBucketController.getSolutionRiddle(riddleRequest);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ConstraintViolationException.class);
            assertThat(e.getMessage()).contains("Bucket X must be a positive integer");
        }
    }

}
