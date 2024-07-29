package com.chicksgold.WaterBucket.util;
import com.chicksgold.WaterBucket.request.RiddleRequest;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RiddleRequestValidator {

    public static void validate(RiddleRequest riddleRequest) throws IllegalArgumentException {

        if (riddleRequest.getBucketX() < 0 || riddleRequest.getBucketY() < 0 || riddleRequest.getAmountWanted() < 0) {
            throw new IllegalArgumentException("Values cannot be negative.");
        }
        if (!isAmountWantedValid(riddleRequest)) {
            throw new IllegalArgumentException("Amount wanted is not a multiple of GCD.");
        }
        if (riddleRequest.getBucketX() < riddleRequest.getAmountWanted() && riddleRequest.getBucketY() < riddleRequest.getAmountWanted()) {
            throw new IllegalArgumentException("Amount wanted is greater than both bucket volumes.");
        }
    }

    private static boolean isAmountWantedValid(RiddleRequest riddleRequest) {
        int gdc = GreatestCommonDivisor.calculate(riddleRequest.getBucketX(), riddleRequest.getBucketY());
        return riddleRequest.getAmountWanted() % gdc == 0;
    }
}
