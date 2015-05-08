package com.caved_in.commons.exceptions;

public class GadgetInitializeError extends Exception {
    public GadgetInitializeError() {
    }

    public GadgetInitializeError(String message) {
        super(message);
    }

    public GadgetInitializeError(String message, Throwable cause) {
        super(message, cause);
    }

    public GadgetInitializeError(Throwable cause) {
        super(cause);
    }

    public GadgetInitializeError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
