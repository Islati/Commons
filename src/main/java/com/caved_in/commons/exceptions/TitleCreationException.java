package com.caved_in.commons.exceptions;

public class TitleCreationException extends Exception {
    public TitleCreationException() {
        super();
    }

    public TitleCreationException(String message) {
        super(message);
    }

    public TitleCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TitleCreationException(Throwable cause) {
        super(cause);
    }

    protected TitleCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
