package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.ChatMessage;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

import java.util.Set;

public class QuickResponseCommand {

	@Command(identifier = "r", permissions = Perms.COMMAND_MESSAGE)
	public void quickRespondMessage(Player player, @Wildcard @Arg(name = "message") String message) {
		String playerName = player.getName();

		if (message == null) {
			Players.sendMessage(player, Messages.MESSAGE_REQUIRED);
			return;
		}

		if (!Chat.hasRecentPrivateMessageFrom(playerName)) {
			Players.sendMessage(player, Messages.NO_RECENT_MESSAGES);
			return;
		}

		String receiver = Chat.getMostRecentPrivateMessager(playerName);

		if (!Players.isOnline(receiver)) {
			Players.sendMessage(player, Messages.playerOffline(receiver));
			return;
		}

		Player playerSendingTo = Players.getPlayer(receiver);


		Players.sendMessage(playerSendingTo, "&r[&e" + player.getName() + "&b -> &aYou&r] " + message);
		Players.sendMessage(player, "&r[&eYou&b -> &a" + playerSendingTo.getName() + "&r] " + message);
		Chat.setRecentPrivateMessageFrom(playerSendingTo.getName(), new ChatMessage(player.getName(), playerSendingTo.getName()));
	}
}
