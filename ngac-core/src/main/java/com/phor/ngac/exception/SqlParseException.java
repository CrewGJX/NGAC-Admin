package com.phor.ngac.exception;

public class SqlParseException extends RuntimeException {
    public SqlParseException() {
        super();
    }

    public SqlParseException(String message) {
        super(message);
    }

    public SqlParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
