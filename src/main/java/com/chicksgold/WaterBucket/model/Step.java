package com.chicksgold.WaterBucket.model;

import com.chicksgold.WaterBucket.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class Step {

    private int step;
    private int bucketX;
    private int bucketY;
    private String action;
    private String status;

    public Step(int step, int bucketX, int bucketY, String action) {
        this.step = step;
        this.bucketX = bucketX;
        this.bucketY = bucketY;
        this.action = action;
    }

}
