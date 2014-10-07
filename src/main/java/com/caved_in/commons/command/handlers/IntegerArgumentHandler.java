package com.caved_in.commons.command.handlers;

import com.caved_in.commons.command.CommandArgument;
import com.caved_in.commons.command.TransformError;
import org.bukkit.command.CommandSender;

public class IntegerArgumentHandler extends NumberArgumentHandler<Integer> {
	public IntegerArgumentHandler() {
		setMessage("parse_error", "The parameter [%p] is not an integer");
	}

	@Override
	public Integer transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new TransformError(argument.getMessage("parse_error"));
		}
	}
}
