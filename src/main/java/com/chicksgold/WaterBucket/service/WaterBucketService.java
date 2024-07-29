package com.chicksgold.WaterBucket.service;

import com.chicksgold.WaterBucket.enumeration.Action;
import com.chicksgold.WaterBucket.enumeration.Status;
import com.chicksgold.WaterBucket.exception.NoSolutionPossibleException;
import com.chicksgold.WaterBucket.model.Bucket;
import com.chicksgold.WaterBucket.request.RiddleRequest;
import com.chicksgold.WaterBucket.model.Step;
import com.chicksgold.WaterBucket.response.RiddleResponse;
import com.chicksgold.WaterBucket.util.RiddleRequestValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WaterBucketService {


    Bucket bucketX;
    Bucket bucketY;

    public final RiddleResponse getSolution(RiddleRequest riddleRequest) throws IllegalArgumentException{
        try {
            RiddleRequestValidator.validate(riddleRequest);

//        RiddleResponse response1 = evaluateSolutions(riddleRequest);

            RiddleResponse response = new RiddleResponse();

            response.setSolution(evaluateSolutions(riddleRequest));
            return response;
        } catch (IllegalArgumentException e) {
            throw new NoSolutionPossibleException(e.getMessage());
        }
    }

    private final List<Step> evaluateSolutions(RiddleRequest riddleRequest) {
        List<Step> solution1 = getStepsStartingWithX(
            riddleRequest.getBucketX(),
            riddleRequest.getBucketY(),
            riddleRequest.getAmountWanted(),
            true
        );
        List<Step> solution2 = getStepsStartingWithX(
            riddleRequest.getBucketY(),
            riddleRequest.getBucketX(),
            riddleRequest.getAmountWanted(),
            false
        );

        if ( solution1.size() < solution2.size()) {
            return solution1;
        }
        return solution2;
    }

    public final List<Step> getStepsStartingWithX(int volumeX, int volumeY, int amountZ, boolean isFirstX) {

        bucketX = new Bucket(volumeX);
        bucketY = new Bucket(volumeY);

        List<Step> steps = new ArrayList<>();      int index = 1;
        bucketX.fill();
        bucketY.empty();

        addSteps(steps, index, Action.FILL, isFirstX, isSolved(amountZ));

        while (bucketX.getCurrentVolume() != amountZ && bucketY.getCurrentVolume() != amountZ) {

            int temp = Math.min(bucketX.getCurrentVolume(), bucketY.getCapacity() - bucketY.getCurrentVolume());

            bucketY.add(temp);
            bucketX.remove(temp);
            index++;

            addSteps(steps, index, Action.TRANSFER, isFirstX, isSolved(amountZ));

            if  ( isSolved(amountZ)) break;

            if ( bucketX.isEmpty()) {
                bucketX.fill();
                index ++;
                addSteps(steps, index, Action.FILL, isFirstX, isSolved(amountZ));
            }

            if (bucketY.isFull()){
                bucketY.empty();
                index ++;
                addSteps(steps, index, Action.EMPTY, isFirstX, isSolved(amountZ));
            }
        }
        return steps;
    }

    private boolean isSolved(int amountWanted) {
        return bucketX.hasVolume(amountWanted) || bucketY.hasVolume(amountWanted);
    }

    private void addSteps(List<Step> steps, int index, Action action, boolean isFirstX, boolean isSolved) {
        String actionMessage = action.getMessage(isFirstX);
        Status status = isSolved ? Status.SOLVED : Status.IN_PROGRESS;
        if (isFirstX) {
            steps.add(new Step(index, bucketX.getCurrentVolume(), bucketY.getCurrentVolume(), actionMessage, status.getMessage()));
        } else {
            steps.add(new Step(index, bucketY.getCurrentVolume(), bucketX.getCurrentVolume(), actionMessage, status.getMessage()));
        }
    }
}
