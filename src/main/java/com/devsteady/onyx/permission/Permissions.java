package com.devsteady.onyx.permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Permissions {

    public static boolean hasPermission(Player player, Permissible item) {
        if (player == null || item == null) {
            return false;
        }

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