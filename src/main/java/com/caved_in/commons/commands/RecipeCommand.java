package com.caved_in.commons.commands;

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
 * Time: 7:14 PM
 */
public class RecipeCommand {
	@CommandController.CommandHandler(name = "recipe", permission = "tunnels.common.recipe")
	public void onItemRecipeCommand(Player player, String[] args) {
		if (args.length > 0) {
			String itemArg = args[0];
			int itemId = 0;
			//If they entered an item id parse it
			if (StringUtils.isNumeric(itemArg)) {
				itemId = Integer.parseInt(itemArg);
			} else {
				ItemType itemType = ItemType.lookup(itemArg);
				if (itemType != null) {
					//Set our itemid to the look-up id
					itemId = itemType.getID();
				} else {
					Players.sendMessage(player, Messages.ITEM_DOESNT_EXIST(itemArg));
					return;
				}
			}
			//Get an itemstack for the item in our recipe
			ItemStack itemStack = Items.makeItemStack(Items.getMaterialById(itemId));
			//If the recipe failed to show, then send a message saying recipe not found
			if (!Items.showRecipe(player, itemStack)) {
				Players.sendMessage(player, Messages.RECIPE_NOT_FOUND(itemStack));
			}
		} else {
			if (Players.hasItemInHand(player)) {
				ItemStack playerHandItem = player.getItemInHand();
				//If the recipe failed to show, then send a message saying recipe not found
				if (!Items.showRecipe(player, playerHandItem)) {
					Players.sendMessage(player, Messages.RECIPE_NOT_FOUND(playerHandItem));
				}
			} else {
				Players.sendMessage(player, Messages.INVALID_COMMAND_USAGE("item in hand / item name"));
			}
		}
	}
}
