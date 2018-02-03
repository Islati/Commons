package com.devsteady.onyx.command;

import org.bukkit.command.CommandSender;

public interface ArgumentVerifier<T> {
    void verify(CommandSender sender, CommandArgument argument, String verifyName, String[] verifyArgs, T value, String valueRaw) throws VerifyError;
}
