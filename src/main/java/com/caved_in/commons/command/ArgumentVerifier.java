package com.caved_in.commons.command;

import org.bukkit.command.CommandSender;

public interface ArgumentVerifier<T> {
	public void verify(CommandSender sender, CommandArgument argument, String verifyName, String[] verifyArgs, T value, String valueRaw) throws VerifyError;
}
