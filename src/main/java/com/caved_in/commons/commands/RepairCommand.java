package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.player.PlayerHandler;
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
			PlayerHandler.repairItems(player, true);
			PlayerHandler.sendMessage(player, Messages.ITEMS_REPAIRED);
		} else {
			if (PlayerHandler.hasItemInHand(player)) {
				ItemHandler.repairItem(player.getItemInHand());
				PlayerHandler.sendMessage(player, Messages.ITEMS_REPAIRED);
			} else {
				PlayerHandler.sendMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
			}
		}
	}
}
