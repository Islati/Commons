package com.caved_in.commons.command;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.potion.PotionType;
import com.caved_in.commons.potion.Potions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 31/01/14
 * Time: 9:56 PM
 */
public class PotionCommand {
	@CommandController.CommandHandler(name = "potion", permission = "tunnels.common.potion")
	public void onPotionCommand(Player player, String[] args) {
		if (args.length > 0) {
			String potionArg = args[0];
			if (PotionType.isPotionType(potionArg)) {
				int effectLevel = 1;
				if (args.length > 1) {
					String levelArg = args[1];
					if (StringUtils.isNumeric(levelArg)) {
						effectLevel = Integer.parseInt(levelArg);
					} else {
						Players.sendMessage(player, Messages.INVALID_COMMAND_USAGE("amplification"));
					}
				}
				Players.addPotionEffect(player, Potions.getPotionEffect(PotionType.getPotionType(potionArg).getPotionEffectType(), effectLevel, Integer.MAX_VALUE));
			} else {
				Players.sendMessage(player, Messages.INVALID_POTION_TYPE);
			}
		} else {
			Players.sendMessage(player, Messages.INVALID_COMMAND_USAGE("potion type"));
		}
	}
}
