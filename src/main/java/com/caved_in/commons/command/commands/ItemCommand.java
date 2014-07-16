package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.exceptions.InvalidMaterialNameException;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class ItemCommand {
	@Command(name = "i", permission = "commons.command.item", aliases = {"item"})
	public void onItemCommand(Player player, String[] args) {
		if (args.length < 1) {
			Players.sendMessage(player, Messages.invalidCommandUsage("item"));
			return;
		}

		//Our item argument
		String itemArgument = args[0];
		//How many of the item we want
		int itemAmount = 1;
		MaterialData materialData;
		//Check if they want an item with a byte value or not
		try {
			materialData = Items.getMaterialDataFromString(itemArgument);
		} catch (InvalidMaterialNameException invalidMaterialNameException) {
			Players.sendMessage(player, Messages.invalidItem(itemArgument));
			return;
		}
		//Now we check if the player entered an amount that they'd like.
		if (args.length > 1) {
			String itemAmountArg = args[1];
			if (StringUtils.isNumeric(itemAmountArg)) {
				//Parse what the player entered and set that to the amount to give them
				itemAmount = Integer.parseInt(itemAmountArg);
			} else {
				Players.sendMessage(player, Messages.invalidCommandUsage("item", "item amount"));
				return;
			}
		}
		//Now we create the itemstack based on what they gave us previously.
		ItemStack itemStack = materialData.toItemStack(itemAmount);
		//give the player the item :)
		Players.giveItem(player, itemStack);
		Players.sendMessage(player, Messages.playerItemsGiven(Items.getFormattedMaterialName(itemStack.getType()), itemAmount));
	}
}
