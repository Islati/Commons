package com.caved_in.commons.listeners;

import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ItemBreakListener implements Listener {
	@EventHandler
	public void onItemBreakEvent(PlayerItemBreakEvent e) {
		Player p = e.getPlayer();
		ItemStack broken = e.getBrokenItem();

		if (Gadgets.isGadget(broken)) {
			Gadget gadget = Gadgets.getGadget(broken);

			if (!gadget.properties().isBreakable()) {
				broken.setDurability(Short.MAX_VALUE);
			} else {
				//TODO Implement decrement of pseudo-durability code
			}
		}
	}
}
