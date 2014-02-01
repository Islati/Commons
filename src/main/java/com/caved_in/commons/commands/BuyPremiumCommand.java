package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.command.CommandSender;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:34 PM
 */
public class BuyPremiumCommand {
	@CommandController.CommandHandler(name = "buypremium", description = "Used by the console to give users premium for a set amount of time", permission = "tunnels.common.buypremium")
	public void buyPlayerPremium(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String playerName = args[0];
			if (!Commons.playerDatabase.updatePlayerPremium(playerName, true)) {
				PlayerHandler.sendMessage(sender, Messages.PLAYER_DATA_NOT_FOUND(playerName));
			} else {
				PlayerHandler.sendMessage(sender, Messages.PREMIUM_STATUS_PROMOTED(playerName));
			}
		} else {
			PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("name"));
		}
	}
}
