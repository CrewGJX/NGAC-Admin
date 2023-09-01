package com.phor.ngac.exception;

public class EventFactoryException extends RuntimeException {
    public EventFactoryException(String message) {
        super(message);
    }

    public EventFactoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
