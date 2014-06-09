package com.caved_in.commons.exceptions;

public class InvalidEnchantment extends Exception {
	private static final String exceptionMessage = "%s isn't a valid enchantment";

	public InvalidEnchantment() {
	}

	public InvalidEnchantment(String message) {
		super(String.format(exceptionMessage, message));
	}

}
