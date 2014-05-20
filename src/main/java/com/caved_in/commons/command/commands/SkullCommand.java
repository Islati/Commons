package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkullCommand {
	@Command(name = "skull", usage = "/skull <Name>", permission = "commons.command.skull")
	public void getPlayerSkullCommand(Player player, String[] commandArgs) {
		if (commandArgs.length > 0) {
			String playerName = commandArgs[0];
			ItemStack playerSkull = Items.getSkull(playerName);
			Items.setName(playerSkull, playerName + "'s Head");
			player.getInventory().addItem(playerSkull);
		} else {
			Players.sendMessage(player, Messages.invalidCommandUsage("name"));
		}
	}
}
