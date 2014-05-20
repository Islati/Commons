package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class RepairCommand {
	@Command(name = "repair", permission = "commons.command.repair")
	public void onItemRepairCommand(Player player, String[] args) {
		//If the command issuer wanted to repair all their items
		if (args.length > 0 && args[0].equalsIgnoreCase("all")) {
			Players.repairItems(player, true);
			Players.sendMessage(player, Messages.ITEMS_REPAIRED);
		} else {
			if (Players.hasItemInHand(player)) {
				Items.repairItem(player.getItemInHand());
				Players.sendMessage(player, Messages.ITEMS_REPAIRED);
			} else {
				Players.sendMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
			}
		}
	}
}
