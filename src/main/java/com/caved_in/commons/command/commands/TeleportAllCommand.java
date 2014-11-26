package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TeleportAllCommand {
	@Command(identifier = "tpall", permissions = Perms.COMMAND_TELEPORT_ALL)
	public void onTpallCommand(Player player) {
		String name = player.getName();

		for (Player onlinePlayer : Players.allPlayersExcept(player.getUniqueId())) {
			Players.teleport(onlinePlayer, player);
			Players.sendMessage(onlinePlayer, Messages.playerTeleportedToPlayer(name));
		}
	}
}
