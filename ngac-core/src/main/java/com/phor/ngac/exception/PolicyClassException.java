package com.phor.ngac.exception;

public class PolicyClassException extends RuntimeException {
    public PolicyClassException(String message) {
        super(message);
    }

    public PolicyClassException(String message, Throwable cause) {
        super(message, cause);
    }
}
