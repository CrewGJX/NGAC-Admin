package com.phor.ngac.exception;

public class DuplicateDefinedException extends RuntimeException {
    public DuplicateDefinedException() {
        super();
    }

    public DuplicateDefinedException(String message) {
        super(message);
    }

    public DuplicateDefinedException(String message, Throwable cause) {
        super(message, cause);
    }
}
