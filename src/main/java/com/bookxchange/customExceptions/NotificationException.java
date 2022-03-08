package com.bookxchange.customExceptions;

public class NotificationException extends RuntimeException {

    public NotificationException() {
        super();
    }

    public NotificationException(String message) {
        super(message);
    }

    public NotificationException(String format, String status) {
    }
}
