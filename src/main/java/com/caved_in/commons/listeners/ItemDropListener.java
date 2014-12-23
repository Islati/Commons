package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDropListener implements Listener {
	private WorldConfiguration config;

	public ItemDropListener() {
		config = Commons.getInstance().getConfiguration().getWorldConfig();
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if (!config.isItemDropEnabled()) {
			event.setCancelled(true);
			return;
		}

		ItemStack item = event.getItemDrop().getItemStack();

		//If we're not dealing with gadgets
		if (!Gadgets.isGadget(item)) {
			event.setCancelled(!config.isItemDropEnabled());
			return;
		}

		Gadget gadget = Gadgets.getGadget(item);


		boolean drop = gadget.drop(event.getPlayer());

		if (!drop) {
			event.setCancelled(true);
		}
	}
}
