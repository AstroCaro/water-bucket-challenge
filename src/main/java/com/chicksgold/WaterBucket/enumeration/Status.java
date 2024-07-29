package com.chicksgold.WaterBucket.enumeration;

import lombok.Getter;

@Getter
public enum Status {

    SOLVED("Solved"),
    IN_PROGRESS("In progress");

    private final String message;

    Status(String message) {
        this.message = message;
    }
}
