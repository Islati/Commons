package com.caved_in.commons.command;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 8:07 PM
 */
public class UnsilenceCommand {
	@CommandController.CommandHandler(name = "unsilence", permission = "tunnels.common.silence")
	public void unsilenceLobbyCommand(CommandSender sender, String[] args) {
		Commons.getConfiguration().getWorldConfig().setChatSilenced(false);
		Players.sendMessageToAllPlayers(Messages.CHAT_UNSILENCED);
	}
}
