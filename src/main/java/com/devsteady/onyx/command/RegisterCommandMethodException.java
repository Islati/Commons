package com.devsteady.onyx.command;

import java.lang.reflect.Method;

public class RegisterCommandMethodException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RegisterCommandMethodException(Method method, String msg) {
        super("Could not register the command method " + method.getName() + " in the class " + method.getDeclaringClass().getName() + ". Reason: " + msg);
    }
}
