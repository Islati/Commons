package com.caved_in.commons.permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Permissions {
//	private static final Map<UUID, PermissionAttachment> permissions = new HashMap<>();
//
//	public static void initPlayer(Player player) {
//		permissions.put(player.getUniqueId(), player.addAttachment(Commons.getInstance()));
//	}
//
//	public static PermissionAttachment getPermissionAttachment(Player player) {
//		return getPermissionAttachment(player.getUniqueId());
//	}
//
//	public static PermissionAttachment getPermissionAttachment(UUID id) {
//		return permissions.get(id);
//	}
//
//	public static boolean hasPermission(Player player, String permission) {
//		PermissionAttachment permissionAttachment = getPermissionAttachment(player);
//		Map<String, Boolean> perms = permissionAttachment.getPermissions();
//		//If there's no key for the permission in this player, return false
//		if (!perms.containsKey(permission)) {
//			return false;
//		}
//
//		//Return the value assigned on the permission (whether it's unset or set)
//		return perms.get(permission);
//	}

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

//	public static void setPermission(Player player, String permission, boolean value) {
//		getPermissionAttachment(player).setPermission(permission, value);
//	}
//
//	public static void unsetPermission(Player player, String permission) {
//		getPermissionAttachment(player).unsetPermission(permission);
//	}
}
