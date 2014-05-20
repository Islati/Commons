package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

public class UnsilenceCommand {
	@Command(name = "unsilence", permission = "commons.command.silence")
	public void unsilenceLobbyCommand(CommandSender sender, String[] args) {
		Commons.getConfiguration().getWorldConfig().setChatSilenced(false);
		Players.messageAll(Messages.CHAT_UNSILENCED);
	}
}
