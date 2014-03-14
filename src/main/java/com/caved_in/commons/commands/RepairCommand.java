package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:13 PM
 */
public class RepairCommand {
	@CommandController.CommandHandler(name = "repair", permission = "tunnels.common.repair")
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
