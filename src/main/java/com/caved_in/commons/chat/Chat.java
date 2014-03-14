package com.caved_in.commons.chat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brandon Curtis
 * @version 1.0
 * @since 1.0
 */
public class Chat {
	private static Map<String, ChatMessage> recentChatters = new HashMap<String, ChatMessage>();

	/**
	 * Checks whether a player has a recent chat message
	 *
	 * @param playerName player to check recent messages for
	 * @return true if there's a recent chat message for this player, false otherwise
	 */
	public static boolean hasRecentChatter(String playerName) {
		return recentChatters.containsKey(playerName);
	}

	/**
	 * Sets the chatmessage for a player
	 *
	 * @param playerFor   player to set the recent chat-message for
	 * @param chatMessage chatmessage to set for the player
	 */
	public static void setRecentChatter(String playerFor, ChatMessage chatMessage) {
		recentChatters.put(playerFor, chatMessage);
	}

	/**
	 * Gets the person who sent the most recent message to the requested player
	 *
	 * @param playerFor player to get the recent chatter for
	 * @return name of the recent chatter; null if none exists
	 */
	public static String getRecentChatter(String playerFor) {
		return recentChatters.get(playerFor).getPlayerSendingMessage();
	}
}
