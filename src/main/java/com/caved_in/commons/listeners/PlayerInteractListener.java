package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.plugin.game.event.GadgetUseEvent;
import com.caved_in.commons.plugin.game.gadget.Gadgets;
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

		Commons.debug("Called a gadget use event for " + player.getName());
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
