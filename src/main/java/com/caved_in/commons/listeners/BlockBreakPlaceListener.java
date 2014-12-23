package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockBreakPlaceListener implements Listener {

	private static Commons commons = Commons.getInstance();
	private static WorldConfiguration config;

	public BlockBreakPlaceListener() {
		config = Commons.getInstance().getConfiguration().getWorldConfig();
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (config.isBlockBreakEnabled()) {
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
		if (config.isBlockBreakEnabled()) {
			return;
		}

		if (!Players.hasPermission(player, Perms.BLOCK_PLACE)) {
			if (player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			}
		}

	}


}
