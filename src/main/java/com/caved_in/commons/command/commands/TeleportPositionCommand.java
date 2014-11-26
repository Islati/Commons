package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TeleportPositionCommand {
	@Command(identifier = "tppos", permissions = Perms.COMMAND_TELEPORT_POSITION)
	public void onTeleportPositionCommand(Player player, @Arg(name = "x") double x, @Arg(name = "y") double y, @Arg(name = "z") double z) {
		Players.teleport(player, new double[]{x, y, z});
		Players.sendMessage(player, Messages.playerTeleportedTo(String.format("%sx %sy %sz", x, y, z)));

	}
}
