package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 5:07 PM
 */
public class HealCommand {
	@CommandController.CommandHandler(name = "heal", usage = "/heal", permission = "tunnels.common.heal")
	public void onHealCommand(Player player, String[] args) {
		Player playerToHeal = player;
		boolean healOther = false;

		//Check if there was a name passed
		if (args.length > 0) {
			String playerArg = args[0];
			//Assure the player requested is online
			if (Players.isOnline(playerArg)) {
				playerToHeal = Players.getPlayer(playerArg);
				healOther = true;
			} else {
				Players.sendMessage(player, Messages.playerOffline(playerArg));
				return;
			}
		}
		//Remove the potion effects on the player
		Players.removePotionEffects(playerToHeal);
		//Heal them to full health
		Entities.setHealth(playerToHeal, Entities.getMaxHealth(playerToHeal));
		//Send them a message saying they were healed
		if (!healOther) {
			Players.sendMessage(player, Messages.PLAYER_HEALED);
		} else {
			Players.sendMessage(playerToHeal, Messages.PLAYER_HEALED);
			Players.sendMessage(player, Messages.playerHealed(playerToHeal.getName()));
		}
	}
}
