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

		//todo check if player is in creative and has option for creative drops off, then don't drop items- Just remove them when they're dropped
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

		if (!gadget.properties().isDroppable()) {
			event.setCancelled(true);
			return;
		}


		gadget.onDrop(event.getPlayer(), event.getItemDrop());
	}
}
