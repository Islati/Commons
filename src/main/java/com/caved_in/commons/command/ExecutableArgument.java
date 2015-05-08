package com.caved_in.commons.command;

import org.bukkit.command.CommandSender;

public interface ExecutableArgument {
    public Object execute(CommandSender sender, Arguments args) throws CommandError;
}
