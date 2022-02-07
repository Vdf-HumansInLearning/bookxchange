package com.bookxchange.customExceptions;

public class InvalidISBNException extends RuntimeException {
    public InvalidISBNException() { super(); }
    public InvalidISBNException(String message) { super(message); }
    public InvalidISBNException(String message, Throwable cause) { super(message,cause); }
    public InvalidISBNException(Throwable cause) {
        super(cause);
    }
}
