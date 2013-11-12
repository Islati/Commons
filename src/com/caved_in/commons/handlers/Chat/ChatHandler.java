package com.caved_in.commons.handlers.Chat;

import java.util.HashMap;
import java.util.Map;

public class ChatHandler
{
	private static Map<String, ChatMessage> recentChatters = new HashMap<String, ChatMessage>();

	public static boolean hasRecentChatter(String playerName)
	{
		return recentChatters.containsKey(playerName);
	}

	public static void setRecentChatter(String playerFor, ChatMessage chatMessage)
	{
		recentChatters.put(playerFor, chatMessage);
	}

	public static String getRecentChatter(String playerFor)
	{
		return recentChatters.get(playerFor).getPlayerSendingMessage();
	}
}
