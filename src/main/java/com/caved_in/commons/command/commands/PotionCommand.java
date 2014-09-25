package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.potion.PotionType;
import com.caved_in.commons.potion.Potions;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;

public class PotionCommand {
	//TODO Make menu where players can select potion types, and then a subsequent menu where they
	//select the level of the potion.
	@Command(name = "potion", permission = "commons.command.potion")
	public void onPotionCommand(Player player, String[] args) {
		if (args.length == 0) {
			Players.sendMessage(player, Messages.invalidCommandUsage("potion type", "&7optional:&c amplification"));
			return;
		}

		String potionType = args[0];
		if (!PotionType.isPotionType(potionType)) {
			Players.sendMessage(player, Messages.INVALID_POTION_TYPE);
			return;
		}

		int effectLevel = StringUtil.getNumberAt(args, 1, 1);

		Players.addPotionEffect(player, Potions.getPotionEffect(PotionType.getPotionType(potionType).getPotionEffectType(), effectLevel, Integer.MAX_VALUE));
	}
}
