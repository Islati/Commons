package com.devsteady.onyx.exceptions;

/**
 * Whenever a chunk is in an invalid state (not loaded, unloaded) and actions are called on it for use, this exception is thrown.
 */
public class InvalidChunkStateException extends Exception {
    public InvalidChunkStateException() {
    }

    public InvalidChunkStateException(String message) {
        super(message);
    }

    public InvalidChunkStateException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidChunkStateException(Throwable cause) {
        super(cause);
    }

    public InvalidChunkStateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
