package com.caved_in.commons.exceptions;

public class InvalidMaterialNameException extends Exception {
    public InvalidMaterialNameException() {
    }

    public InvalidMaterialNameException(String message) {
        super(message);
    }

    public InvalidMaterialNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMaterialNameException(Throwable cause) {
        super(cause);
    }

    public InvalidMaterialNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
