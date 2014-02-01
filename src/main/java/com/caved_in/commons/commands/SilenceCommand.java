package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.command.CommandSender;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 8:06 PM
 */
public class SilenceCommand {
	@CommandController.CommandHandler(name = "silence", permission = "tunnels.common.silence")
	public void silenceLobbyCommand(CommandSender sender, String[] args) {
		Commons.getConfiguration().getWorldConfig().setChatSilenced(true);
		PlayerHandler.sendMessageToAllPlayers(Messages.CHAT_SILENCED);
	}
}
