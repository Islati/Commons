package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.ArmorSet;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * User: brandon
 */
public class ArmorCommand {

	@Command(
			identifier = "armor",
			onlyPlayers = true,
			description = "Equip yourself with a full set of armor!",
			permissions = {Perms.COMMAND_ARMOR}
	)
	public void armorCommand(Player sender, @Arg(name = "armor_type") String armorType) {
		ArmorSet set = ArmorSet.getSetByName(armorType);
		if (set == null) {
			Players.sendMessage(sender, Messages.invalidArmorSet(armorType));
			return;
		}

		Players.setArmor(sender, set.getArmor());
	}
}
