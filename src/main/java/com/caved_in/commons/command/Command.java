package com.caved_in.commons.command;

import org.bukkit.command.CommandSender;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {
	/**
	 * The description of this command
	 */
	String description() default "";

	/**
	 * The identifier describes what command definition this will bind to. Spliced by spaces, you can define as many sub commands as you want, as long as the first command (the root) is defined in the plugin.yml file.<br><br>
	 * Example: {@code @Command(identifier="root sub1 sub2")}<br>
	 * The first command "root" needs to be defined in the plugin.yml. The user will be able to access the command by writing (if the root command does not choose an alias instead):<br>
	 * {@code /root sub1 sub2}<br>
	 */
	String identifier();

	/**
	 * If this command can only be executed by players (default true).<br>
	 * If you turn this to false, the first parameter in the method must be the {@link CommandSender} to avoid {@link ClassCastException}
	 */
	boolean onlyPlayers() default true;

	/**
	 * The permissions to check if the user have before execution. If it is empty the command does not require any permission.<br><br>
	 * If the user don't have one of the permissions, they will get an error message stating that they do not have permission to use the command.
	 */
	String[] permissions() default {};
}
