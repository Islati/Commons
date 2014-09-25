package com.caved_in.commons.location;

import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public enum PreTeleportType {
	COMMAND("commons.command.back", TeleportCause.COMMAND),
	END_PORTAL("commons.back.endportal", TeleportCause.END_PORTAL),
	ENDER_PEARL("commons.back.enderpearl", TeleportCause.ENDER_PEARL),
	NETHER_PORTAL("commons.back.netherportal", TeleportCause.NETHER_PORTAL),
	PLUGIN("commons.command.back", TeleportCause.COMMAND),
	DEATH("commons.back.death", TeleportCause.PLUGIN),
	UNKNOWN("commons.command.back", TeleportCause.UNKNOWN),
	WARP("commons.back.warp", TeleportCause.PLUGIN);


	private static Map<TeleportCause, PreTeleportType> typeMap = new HashMap<>();

	static {
		for (PreTeleportType teleType : EnumSet.allOf(PreTeleportType.class)) {
			typeMap.put(teleType.getCause(), teleType);
		}
	}

	private String permission;
	private TeleportCause teleCause;

	PreTeleportType(String permission, TeleportCause cause) {
		this.permission = permission;
		this.teleCause = cause;
	}

	public String getPermission() {
		return permission;
	}

	public TeleportCause getCause() {
		return teleCause;
	}

	public static PreTeleportType getByCause(TeleportCause cause) {
		return typeMap.get(cause);
	}
}
