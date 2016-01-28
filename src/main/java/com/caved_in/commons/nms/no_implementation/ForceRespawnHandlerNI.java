package com.caved_in.commons.nms.no_implementation;

import com.caved_in.commons.nms.ForceRespawnHandler;
import org.bukkit.entity.Player;

public class ForceRespawnHandlerNI implements ForceRespawnHandler {
	@Override
	public void forceRespawn(Player player) {
		throw new IllegalAccessError("No implementation provided for forcing a player respawn");
	}
}
