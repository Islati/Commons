package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Messages;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugHandItem implements DebugAction {

	@Override
	public void doAction(Player player) {
		if (!Players.hasItemInHand(player)) {
			Players.sendMessage(player, "You require an item in your hand to use this debug action");
			return;
		}

		ItemStack itemStack = player.getItemInHand();
		Players.sendMessage(player, Messages.itemInfo(itemStack));
	}

	@Override
	public String getActionName() {
		return "hand_item_info";
	}
}
