package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.ChatMessage;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 8:28 PM
 */
public class MessageCommand {
	@CommandController.CommandHandler(name = "msg", usage = "/msg <player> <msg> to send a player a message", aliases = {"m", "tell", "t", "whisper"}/*permission = "tunnels.common.message"*/)
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
				Players.sendMessage(Player, Messages.INVALID_COMMAND_USAGE("message"));
			}
		} else {
			Players.sendMessage(Player, Messages.INVALID_COMMAND_USAGE("player", "message"));
		}
	}

	private void messagePlayer(Player playerSendingTo, Player playerSendingFrom, String message) {
		Players.sendMessage(playerSendingTo, "&f[&e" + playerSendingFrom.getDisplayName() + "&b -> &aYou&f] " + message);
		Players.sendMessage(playerSendingFrom, "&f[&eYou &b-> &a" + playerSendingTo.getDisplayName() + "&f] " + message);
		Chat.setRecentChatter(playerSendingTo.getName(), new ChatMessage(playerSendingFrom.getName(), playerSendingTo.getName()));
	}
}
