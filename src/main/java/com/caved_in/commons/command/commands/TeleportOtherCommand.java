package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TeleportOtherCommand {
	@Command(identifier = "tpo", permissions = Perms.COMMAND_TELEPORT_OTHER, onlyPlayers = true)
	public void onTeleportOtherCommand(Player sender, @Arg(name = "player") Player p, @Arg(name = "target") Player target) {
		Players.teleport(p, target);
		Chat.message(p, Messages.playerTeleportedToPlayer(target.getName()));
	}
}
