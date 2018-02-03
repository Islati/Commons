package com.devsteady.onyx.nms;

import org.bukkit.entity.Player;

import java.util.ConcurrentModificationException;

public interface ForceRespawnHandler {
	void forceRespawn(Player player) throws ConcurrentModificationException;
}
