package com.caved_in.commons.command;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:08 PM
 */
public class TeleportCommand {
	@CommandController.CommandHandler(name = "tp", permission = "tunnels.common.teleport", aliases = {"teleport"})
	public void onTeleportCommand(CommandSender sender, String[] args) {
		//Check if the command issuer only entered one name
		if (args.length == 1) {
			//Check if the issuer is a player
			if (sender instanceof Player) {
				Player player = (Player) sender;
				String playerName = args[0];
				//Check if the player requested is online
				if (Players.isOnline(playerName)) {
					//Teleport the player using the command to the player they're requesting
					player.teleport(Players.getPlayer(playerName), PlayerTeleportEvent.TeleportCause.COMMAND);
				} else {
					//Player is offline, send the message saying so
					Players.sendMessage(player, Messages.PLAYER_OFFLINE(playerName));
				}
			} else {
				Players.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("player", "target"));
			}
		} else if (args.length == 2) {
			String playerOne = args[0];
			String playerTwo = args[1];
			//If player one is online
			if (Players.isOnline(playerOne)) {
				//If player two is online
				if (Players.isOnline(playerTwo)) {
					//Teleport the first player to the second
					Players.teleport(Players.getPlayer(playerOne), Players.getPlayer(playerTwo));
					Players.sendMessage(sender, Messages.TELEPORTED_TO(playerOne, playerTwo));
				} else {
					Players.sendMessage(sender, Messages.PLAYER_OFFLINE(playerTwo));
				}

			} else {
				Players.sendMessage(sender, Messages.PLAYER_OFFLINE(playerOne));
			}
		}
	}
}
