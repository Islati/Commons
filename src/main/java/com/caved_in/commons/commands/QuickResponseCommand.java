package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
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
		String playerName = player.getName();
		if (!Chat.hasRecentPrivateMessageFrom(playerName)) {
			Players.sendMessage(player, Messages.NO_RECENT_MESSAGES);
			return;
		}

		if (commandArgs.length == 0) {
			Players.sendMessage(player, Messages.MESSAGE_REQUIRED);
			return;
		}

		String playerToSendMessageTo = Chat.getMostRecentPrivateMessager(playerName);
		String playerMessage = StringUtil.joinString(commandArgs, " ");

		if (!Players.isOnline(playerToSendMessageTo)) {
			Players.sendMessage(player, Messages.playerOffline(playerToSendMessageTo));
			return;
		}

		Player playerSendingTo = Players.getPlayer(playerToSendMessageTo);


		Players.sendMessage(playerSendingTo, "&r[&e" + player.getName() + "&b -> &aYou&r] " + playerMessage);
		Players.sendMessage(player, "&r[&eYou&b -> &a" + playerSendingTo.getName() + "&r] " + playerMessage);
		Chat.setRecentPrivateMessageFrom(playerSendingTo.getName(), new ChatMessage(player.getName(), playerSendingTo.getName()));
	}
}
