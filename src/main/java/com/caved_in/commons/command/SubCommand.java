package com.caved_in.commons.command;

import com.caved_in.commons.Messages;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author AmoebaMan
 *         An annotation interface that may be attached to a method to designate it as a subcommand handler.
 *         When registering a handler with this class, only methods marked with this annotation will be considered for subcommand registration.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {
	String parent();

	String name();

	String permission() default "";

	String permissionMessage() default Messages.MESSAGE_PREFIX + "You do not have permission for this command, if you believe this is an error please fill out a bug report on our forums.";
}
