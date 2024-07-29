package com.chicksgold.WaterBucket.controller;

import com.chicksgold.WaterBucket.request.RiddleRequest;
import com.chicksgold.WaterBucket.response.RiddleResponse;
import com.chicksgold.WaterBucket.service.WaterBucketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/water-bucket")
public class WaterBucketController {

    @Autowired
    private WaterBucketService waterBucketService;

    @Operation(summary = "Generate solution for Water Bucket Riddle", tags = { "Water Bucket" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get solution successfully"),
            @ApiResponse(responseCode = "409", description = "No possible solution")  })
    @PostMapping()
    public ResponseEntity<RiddleResponse> getSolutionRiddle(@Valid @RequestBody RiddleRequest riddleRequest){
            RiddleResponse riddleResponse = waterBucketService.getSolution(riddleRequest);
            return ResponseEntity.ok(riddleResponse);
    }
}
