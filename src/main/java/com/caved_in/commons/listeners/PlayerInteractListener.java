package com.caved_in.commons.listeners;

import com.caved_in.commons.game.event.GadgetUseEvent;
import com.caved_in.commons.game.gadget.Gadgets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

	@EventHandler
	public void onPlayerInteracted(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		ItemStack itemInHand = player.getItemInHand();
		if (!Gadgets.isGadget(itemInHand)) {
			return;
		}
		GadgetUseEvent gadgetEvent = new GadgetUseEvent(player, itemInHand);
		GadgetUseEvent.handle(gadgetEvent);
	}
}
