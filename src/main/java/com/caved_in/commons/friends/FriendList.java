package com.caved_in.commons.friends;

import java.util.*;

public class FriendList {
	private String playerName = "";
	private Map<String, Friend> playerFriends = new HashMap<String, Friend>();

	public FriendList(String playerName) {
		this.playerName = playerName;
	}

	public FriendList(String playerName, Collection<Friend> playerFriends) {
		this.playerName = playerName;
		for (Friend friend : playerFriends) {
			this.playerFriends.put(friend.getFriendName(), friend);
		}
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public boolean isFriendsWith(String name) {
		return playerFriends.containsKey(name) && playerFriends.get(name).isAccepted();
	}

	public void addFriend(Friend friendToAdd) {
		playerFriends.put(friendToAdd.getFriendName(), friendToAdd);
	}

	public void removeFriend(String name) {
		playerFriends.remove(name);
	}

	public void acceptFriend(String name) {
		playerFriends.get(name).setAccepted(true);
	}

	public Collection<Friend> getFriends() {
		return playerFriends.values();
	}

	public Set<Friend> getUnacceptedFriends() {
		Set<Friend> unacceptedFriends = new HashSet<Friend>();
		for (Friend friend : getFriends()) {
			if (!friend.isAccepted()) {
				unacceptedFriends.add(friend);
			}
		}
		return unacceptedFriends;
	}

	public boolean hasRequest(String playerName) {
		return playerFriends.containsKey(playerName) && !playerFriends.get(playerName).isAccepted();
	}

	public Map<String, Friend> getFriendsMap() {
		return this.playerFriends;
	}
}
