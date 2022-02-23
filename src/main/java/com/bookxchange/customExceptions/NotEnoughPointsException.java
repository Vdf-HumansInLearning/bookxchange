package com.bookxchange.customExceptions;

public class NotEnoughPointsException extends  RuntimeException{
    public NotEnoughPointsException() {
        super();
    }

    public NotEnoughPointsException(String message) {
        super(message);
    }

    public NotEnoughPointsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughPointsException(Throwable cause) {
        super(cause);
    }
}
