package com.caved_in.commons.command.commands;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.ChatMessage;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class MessageCommand {
	@Command(identifier = "msg", permissions = {Perms.COMMAND_MESSAGE})
	public void onMessageCommand(Player player, @Arg(name = "receiver") Player target, @Wildcard @Arg(name = "message") String message) {
		messagePlayer(target, player, message);
	}

	private void messagePlayer(Player playerSendingTo, Player playerSendingFrom, String message) {
		Players.sendMessage(playerSendingTo, "&f[&e" + playerSendingFrom.getDisplayName() + "&b -> &aYou&f] " + message);
		Players.sendMessage(playerSendingFrom, "&f[&eYou &b-> &a" + playerSendingTo.getDisplayName() + "&f] " + message);
		Chat.setRecentPrivateMessageFrom(playerSendingTo.getName(), new ChatMessage(playerSendingFrom.getName(), playerSendingTo.getName()));
	}
}
