package com.caved_in.commons.exceptions;

public class InvalidMaterialName extends Exception {
	public InvalidMaterialName() {
	}

	public InvalidMaterialName(String message) {
		super(message);
	}

	public InvalidMaterialName(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMaterialName(Throwable cause) {
		super(cause);
	}

	public InvalidMaterialName(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
