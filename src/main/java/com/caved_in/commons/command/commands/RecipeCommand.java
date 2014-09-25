package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
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
	@Command(name = "recipe", permission = "commons.command.recipe")
	public void onItemRecipeCommand(Player player, String[] args) {
		if (args.length == 0) {
			if (!Players.hasItemInHand(player)) {
				Players.sendMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
				return;
			}

			ItemStack hand = player.getItemInHand();

			boolean shownRecipe = Items.showRecipe(player, hand);

			if (!shownRecipe) {
				Players.sendMessage(player, Messages.invalidRecipe(hand));
			}
			return;
		}

		String itemArg = args[0];
		int itemId = 0;

		if (!StringUtils.isNumeric(itemArg)) {
			ItemType type = ItemType.lookup(itemArg);
			if (type == null) {
				Players.sendMessage(player, Messages.invalidItem(itemArg));
				return;
			}
			itemId = type.getID();
		} else {
			itemId = Integer.parseInt(itemArg);
		}

		ItemStack item = Items.makeItem(Items.getMaterialById(itemId));

		boolean shown = Items.showRecipe(player, item);
		if (!shown) {
			Players.sendMessage(player, Messages.invalidRecipe(item));
		}

	}
}
