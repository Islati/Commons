package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

/**
 * User: brandon
 */
public class ArmorCommand {
	@Command(name = "armor", permission = "commons.command.armor")
	public void onArmorCommand(Player player, String[] args) {
		if (args.length == 0) {
			Players.sendMessage(player, Messages.invalidCommandUsage("armor type [leather, iron, gold, diamond]"));
			return;
		}

		String armorArg = args[0];
		PlayerInventory inventory = player.getInventory();
		switch (armorArg.toLowerCase()) {
			case "leather":
				inventory.setArmorContents(Items.LEATHER_ARMOR);
				break;
			case "iron":
				inventory.setArmorContents(Items.IRON_ARMOR);
				break;
			case "gold":
				inventory.setArmorContents(Items.GOLD_ARMOR);
				break;
			case "diamond":
				inventory.setArmorContents(Items.DIAMOND_ARMOR);
				break;
		}
	}
}
