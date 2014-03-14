package com.caved_in.commons.command;

import com.caved_in.commons.Messages;
import com.caved_in.commons.item.ItemType;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:09 PM
 */
public class ItemCommand {

	@CommandController.CommandHandler(name = "i", permission = "tunnels.common.item", aliases = {"item"})
	public void onItemCommand(Player player, String[] args) {
		if (args.length > 0) {
			//Our item argument
			String itemArgument = args[0];
			//Our item ID
			int itemID = 0;
			//How many of the item we want
			int itemAmount = 1;
			int dataValue = 0;
			//Check if they want an item with a byte value or not
			if (itemArgument.contains(":")) {
				String[] splitItemData = itemArgument.split(":");
				//The ID or material
				String itemMaterial = splitItemData[0];
				//The potential byte value
				String itemByte = splitItemData[1];

				//Check if the first have of our string is an integer, or a name
				if (StringUtils.isNumeric(itemMaterial)) {
					itemID = Integer.parseInt(itemMaterial);
				} else {
					//Get the item type based on whatever the player entered
					ItemType itemType = ItemType.lookup(itemMaterial, true);
					if (itemType != null) {
						//Set our itemID to be the id of the name they entered
						itemID = itemType.getID();
					} else {
						//Player entered an item that doesn't exist
						Players.sendMessage(player, Messages.ITEM_DOESNT_EXIST(itemMaterial));
						return;
					}
				}
				//Now we parse for the extra byte data
				if (StringUtils.isNumeric(itemByte)) {
					//Parse the byte and say that we DO have a byte value
					dataValue = Integer.parseInt(itemByte);
				} else {
					//They entered an invalid data value
					Players.sendMessage(player, Messages.INVALID_ITEM_DATA(itemByte));
					return;
				}
			} else {
				//Check if they entered an ID or name
				if (StringUtils.isNumeric(itemArgument)) {
					//They passed an ID as the argument, so parse it
					itemID = Integer.parseInt(itemArgument);
				} else {
					//Look for the itemtype based on the name they entered
					ItemType itemType = ItemType.lookup(itemArgument, true);
					if (itemType != null) {
						itemID = itemType.getID();
					} else {
						//No results; Sawwy!
						Players.sendMessage(player, Messages.ITEM_DOESNT_EXIST(itemArgument));
						return;
					}
				}
			}
			//Now we check if the player entered an amount that they'd like.
			if (args.length > 1) {
				String itemAmountArg = args[1];
				if (StringUtils.isNumeric(itemAmountArg)) {
					//Parse what the player entered and set that to the amount to give them
					itemAmount = Integer.parseInt(itemAmountArg);
				} else {
					Players.sendMessage(player, Messages.INVALID_COMMAND_USAGE("item", "item amount"));
					return;
				}
			}
			//Now we create the itemstack based on what they gave us previously.
			ItemStack itemStack = Items.getMaterialData(itemID, dataValue).toItemStack(itemAmount);
			//give the player the item :)
			Players.giveItem(player, itemStack);
			Players.sendMessage(player, Messages.ITEM_GIVEN_COMMAND(Items.getFormattedMaterialName(itemStack.getType()), itemAmount));
		} else {
			Players.sendMessage(player, Messages.INVALID_COMMAND_USAGE("item"));
		}
	}
}
