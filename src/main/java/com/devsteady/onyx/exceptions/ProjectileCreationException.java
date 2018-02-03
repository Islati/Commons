package com.devsteady.onyx.exceptions;

public class ProjectileCreationException extends Exception {

    public ProjectileCreationException() {
    }

    public ProjectileCreationException(String message) {
        super(message);
    }

    public ProjectileCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectileCreationException(Throwable cause) {
        super(cause);
    }

    public ProjectileCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
