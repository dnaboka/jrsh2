package com.jaspersoft.jrsh.exception;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/16/15
 */
public class ExecutionException extends Exception {

    public ExecutionException() {
        super();
    }

    public ExecutionException(final String message) {
        super(message);
    }

    public ExecutionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ExecutionException(final Throwable cause) {
        super(cause);
    }

    public ExecutionException(final String message, final Throwable cause,
                              final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}