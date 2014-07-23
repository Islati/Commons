package com.caved_in.commons.listeners;

import com.caved_in.commons.event.GadgetUseEvent;
import com.caved_in.commons.gadget.Gadgets;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerInteracted(PlayerInteractEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Player player = event.getPlayer();

		if (!Players.hasItemInHand(player)) {
			return;
		}

		ItemStack itemInHand = player.getItemInHand();

		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		if (!Gadgets.isGadget(itemInHand)) {
			return;
		}

		GadgetUseEvent gadgetEvent = new GadgetUseEvent(player, itemInHand);
		GadgetUseEvent.handle(gadgetEvent);
//
//		if (itemInHand != null && itemInHand.getType() == Material.COMPASS) {
//			if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//				if (Items.getName(itemInHand).toLowerCase().contains("server selector")) {
//					Commons.serverMenu.openMenu(player);
//				}
//			}
//		}
	}
}
