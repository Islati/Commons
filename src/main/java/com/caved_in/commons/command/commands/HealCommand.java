package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 5:07 PM
 */
public class HealCommand {
	@Command(name = "heal", usage = "/heal", permission = "commons.command.heal")
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

		//Heal them of all ailments and restore their health
		Players.heal(playerToHeal);
		//Send them a message saying they were healed
		if (!healOther) {
			Players.sendMessage(player, Messages.PLAYER_HEALED);
		} else {
			Players.sendMessage(playerToHeal, Messages.PLAYER_HEALED);
			Players.sendMessage(player, Messages.playerHealed(playerToHeal.getName()));
		}
	}
}
