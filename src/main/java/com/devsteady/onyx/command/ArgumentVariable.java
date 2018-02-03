package com.devsteady.onyx.command;

import org.bukkit.command.CommandSender;

public interface ArgumentVariable<T> {
    /**
     * Transform the variable to it's specified type.
     *
     * @param sender   user performing the command.
     * @param argument argument which the variable was used for.
     * @param varName  value of the variable.
     * @return transformed object, otherwise error.
     * @throws CommandError
     */
    T var(CommandSender sender, CommandArgument argument, String varName) throws CommandError;
}
