package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:12 PM
 */
public class FeedCommand {
	@CommandController.CommandHandler(name = "feed", permission = "tunnels.common.feed")
	public void onFeedCommand(CommandSender sender, String[] args) {
		//Check if they entered a player name as an argument
		if (args.length > 0) {
			String playerName = args[0];
			//Check if the player name they entered is online
			if (PlayerHandler.isOnline(playerName)) {
				//Get the player and feed them
				Player player = PlayerHandler.getPlayer(playerName);
				PlayerHandler.feedPlayer(player);
				//Send messages saying the player requested was fed
				PlayerHandler.sendMessage(player, Messages.PLAYER_FED);
				PlayerHandler.sendMessage(sender, Messages.PLAYER_FED(playerName));
			} else {
				PlayerHandler.sendMessage(sender, Messages.PLAYER_OFFLINE(playerName));
			}
		} else {
			if (sender instanceof Player) {
				PlayerHandler.feedPlayer((Player) sender);
				PlayerHandler.sendMessage(sender, Messages.PLAYER_FED);
			} else {
				PlayerHandler.sendMessage(sender, Messages.PLAYER_COMMAND_SENDER_REQUIRED);
			}
		}
	}
}
