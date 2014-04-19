package com.caved_in.commons.permission;

import com.caved_in.commons.Commons;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class Permissions {
	private static final Map<UUID, PermissionAttachment> permissions = new HashMap<>();

	public static void initPlayer(Player player) {
		permissions.put(player.getUniqueId(), player.addAttachment(Commons.getInstance()));
	}

	public static PermissionAttachment getPermissionAttachment(Player player) {
		return getPermissionAttachment(player.getUniqueId());
	}

	public static PermissionAttachment getPermissionAttachment(UUID id) {
		return permissions.get(id);
	}

	public static boolean hasPermission(Player player, String permission) {
		PermissionAttachment permissionAttachment = getPermissionAttachment(player);
		Map<String, Boolean> perms = permissionAttachment.getPermissions();
		//If there's no key for the permission in this player, return false
		if (!perms.containsKey(permission)) {
			return false;
		}

		//Return the value assigned on the permission (whether it's unset or set)
		return perms.get(permission);
	}

	public static void setPermission(Player player, String permission, boolean value) {
		getPermissionAttachment(player).setPermission(permission, value);
	}

	public static void unsetPermission(Player player, String permission) {
		getPermissionAttachment(player).unsetPermission(permission);
	}
}
