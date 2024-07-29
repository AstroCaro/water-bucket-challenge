package com.chicksgold.WaterBucket.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RiddleRequest {

    @NotNull(message = "Bucket X cannot be null")
    @Min(value = 1, message = "Bucket X must be a positive integer")
    private final Integer bucketX;

    @NotNull(message = "Bucket Y cannot be null")
    @Min(value = 1, message = "Bucket Y must be a positive integer")
    private final Integer bucketY;

    @NotNull(message = "Amount Wanted cannot be null")
    @Min(value = 1, message = "Amount Wanted must be a positive integer")
    private final Integer amountWanted;



}
