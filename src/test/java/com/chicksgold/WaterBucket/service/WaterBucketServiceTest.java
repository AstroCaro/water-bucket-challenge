package com.chicksgold.WaterBucket.service;

import com.chicksgold.WaterBucket.exception.NoSolutionPossibleException;
import org.springframework.boot.test.context.SpringBootTest;
import com.chicksgold.WaterBucket.model.Step;
import com.chicksgold.WaterBucket.request.RiddleRequest;
import com.chicksgold.WaterBucket.response.RiddleResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;


@SpringBootTest
public class WaterBucketServiceTest {

    @InjectMocks
    private WaterBucketService waterBucketService;

    @Test
    public void testGetSolution_ValidRequest() {
        RiddleRequest request = new RiddleRequest(3, 5, 4);

        RiddleResponse response = waterBucketService.getSolution(request);

        assertThat(response, is(notNullValue()));
        List<Step> steps = response.getSolution();
        assertThat(steps, is(not(empty())));
        assertThat(steps.getLast().getBucketX(), is(3));
        assertThat(steps.getLast().getBucketY(), is(4));
    }

    @Test
    public void testGetSolution_AnotherValidRequest() {
        RiddleRequest request = new RiddleRequest(2, 100, 96);

        RiddleResponse response = waterBucketService.getSolution(request);

        assertThat(response, is(notNullValue()));
        List<Step> steps = response.getSolution();
        assertThat(steps, is(not(empty())));
        assertThat(steps.getLast().getBucketX(), is(2));
        assertThat(steps.getLast().getBucketY(), is(96));
    }

    @Test
    public void testGetSolution_NoValidAmount() {
        RiddleRequest request = new RiddleRequest(2, 4, 5);
        assertThatExceptionOfType(NoSolutionPossibleException.class)
                .isThrownBy(()->waterBucketService.getSolution(request));
    }

    @Test
    public void testGetSolution_GreaterAmount() {
        RiddleRequest request = new RiddleRequest(5, 2, 6);
        assertThatExceptionOfType(NoSolutionPossibleException.class)
                .isThrownBy(()->waterBucketService.getSolution(request));
    }

    @Test
    public void testGetSolution_NegativeValues() {
        RiddleRequest request = new RiddleRequest(-5, 2, 6);
        assertThatExceptionOfType(NoSolutionPossibleException.class)
                .isThrownBy(()->waterBucketService.getSolution(request));
    }
}