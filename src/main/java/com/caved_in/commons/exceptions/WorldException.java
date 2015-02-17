package com.caved_in.commons.exceptions;

public class WorldException extends Exception {
	public WorldException() {
	}

	public WorldException(String message) {
		super(message);
	}

	public WorldException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorldException(Throwable cause) {
		super(cause);
	}

	public WorldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
