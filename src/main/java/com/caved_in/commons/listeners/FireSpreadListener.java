package com.caved_in.commons.listeners;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class FireSpreadListener implements Listener {

	@EventHandler
	public void onFireSpread(BlockIgniteEvent event) {
		IgniteCause igniteCause = event.getCause();
		Block block = event.getBlock();
		Player player = event.getPlayer();

		switch (igniteCause) {
			case SPREAD:
			case LIGHTNING:
			case LAVA:
				event.setCancelled(true);
				break;
			case FLINT_AND_STEEL:
				if (player != null) {
					//If they're not an operator and they're not in creative
					if (!player.isOp() && player.getGameMode() != GameMode.CREATIVE) {
						event.setCancelled(true);
					}
				}
				break;
			default:
				break;
		}
	}

	@EventHandler
	public void onBlockBurn(BlockBurnEvent event) {
		event.setCancelled(true);
	}
}
