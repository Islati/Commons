package com.caved_in.commons.permission;

import org.bukkit.command.CommandSender;

public interface PermissionHandler {
    public boolean hasPermission(CommandSender sender, String[] permissions);
}
