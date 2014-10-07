package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RecipeCommand {
	@Command(identifier = "recipe", permissions = {"commons.command.recipe"})
	public void onItemRecipeCommand(Player player, @Arg(name = "item", def = "?hand") ItemStack item) {
		boolean shown = Items.showRecipe(player, item);
		if (!shown) {
			Players.sendMessage(player, Messages.invalidRecipe(item));
		}
	}
}
