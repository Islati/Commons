package com.devsteady.onyx.command;

public class InvalidVerifyArgument extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidVerifyArgument(String name) {
        super("The verifier " + name + " is not valid.");
    }
}
