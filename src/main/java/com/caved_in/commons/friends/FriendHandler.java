package com.caved_in.commons.friends;

import com.caved_in.commons.Commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendHandler {
	private static Map<String, FriendList> friendsLists = new HashMap<String, FriendList>();

	public static void addFriendList(final String playerName) {
		List<Friend> playerFriends = new ArrayList<Friend>();

		if (Commons.friendDatabase.hasData(playerName)) {
			playerFriends = Commons.friendDatabase.getFriends(playerName);
		}
		friendsLists.put(playerName, new FriendList(playerName, playerFriends));
	}

	public static void removeFriendList(String playerName) {
		friendsLists.remove(playerName);
	}

	public static FriendList getFriendList(String playerName) {
		return friendsLists.get(playerName);
	}
}
