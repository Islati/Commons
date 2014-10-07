package com.caved_in.commons.command.handlers;

import com.caved_in.commons.command.CommandArgument;
import com.caved_in.commons.command.TransformError;
import org.bukkit.command.CommandSender;

public class DoubleArgumentHandler extends NumberArgumentHandler<Double> {
	public DoubleArgumentHandler() {
		setMessage("parse_error", "The parameter [%p] is not a number");
	}

	@Override
	public Double transform(CommandSender sender, CommandArgument argument, String value) throws TransformError {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new TransformError(argument.getMessage("parse_error"));
		}
	}
}
