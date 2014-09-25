package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.ChatMessage;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class MessageCommand {
	@Command(name = "msg", usage = "/msg <player> <msg> to send a player a message", aliases = {"m", "tell", "t", "whisper"}, permission = "commons.messages.send")
	public void onMessageCommand(Player Player, String[] args) {
		if (args.length > 0) {
			String receivingPlayer = args[0];
			if (args.length > 1) {
				String message = "";
				for (int I = 1; I < args.length; I++) {
					message += args[I] + " ";
				}

				if (Players.isOnline(receivingPlayer)) {
					messagePlayer(Players.getPlayer(receivingPlayer), Player, message);
				} else {
					Players.sendMessage(Player, "&cUnable to send a message to &e" + receivingPlayer + "&c, perhaps they're offline?");
				}
			} else {
				Players.sendMessage(Player, Messages.invalidCommandUsage("message"));
			}
		} else {
			Players.sendMessage(Player, Messages.invalidCommandUsage("player", "message"));
		}
	}

	private void messagePlayer(Player playerSendingTo, Player playerSendingFrom, String message) {
		Players.sendMessage(playerSendingTo, "&f[&e" + playerSendingFrom.getDisplayName() + "&b -> &aYou&f] " + message);
		Players.sendMessage(playerSendingFrom, "&f[&eYou &b-> &a" + playerSendingTo.getDisplayName() + "&f] " + message);
		Chat.setRecentPrivateMessageFrom(playerSendingTo.getName(), new ChatMessage(playerSendingFrom.getName(), playerSendingTo.getName()));
	}
}
