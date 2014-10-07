package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

public class UnsilenceCommand {
	@Command(identifier = "unsilence", permissions = "commons.command.silence", onlyPlayers = false)
	public void unsilenceLobbyCommand(CommandSender sender) {
		Commons.getConfiguration().getWorldConfig().setChatSilenced(false);
		Players.messageAll(Messages.CHAT_UNSILENCED);
	}
}
