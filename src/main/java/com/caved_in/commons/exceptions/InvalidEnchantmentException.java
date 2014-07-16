package com.caved_in.commons.exceptions;

public class InvalidEnchantmentException extends Exception {
	private static final String exceptionMessage = "%s isn't a valid enchantment";

	public InvalidEnchantmentException() {
	}

	public InvalidEnchantmentException(String message) {
		super(String.format(exceptionMessage, message));
	}

}
