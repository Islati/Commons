package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Enchantments;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:11 PM
 */
public class EnchantCommand {
	@Command(identifier = "enchant", permissions = "commons.command.enchant")
	public void enchantCommand(Player player, @Arg(name = "enchantment") Enchantment enchant, @Arg(name = "level") int level) {

		//Assure the player has an item in their hand, what they're adding the enchantments to.
		if (!Players.hasItemInHand(player)) {
			Players.sendMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
			return;
		}

		//Get the item to add the enchantment to, sending feedback whether it worked, or not!
		ItemStack hand = player.getItemInHand();
		if (!Items.addEnchantment(hand, enchant, level, true)) {
			Players.sendMessage(player, Messages.FAILED_TO_ENCHANT_ITEM);
			return;
		}

		Players.sendMessage(player, Messages.itemEnchantmentAdded(enchant.getName(), level));
	}

	@Command(identifier = "enchant list", permissions = "commons.command.enchant")
	public void enchantListCommand(Player player) {
		for (Enchantments enchants : Enchantments.values()) {
			Players.sendMessage(player, String.format("&eIdentifier: &a%s\n&r&e- Aliases:\n&a%s", enchants.getMainAlias(), StringUtil.joinString(enchants.getAliases(), ", ")));
		}
	}
}
