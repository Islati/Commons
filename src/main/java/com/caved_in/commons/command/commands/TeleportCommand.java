package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportCommand {
	@Command(name = "tp", permission = "commons.commands.teleport", aliases = {"teleport"})
	public void onTeleportCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Players.sendMessage(sender, Messages.invalidCommandUsage("player", "target"));
			return;
		}
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
					Players.sendMessage(player, Messages.playerOffline(playerName));
				}
			} else {
				Players.sendMessage(sender, Messages.invalidCommandUsage("player", "target"));
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
					Players.sendMessage(sender, Messages.playerTeleportedTo(playerOne, playerTwo));
				} else {
					Players.sendMessage(sender, Messages.playerOffline(playerTwo));
				}

			} else {
				Players.sendMessage(sender, Messages.playerOffline(playerOne));
			}
		}
	}
}
