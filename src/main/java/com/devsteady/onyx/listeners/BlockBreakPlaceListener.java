package com.devsteady.onyx.listeners;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.config.Configuration;
import com.devsteady.onyx.debug.Debugger;
import com.devsteady.onyx.permission.Perms;
import com.devsteady.onyx.player.MinecraftPlayer;
import com.devsteady.onyx.player.Players;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockBreakPlaceListener implements Listener {

	private static Onyx commons = Onyx.getInstance();
	private static Configuration config;

	public BlockBreakPlaceListener() {
		config = commons.getConfiguration();
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (config.enableBlockBreak()) {
			return;
		}

		Player player = event.getPlayer();
		MinecraftPlayer minecraftPlayer = commons.getPlayerHandler().getData(player);
		//If block breaking is disabled
		//If the player doesn't have the permission to break blocks, disable it
		if (!Players.hasPermission(player, Perms.BLOCK_BREAK)) {
			event.setCancelled(true);
		}
		//If the player's in debug mode, then send them debug info
		if (minecraftPlayer.isInDebugMode()) {
			Debugger.debugBlockBreakEvent(player, event);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();
		if (config.enableBlockBreak()) {
			return;
		}

		if (!Players.hasPermission(player, Perms.BLOCK_PLACE)) {
			if (player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			}
		}

	}


}
