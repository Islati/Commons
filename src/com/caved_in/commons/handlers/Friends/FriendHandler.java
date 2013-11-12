package com.caved_in.commons.handlers.Friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.caved_in.commons.Commons;

public class FriendHandler
{
	private static Map<String, FriendList> friendsLists = new HashMap<String, FriendList>();

	public static void addFriendList(final String playerName)
	{
		List<Friend> playerFriends = new ArrayList<Friend>();

		if (Commons.friendDatabase.hasData(playerName))
		{
			List<Friend> friendList = Commons.friendDatabase.getFriends(playerName);
			for (Friend friend : friendList)
			{
				playerFriends.add(friend);
			}
		}

		/*
		 * Commons.threadManager.runTaskAsynch(new Runnable() {
		 * 
		 * @Override public void run() { if
		 * (Commons.friendDatabase.hasData(playerName)) { List<Friend>
		 * friendList = Commons.friendDatabase.getFriends(playerName);
		 * for(Friend friend : friendList) { playerFriends.add(friend); }
		 * 
		 * } } });
		 */

		friendsLists.put(playerName, new FriendList(playerName, playerFriends));
	}

	public static void removeFriendList(String playerName)
	{
		if (friendsLists.containsKey(playerName))
		{
			friendsLists.remove(playerName);
		}
	}

	public static FriendList getFriendList(String playerName)
	{
		if (friendsLists.containsKey(playerName))
		{
			return friendsLists.get(playerName);
		}
		return null;
	}
}
