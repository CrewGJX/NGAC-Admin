package com.phor.ngac.exception;

public class NodeNotFountException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "节点不存在";

    public NodeNotFountException() {
        super(DEFAULT_MESSAGE);
    }

    public NodeNotFountException(String message) {
        super(message);
    }

    public NodeNotFountException(String message, Throwable cause) {
        super(message, cause);
    }
}
