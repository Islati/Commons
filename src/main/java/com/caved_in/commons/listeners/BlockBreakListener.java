package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Permission;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.Debugger;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class BlockBreakListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		PlayerWrapper playerWrapper = Players.getData(player);
		//If block breaking is disabled
		if (!Commons.getWorldConfig().isBlockBreakEnabled()) {
			//If the player doesn't have the permission to break blocks, disable it
			if (!Players.hasPermission(player, Permission.BLOCK_BREAK)) {
				event.setCancelled(true);
			}
		}
		//If the player's in debug mode, then send them debug info
		if (playerWrapper.isInDebugMode()) {
			Debugger.debugBlockBreakEvent(player, event);
		}
	}

	@EventHandler
	public void onBlockPLace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		if (Commons.getConfiguration().getWorldConfig().isBlockBreakEnabled()) {
			return;
		}
		if (!Players.hasPermission(player, Permission.BLOCK_PLACE)) {
			if (player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			}
		}

	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		//Clear all the blocks from the block list to assure they're not removed when exploding
		event.blockList().clear();
	}
}
