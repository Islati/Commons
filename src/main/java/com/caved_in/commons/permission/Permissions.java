package com.caved_in.commons.permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Permissions {

    public static boolean hasPermission(Player player, Permissible item) {
        return player.hasPermission(item.getPermission());
    }

    public static boolean hasPermissions(CommandSender sender, String[] permissions) {
        for (String s : permissions) {
            if (!sender.hasPermission(s)) {
                return false;
            }
        }
        return true;
    }
}