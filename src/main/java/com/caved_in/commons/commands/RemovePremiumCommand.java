package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.command.CommandSender;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 8:03 PM
 */
public class RemovePremiumCommand {
	@CommandController.CommandHandler(name = "removepremium", description = "Used by the console to remove premium from players", permission = "tunnels.common.removepremium")
	public void removePlayerPremium(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String playerName = args[0];
			if (!Commons.playerDatabase.updatePlayerPremium(playerName, false)) {
				PlayerHandler.sendMessage(sender, Messages.PLAYER_DATA_NOT_FOUND(playerName));
			} else {
				PlayerHandler.sendMessage(sender, Messages.PREMIUM_STATUS_DEMOTED(playerName));
			}
		} else {
			PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("PlayerName"));
		}
	}
}
