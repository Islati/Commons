package com.devsteady.onyx.exceptions;

public class WorldLoadException extends WorldException {
    public WorldLoadException() {
    }

    public WorldLoadException(String s) {
        super(s);
    }

    public WorldLoadException(String message, Throwable cause) {
        super(message, cause);
    }

    public WorldLoadException(Throwable cause) {
        super(cause);
    }

    public WorldLoadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
