package com.phor.ngac.exception;

/**
 * 没有找到表别名异常
 *
 * @version 0.1
 * @since 0.1
 */
public class TableAliasNotDefinedException extends RuntimeException {
    public TableAliasNotDefinedException() {
        super();
    }

    public TableAliasNotDefinedException(String message) {
        super(message);
    }

    public TableAliasNotDefinedException(String message, Throwable cause) {
        super(message, cause);
    }
}
