package com.caved_in.commons.commands.chat;

import com.caved_in.commons.chat.ChatHandler;
import com.caved_in.commons.chat.ChatMessage;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.entity.Player;

public class ChatCommands {
	@CommandHandler(name = "msg", usage = "/msg <player> <msg> to send a player a message", aliases = {"m", "tell", "t", "whisper"}/*permission = "tunnels.common.message"*/)
	public void Message(Player Player, String[] commandArgs) {
		if (commandArgs.length > 0 && !commandArgs[0].isEmpty()) {
			String receivingPlayer = commandArgs[0];
			if (!commandArgs[1].isEmpty()) {
				String message = "";
				for (int I = 1; I < commandArgs.length; I++) {
					message += commandArgs[I] + " ";
				}

				if (PlayerHandler.isOnline(receivingPlayer)) {
					messagePlayer(PlayerHandler.getPlayer(receivingPlayer), Player, message);
				} else {
					Player.sendMessage(StringUtil.formatColorCodes("&cUnable to send a message to &e" + receivingPlayer + "&c, perhaps they're offline?"));
				}
			}
		}
	}

	private void messagePlayer(Player playerSendingTo, Player playerSendingFrom, String message) {
		PlayerHandler.sendMessage(playerSendingTo, "&f[&e" + playerSendingFrom.getDisplayName() + "&b -> &aYou&f] " + message);
		PlayerHandler.sendMessage(playerSendingFrom, "&f[&eYou &b-> &a" + playerSendingTo.getDisplayName() + "&f] " + message);
		ChatHandler.setRecentChatter(playerSendingTo.getName(), new ChatMessage(playerSendingFrom.getName(), playerSendingTo.getName()));
	}

	@CommandHandler(name = "r", usage = "/r <Message>"/*, permission = "tunnels.common.message"*/)
	public void quickRespondMessage(Player player, String[] commandArgs) {
		if (commandArgs.length > 0) {
			String playerName = player.getName();
			if (ChatHandler.hasRecentChatter(playerName)) {
				String playerToSendMessageTo = ChatHandler.getRecentChatter(playerName);

				String playerMessage = "";
				for (int I = 0; I < commandArgs.length; I++) {
					playerMessage += commandArgs[I] + " ";
				}

				if (PlayerHandler.isOnline(playerToSendMessageTo)) {
					Player playerSendingTo = PlayerHandler.getPlayer(playerToSendMessageTo);
					playerSendingTo.sendMessage(StringUtil.formatColorCodes("&r[&e" + player.getName() + "&b -> &aYou&r] " + playerMessage));
					player.sendMessage(StringUtil.formatColorCodes("&r[&eYou&b -> &a" + playerSendingTo.getName() + "&r] " + playerMessage));
					ChatHandler.setRecentChatter(playerSendingTo.getName(), new ChatMessage(player.getName(), playerSendingTo.getName()));
				} else {
					player.sendMessage(StringUtil.formatColorCodes("&e" + playerToSendMessageTo + "&c has gone offline"));
				}
			} else {
				player.sendMessage(StringUtil.formatColorCodes("&cYou havn't received any messages from anybody"));
			}
		}
	}
}
