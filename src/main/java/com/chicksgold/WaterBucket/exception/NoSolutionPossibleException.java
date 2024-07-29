package com.chicksgold.WaterBucket.exception;

public class NoSolutionPossibleException extends RuntimeException {

    public NoSolutionPossibleException() {
        super();
    }

    public NoSolutionPossibleException(final String message) {
        super(message);
    }

    public NoSolutionPossibleException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
