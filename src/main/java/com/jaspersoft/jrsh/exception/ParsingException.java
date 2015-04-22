package com.jaspersoft.jrsh.exception;

/**
 * @author kyrylo.torbin
 * @version 1.0 4/20/15
 */
public class ParsingException extends Exception {

    public ParsingException() {
    }

    public ParsingException(final String message) {
        super(message);
    }

    public ParsingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ParsingException(final Throwable cause) {
        super(cause);
    }

    public ParsingException(final String message, final Throwable cause,
                            final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}