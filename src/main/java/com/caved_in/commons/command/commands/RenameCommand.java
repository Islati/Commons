package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class RenameCommand {
	@Command(name = "rename", permission = "commons.command.rename")
	public void onRenameCommand(Player player, String[] args) {
		if (args.length == 0) {
			Players.sendMessage(player, Messages.invalidCommandUsage("item name"));
			return;
		}

		if (!Players.hasItemInHand(player)) {
			Players.sendMessage(player, "&eYou need an item in your hand.");
			return;
		}

		StringBuilder itemNameBuilder = new StringBuilder();

		for (String arg : args) {
			itemNameBuilder.append(arg).append(" ");
		}

		String itemName = itemNameBuilder.toString();
		Items.setName(player.getItemInHand(), itemName);
		Players.sendMessage(player, String.format("&aItem Re-Named to %s", itemName));
	}
}
