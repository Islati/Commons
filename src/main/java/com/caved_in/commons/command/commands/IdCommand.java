package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.exceptions.InvalidMaterialNameException;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IdCommand {
	@Command(name = "id", permission = "commons.commands.id")
	public void onIdCommand(Player player, String[] args) {
		if (args.length < 1) {
			if (!Players.hasItemInHand(player)) {
				Players.sendMessage(player, "&eEither &ogive an item name as an argument, &e&lor&r&e &ohold an item in your hand&r&e.");
				return;
			}

			ItemStack item = player.getItemInHand();
			Players.sendMessage(player, Messages.itemId(item));
			return;
		}

		String itemArg = args[0];
		Material material = null;

		try {
			material = Items.getMaterialByName(itemArg);
			Players.sendMessage(player, Messages.itemId(itemArg, material));
		} catch (InvalidMaterialNameException e) {
			Players.sendMessage(player, Messages.invalidItem(itemArg));
		}
	}
}
