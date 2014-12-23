package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

public class UnsilenceCommand {

	private static Configuration config = Commons.getInstance().getConfiguration();

	public UnsilenceCommand() {

	}

	@Command(identifier = "unsilence", permissions = Perms.COMMAND_SILENCE, onlyPlayers = false)
	public void unsilenceLobbyCommand(CommandSender sender) {
		config.getWorldConfig().setChatSilenced(false);
		Chat.messageAll(Messages.CHAT_UNSILENCED);
	}
}
