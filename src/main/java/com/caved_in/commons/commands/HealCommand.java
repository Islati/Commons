package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.entity.EntityUtility;
import com.caved_in.commons.player.PlayerHandler;
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
		//Check if there was a name passed
		if (args.length > 0) {
			String playerArg = args[0];
			//Assure the player requested is online
			if (PlayerHandler.isOnline(playerArg)) {
				playerToHeal = PlayerHandler.getPlayer(playerArg);
			} else {
				PlayerHandler.sendMessage(player, Messages.PLAYER_OFFLINE(playerArg));
			}
		}
		//Remove the potion effects on the player
		PlayerHandler.removePotionEffects(playerToHeal);
		//Heal them to full health
		EntityUtility.setCurrentHealth(playerToHeal, EntityUtility.getMaxHealth(playerToHeal));
		//Send them a message saying they were healed
		PlayerHandler.sendMessage(playerToHeal, Messages.PLAYER_HEALED);
	}
}
