package com.caved_in.commons.exceptions;

public class EventCreationException extends Exception {
    public EventCreationException() {
    }

    public EventCreationException(String message) {
        super(message);
    }

    public EventCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EventCreationException(Throwable cause) {
        super(cause);
    }

    public EventCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
