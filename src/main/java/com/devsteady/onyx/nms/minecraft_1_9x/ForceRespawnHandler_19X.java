package com.devsteady.onyx.nms.minecraft_1_9x;

import com.devsteady.onyx.nms.ForceRespawnHandler;
import org.bukkit.entity.Player;

import java.util.ConcurrentModificationException;

public class ForceRespawnHandler_19X implements ForceRespawnHandler {
	@Override
	public void forceRespawn(Player player) throws ConcurrentModificationException {
		player.spigot().respawn();
	}
}
