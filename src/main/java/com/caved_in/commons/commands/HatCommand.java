package com.caved_in.commons.commands;

import com.caved_in.commons.item.ItemType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 8:26 PM
 */
public class HatCommand {
	@CommandController.CommandHandler(name = "hat", usage = "/hat <ItemID/ItemName> to place an item on your head (overwriting your current item); /hat to remove it", permission = "tunnels.common.hat")
	public void HatCommand(Player player, String[] commandArgs) {
		Material hatMaterial = null;
		if (commandArgs.length >= 1) {
			if (commandArgs[0] != null) {
				if (StringUtils.isNumeric(commandArgs[0])) {
					hatMaterial = Material.getMaterial(Integer.parseInt(commandArgs[0]));
				} else {
					ItemType itemType = ItemType.lookup(commandArgs[0]);
					if (itemType != null) {
						hatMaterial = Material.getMaterial(itemType.getID());
					} else {
						player.sendMessage(ChatColor.RED + "Please enter a valid Item Name");
						return;
					}
				}
			}
		}
		if (hatMaterial == null) {
			if (player.getItemInHand() != null) {
				if (player.getInventory().getHelmet() != null) {
					if (player.getInventory().firstEmpty() != -1) {
						player.getInventory().addItem(player.getInventory().getHelmet());
						player.getInventory().setHelmet(player.getItemInHand());
						player.getInventory().setItemInHand(null);
						player.sendMessage(ChatColor.GREEN + "Enjoy your Helmet!");
					} else {
						player.sendMessage(ChatColor.RED + "Please make sure you have room in your inventory for your current helmet before attempting to equip another");
					}
				} else {
					player.getInventory().setHelmet(player.getItemInHand());
					player.getInventory().setItemInHand(null);
					player.sendMessage(ChatColor.GREEN + "Enjoy your Helmet!");
				}
			} else {
				if (player.getInventory().getHelmet() != null) {
					if (player.getInventory().firstEmpty() != -1) {
						player.getInventory().setItemInHand(player.getInventory().getHelmet());
						player.getInventory().setHelmet(null);
						player.sendMessage(ChatColor.GREEN + "You've unequipped your helmet.");
					} else {
						player.sendMessage(ChatColor.RED + "Please make sure you have room in your inventory for your current helmet before attempting to unequip it");
					}
				} else {
					player.sendMessage(ChatColor.YELLOW + "The correct usage is /hat <ItemID/ItemName> to place an item on your head (overwriting your current item); /hat to remove it ");
					player.sendMessage(ChatColor.YELLOW + "You're also able to use /hat if you've got an item you wish to equip in your hand, and space for your currently equipped one (if so)");
				}
			}
		} else {
			if (player.getInventory().getHelmet() != null) {
				if (player.getInventory().firstEmpty() != -1) {
					player.getInventory().addItem(player.getInventory().getHelmet());
					player.getInventory().setHelmet(new ItemStack(hatMaterial, 1));
					player.sendMessage(ChatColor.GREEN + "Enjoy your Helmet");
				} else {
					player.sendMessage(ChatColor.RED + "Please make sure you have room in your inventory for your current helmet before attempting to unequip it");
				}
			} else {
				player.getInventory().setHelmet(new ItemStack(hatMaterial, 1));
				player.sendMessage(ChatColor.GREEN + "Enjoy your Helmet");
			}
		}
	}
}
