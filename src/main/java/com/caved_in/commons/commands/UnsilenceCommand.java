package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

public class UnsilenceCommand {
	@CommandController.CommandHandler(name = "unsilence", permission = "tunnels.common.silence")
	public void unsilenceLobbyCommand(CommandSender sender, String[] args) {
		Commons.getConfiguration().getWorldConfig().setChatSilenced(false);
		Players.messageAll(Messages.CHAT_UNSILENCED);
	}
}
