package com.bookxchange.customExceptions;

public class RegisterException extends RuntimeException{
    public RegisterException() {
        super();
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String format, String status) {
    }
}
