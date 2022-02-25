package com.bookxchange.customExceptions;

public class InvalidRatingException extends RuntimeException {

    public InvalidRatingException() {
        super();
    }

    public InvalidRatingException(String message) {
        super(message);
    }

    public InvalidRatingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRatingException(Throwable cause) {
        super(cause);
    }
}
