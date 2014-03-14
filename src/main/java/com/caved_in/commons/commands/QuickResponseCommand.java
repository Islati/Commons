package com.caved_in.commons.commands;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.ChatMessage;
import com.caved_in.commons.player.Players;
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
			if (Chat.hasRecentChatter(playerName)) {
				String playerToSendMessageTo = Chat.getRecentChatter(playerName);
				String playerMessage = StringUtil.joinString(commandArgs, " ");

				if (Players.isOnline(playerToSendMessageTo)) {
					Player playerSendingTo = Players.getPlayer(playerToSendMessageTo);
					Players.sendMessage(playerSendingTo, "&r[&e" + player.getName() + "&b -> &aYou&r] " + playerMessage);
					Players.sendMessage(player, "&r[&eYou&b -> &a" + playerSendingTo.getName() + "&r] " + playerMessage);
					Chat.setRecentChatter(playerSendingTo.getName(), new ChatMessage(player.getName(), playerSendingTo.getName()));
				} else {
					Players.sendMessage(player, "&e" + playerToSendMessageTo + "&c has gone offline");
				}
			} else {
				Players.sendMessage(player, "&cYou havn't received any messages from anybody");
			}
		}
	}
}
