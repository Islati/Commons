package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TeleportHereCommand {
	@Command(identifier = "tphere", permissions = Perms.COMMAND_TELEPORT_HERE)
	public void onTeleportHereCommand(Player player, @Arg(name = "player") Player target) {
		Players.teleport(target, player);
		Players.sendMessage(target, Messages.playerTeleportedToPlayer(player.getName()));
	}
}
