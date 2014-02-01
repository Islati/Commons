package com.caved_in.commons.commands;

import com.caved_in.commons.chat.ChatHandler;
import com.caved_in.commons.chat.ChatMessage;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 8:30 PM
 */
public class QuickResponseCommand {
	@CommandController.CommandHandler(name = "r", usage = "/r <Message>"/*, permission = "tunnels.common.message"*/)
	public void quickRespondMessage(Player player, String[] commandArgs) {
		if (commandArgs.length > 0) {
			String playerName = player.getName();
			if (ChatHandler.hasRecentChatter(playerName)) {
				String playerToSendMessageTo = ChatHandler.getRecentChatter(playerName);
				String playerMessage = StringUtil.joinString(commandArgs, " ");

				if (PlayerHandler.isOnline(playerToSendMessageTo)) {
					Player playerSendingTo = PlayerHandler.getPlayer(playerToSendMessageTo);
					PlayerHandler.sendMessage(playerSendingTo, "&r[&e" + player.getName() + "&b -> &aYou&r] " + playerMessage);
					PlayerHandler.sendMessage(player, "&r[&eYou&b -> &a" + playerSendingTo.getName() + "&r] " + playerMessage);
					ChatHandler.setRecentChatter(playerSendingTo.getName(), new ChatMessage(player.getName(), playerSendingTo.getName()));
				} else {
					PlayerHandler.sendMessage(player, "&e" + playerToSendMessageTo + "&c has gone offline");
				}
			} else {
				PlayerHandler.sendMessage(player, "&cYou havn't received any messages from anybody");
			}
		}
	}
}
