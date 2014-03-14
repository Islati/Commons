package com.caved_in.commons.command;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:56 PM
 */
public class UnbanCommand {
	@CommandController.CommandHandler(name = "unban", description = "Unban / pardon a user from their ban", permission = "tunnels.common.unban", usage = "/unban [Name]", aliases = {"pardon"})
	public void UnbanCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String playerToUnban = args[0];
			String pardonIssuer = "";

			if (sender instanceof Player) {
				pardonIssuer = ((Player) sender).getName();
			} else {
				pardonIssuer = "Console";
			}

			if (Commons.bansDatabase.pardonPlayer(playerToUnban, pardonIssuer)) {
				Players.sendMessage(sender, Messages.PLAYER_UNBANNED(playerToUnban, pardonIssuer));
			} else {
				Players.sendMessage(sender, Messages.PLAYER_NOT_BANNED(playerToUnban));
			}
		} else {
			Players.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("name"));
		}
	}
}
