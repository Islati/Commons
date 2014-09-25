package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.ArmorSet;
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

		ArmorSet set = ArmorSet.getSetByName(armorArg);
		if (set == null) {
			Players.sendMessage(player, Messages.invalidArmorSet(armorArg));
			return;
		}

		PlayerInventory inventory = player.getInventory();
		inventory.setArmorContents(set.getArmor());
	}
}
