package com.caved_in.commons.chat;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

/**
 * Used to handle private messaging and message broadcasting.
 */
public class Chat {
	private static Map<String, ChatMessage> recentChatters = new HashMap<>();


	/**
	 * Checks whether a player has a recent chat message
	 *
	 * @param playerName player to check recent messages for
	 * @return true if there's a recent chat message for this player, false otherwise
	 */
	public static boolean hasRecentPrivateMessageFrom(String playerName) {
		return recentChatters.containsKey(playerName);
	}

	/**
	 * Sets the chatmessage for a player
	 *
	 * @param playerFor   player to set the recent chat-message for
	 * @param chatMessage chatmessage to set for the player
	 */
	public static void setRecentPrivateMessageFrom(String playerFor, ChatMessage chatMessage) {
		recentChatters.put(playerFor, chatMessage);
	}

	/**
	 * Gets the person who sent the most recent message to the requested player
	 *
	 * @param playerFor player to get the recent chatter for
	 * @return name of the recent chatter; null if none exists
	 */
	public static String getMostRecentPrivateMessager(String playerFor) {
		return recentChatters.get(playerFor).getPlayerSendingMessage();
	}

	/**
	 * Broadcast the messages passed, automagically formatting all color codes.
	 *
	 * @param messages
	 */
	public static void broadcast(String... messages) {
		for (String message : messages) {
			Bukkit.broadcastMessage(StringUtil.formatColorCodes(message));
		}
	}

	public static void message(CommandSender sender, String... messages) {
		Players.sendMessage(sender, messages);
	}

	public static void debug(String... message) {
		Commons c = Commons.getInstance();

		c.debug(message);
	}
}
