package com.caved_in.commons.handlers.Friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendList
{
	private String playerName = "";
	private Map<String, Friend> playerFriends = new HashMap<String, Friend>();

	public FriendList(String playerName)
	{
		this.playerName = playerName;
	}

	public FriendList(String playerName, List<Friend> playerFriends)
	{
		this.playerName = playerName;
		for (Friend friend : playerFriends)
		{
			this.playerFriends.put(friend.getFriendName(), friend);
		}
	}

	public String getPlayerName()
	{
		return this.playerName;
	}

	public boolean isFriendsWith(String name)
	{
		if (this.playerFriends.containsKey(name))
		{
			return this.playerFriends.get(name).isAccepted();
		}
		return false;
	}

	public void addFriend(Friend friendToAdd)
	{
		this.playerFriends.put(friendToAdd.getFriendName(), friendToAdd);
	}

	public void removeFriend(String name)
	{
		if (isFriendsWith(name))
		{
			this.playerFriends.remove(name);
		}
	}

	public List<Friend> getFriends()
	{
		List<Friend> playerFriends = new ArrayList<Friend>();
		for (Friend friend : this.playerFriends.values())
		{
			if (friend.isAccepted())
			{
				playerFriends.add(friend);
			}
		}
		return playerFriends;
	}

	public List<Friend> getUnacceptedFriends()
	{
		List<Friend> friends = new ArrayList<Friend>();
		for (Friend friend : this.getFriends())
		{
			if (friend.isAccepted() == false)
			{
				friends.add(friend);
			}
		}
		return friends;
	}

	public Map<String, Friend> getFriendsMap()
	{
		return this.playerFriends;
	}
}
