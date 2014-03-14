package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 6:52 PM
 */
public class SkullCommand {
	@CommandController.CommandHandler(name = "skull", usage = "/skull <Name>", permission = "tunnels.common.skull")
	public void getPlayerSkullCommand(Player player, String[] commandArgs) {
		if (commandArgs.length > 0) {
			String playerName = commandArgs[0];
			ItemStack playerSkull = Items.getSkull(playerName);
			Items.setItemName(playerSkull, playerName + "'s Head");
			player.getInventory().addItem(playerSkull);
		} else {
			Players.sendMessage(player, Messages.INVALID_COMMAND_USAGE("name"));
		}
	}
}
