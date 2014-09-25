package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Enchantments;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.menu.ItemFormat;
import com.caved_in.commons.menu.Menus;
import com.caved_in.commons.menu.PageDisplay;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:11 PM
 */
public class EnchantCommand {
	//TODO Create permissions restricting all of the enchantments and levels.
	@Command(name = "enchant", permission = "commons.command.enchant")
	public void onEnchantCommand(Player player, String[] args) {
		//Check if an enchantment name was passed
		if (args.length > 0) {
			String enchantmentName = args[0];
			//Check if the enchantment they entered is a valid enchantment
			if (Enchantments.isEnchantment(enchantmentName)) {
				//Get the enchantment they entered
				Enchantment enchantment = Enchantments.getEnchantment(enchantmentName);
				int enchantmentLevel = 1;
				//Check if they entered a level argument for the enchantment
				if (args.length > 1 && StringUtils.isNumeric(args[1])) {
					enchantmentLevel = Integer.parseInt(args[1]);
				}
				ItemStack itemStack = player.getItemInHand();
				//If the player has an item in their hand
				if (itemStack != null) {
					//Check if they enchanted the item successfully, and if so send them a message
					if (Items.addEnchantment(itemStack, enchantment, enchantmentLevel, true)) {
						Players.sendMessage(player, Messages.itemEnchantmentAdded(enchantmentName, enchantmentLevel));
					} else {
						//Send them a message saying it failed
						Players.sendMessage(player, Messages.FAILED_TO_ENCHANT_ITEM);
					}
				} else {
					//Send the player a message saying they require an item in their hand
					Players.sendMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
				}
			} else if (enchantmentName.equalsIgnoreCase("list")) {
				HelpScreen helpScreen = Menus.generateHelpScreen(ChatColor.GOLD + "Enchantments list", PageDisplay.DEFAULT, ItemFormat.SINGLE_DASH, ChatColor.GREEN, ChatColor.DARK_GREEN);
				for (Enchantments enchantments : Enchantments.values()) {
					helpScreen.setEntry(enchantments.getMainAlias(), StringUtil.joinString(enchantments.getAliases(), ", "));
				}
				int page = 1;
				if (args.length > 1) {
					String pageArgument = args[1];
					if (StringUtils.isNumeric(pageArgument)) {
						page = Integer.parseInt(pageArgument);
					} else {
						Players.sendMessage(player, Messages.invalidCommandUsage("page number"));
					}
				}
				helpScreen.sendTo(player, page, 7);
			} else {
				//Send the player a message saying the enchantment doesn't exist
				Players.sendMessage(player, Messages.invalidEnchantment(enchantmentName));
			}
		} else {
			Players.sendMessage(player, Messages.invalidCommandUsage("enchantment", "level"));
			Players.sendMessage(player, "&7Use &o/enchant list&r&7 to get a list of enchantments");
		}
	}
}
