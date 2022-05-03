package com.bookxchange.customExceptions;

public class BadAuthentificationException extends RuntimeException{

    public BadAuthentificationException() {
    }

    public BadAuthentificationException(String message) {
        super(message);
    }

    public BadAuthentificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadAuthentificationException(Throwable cause) {
        super(cause);
    }

    public BadAuthentificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
