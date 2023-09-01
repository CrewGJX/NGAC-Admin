package com.phor.ngac.exception;

public class FreeMarkerException extends RuntimeException {
    public FreeMarkerException(String message) {
        super(message);
    }

    public FreeMarkerException(String message, Throwable cause) {
        super(message, cause);
    }
}
