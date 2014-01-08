package com.caved_in.commons.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockSpreadListener implements Listener {

	@EventHandler
	public void onBlockSpread(BlockSpreadEvent event) {
		if (event.getSource().getType() == Material.MYCEL) {
			event.setCancelled(true);
		}
	}
}
