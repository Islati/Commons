package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportHereCommand {
	@Command(name = "tphere", permission = "commons.command.tphere", aliases = {"bring"})
	public void onTeleportHereCommand(Player player, String[] args) {
		if (args.length > 0) {
			String playerName = args[0];
			if (Players.isOnline(playerName)) {
				Player playerToTeleport = Players.getPlayer(playerName);
				playerToTeleport.teleport(player, PlayerTeleportEvent.TeleportCause.COMMAND);
			} else {
				Players.sendMessage(player, Messages.playerOffline(playerName));
			}
		} else {
			Players.sendMessage(player, Messages.invalidCommandUsage("name"));
		}
	}
}
