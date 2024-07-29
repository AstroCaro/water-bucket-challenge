package com.chicksgold.WaterBucket.enumeration;

public enum Action {
    FILL("Fill bucket %s"),
    EMPTY("Empty bucket %s"),
    TRANSFER("Transfer from bucket %s to %s");

    private final String message;

    Action(String message) {
        this.message = message;
    }

    public String getMessage(boolean isFirstX) {
        return switch (this) {
            case FILL -> String.format(message, isFirstX ? "X" : "Y");
            case EMPTY -> String.format(message, isFirstX ? "Y" : "X");
            case TRANSFER -> String.format(message, isFirstX ? "X" : "Y", isFirstX ? "Y" : "X");
            default -> throw new IllegalArgumentException("Invalid action");
        };
    }
}