package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TeleportAllCommand {
	@Command(name = "tpall", usage = "/tpall", permission = "commons.command.tpall")
	public void onTpallCommand(Player player, String[] args) {
		String playerName = player.getName();
		for (Player onlinePlayer : Players.allPlayers()) {
			if (!onlinePlayer.getName().equals(playerName)) {
				Players.teleport(onlinePlayer, player);
				Players.sendMessage(onlinePlayer, Messages.playerTeleportedToPlayer(playerName));
			}
		}
	}
}
