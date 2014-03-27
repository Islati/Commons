package com.caved_in.commons.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Permission {
	/* Used to allow joining during maintenance mode */
	MAINTENANCE_WHITELIST("common.maintenance.join"),
	/* Used to see through player disguises */
	BYPASS_DISGUISED_PLAYERS("common.disguise.nickvisible"),
	/* NameTag Permissions */
	BLACK_NAMETAG("common.nametag.black"),
	YELLOW_NAMETAG("common.nametag.yellow"),
	LIGHT_PURPLE_NAMETAG("common.nametag.lightpurple"),
	DARK_GRAY_NAMETAG("common.nametag.darkgray"),
	DARK_GREEN_NAMETAG("common.nametag.darkgreen"),
	GREEN_NAMETAG("common.nametag.green"),
	GRAY_NAMETAG("common.nametag.gray"),
	GOLD_NAMETAG("common.nametag.gold"),
	PURPLE_NAMETAG("common.nametag.purple"),
	DARK_RED_NAMETAG("common.nametag.darkred"),
	RED_NAMETAG("common.nametag.red"),
	DARK_BLUE_NAMETAG("common.nametag.darkblue"),
	BLUE_NAMETAG("common.nametag.blue"),
	AQUA_NAMETAG("common.nametag.aqua"),
	WHITE_NAMETAG("common.nametag.white"),
	/* Debug mode allows player to get feedback on the things they're doing */
	DEBUG_MODE("common.debugmode"),
	/* Action-Related Permissions */
	BLOCK_BREAK("common.block.break"),
	BLOCK_PLACE("common.block.place");

	private static Map<String, Permission> permissions = new HashMap<>();

	static {
		for (Permission permission : EnumSet.allOf(Permission.class)) {
			permissions.put(permission.node, permission);
		}
	}

	private final String node;

	Permission(String node) {
		this.node = node;
	}

	@Override
	public String toString() {
		return node;
	}

	public static Permission getPermission(String node) {
		return permissions.get(node);
	}
}
