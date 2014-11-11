package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.game.item.Weapon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDropListener implements Listener {
	private boolean drop;

	public ItemDropListener() {
		drop = Commons.getConfiguration().getWorldConfig().isItemDropEnabled();
	}

	@EventHandler
	public void onItemDrop(PlayerDropItemEvent event) {
		if (!drop) {
			event.setCancelled(true);
			return;
		}

		ItemStack item = event.getItemDrop().getItemStack();

		//If we're not dealing with gadgets
		if (!Gadgets.isGadget(item)) {
			event.setCancelled(!drop);
			return;
		}

		Gadget gadget = Gadgets.getGadget(item);

		if (!(gadget instanceof Weapon)) {
			return;
		}

		Weapon weapon = (Weapon) gadget;
		weapon.onDrop(event.getPlayer());
	}
}
