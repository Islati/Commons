package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.commands.HelpMenus;
import com.caved_in.commons.items.Enchantments;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.player.PlayerHandler;
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
	@CommandController.CommandHandler(name = "enchant", permission = "tunnels.common.enchant")
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
					if (ItemHandler.addEnchantment(itemStack, enchantment, enchantmentLevel, true)) {
						PlayerHandler.sendMessage(player, Messages.ITEM_ENCHANTED(enchantmentName, enchantmentLevel));
					} else {
						//Send them a message saying it failed
						PlayerHandler.sendMessage(player, Messages.FAILED_TO_ENCHANT_ITEM);
					}
				} else {
					//Send the player a message saying they require an item in their hand
					PlayerHandler.sendMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
				}
			} else if (enchantmentName.equalsIgnoreCase("list")) {
				HelpScreen helpScreen = HelpMenus.generateHelpScreen(ChatColor.GOLD + "Enchantments list", HelpMenus.PageDisplay.DEFAULT, HelpMenus.ItemFormat.SINGLE_DASH, ChatColor.GREEN, ChatColor.DARK_GREEN);
				for (Enchantments enchantments : Enchantments.values()) {
					helpScreen.setEntry(enchantments.getMainAlias(), StringUtil.joinString(enchantments.getAliases(), ", "));
				}
				int page = 1;
				if (args.length > 1) {
					String pageArgument = args[1];
					if (StringUtils.isNumeric(pageArgument)) {
						page = Integer.parseInt(pageArgument);
					} else {
						PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("page number"));
					}
				}
				helpScreen.sendTo(player, page, 7);
			} else {
				//Send the player a message saying the enchantment doesn't exist
				PlayerHandler.sendMessage(player, Messages.ENCHANTMENT_DOESNT_EXIST(enchantmentName));
			}
		} else {
			PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("enchantment", "level"));
			PlayerHandler.sendMessage(player, "&7Use &o/enchant list&r&7 to get a list of enchantments");
		}
	}
}
