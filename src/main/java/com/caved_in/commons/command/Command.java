package com.caved_in.commons.command;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author AmoebaMan
 *         An annotation interface that may be attached to a method to designate it as a command handler.
 *         When registering a handler with this class, only methods marked with this annotation will be considered for command registration.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	String name();

	String[] aliases() default {""};

	String description() default "";

	String usage() default "";

	String permission() default "";

	String permissionMessage() default "You do not have permission for this command.";
}
