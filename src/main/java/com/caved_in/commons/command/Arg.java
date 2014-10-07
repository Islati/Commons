package com.caved_in.commons.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Arg {
	/**
	 * The default argument to process if the argument is not defined by the user. To make it a mandatory argument define it as a space " " (as it is default).<br><br>
	 * When this is combined with the {@link FlagArg} annotation the default argument will only be used if the <b>flag</b> is not defined by the user. Flags can't have default arguments.
	 */
	String def() default " ";

	/**
	 * The description of this argument.
	 */
	String description() default "";

	/**
	 * The short-name of this argument. This is what will show between the [] when this argument is described to the user.
	 */
	String name();

	/**
	 * The verifiers is a way to verify the arguments before they even execute the command.<br><br>
	 * For example, if you would want a integer defined by the user but it isen't allowed to be greater than 10 you can use the <b>max</b> verifier. <br>
	 * {@code @Arg(name="example", verifiers="max[10]")}<br>
	 * If the user input is greater than 10, it will return an predefined error message to the user.<br><br>
	 * You can add multiple verifiers using the <b>|</b> between each.<br>
	 * {@code @Arg(name="example", verifiers="min[5]|max[10]")}<br><br>
	 * The verifiers differs from what class the parameter have. To check which verifiers are available, see the {@linkplain ArgumentHandler} for that class.
	 */
	String verifiers() default "";
}
