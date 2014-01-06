package com.caved_in.commons.friends;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendList {
	private String playerName = "";
	private Map<String, Friend> playerFriends = new HashMap<String, Friend>();

	public FriendList(String playerName) {
		this.playerName = playerName;
	}

	public FriendList(String playerName, List<Friend> playerFriends) {
		this.playerName = playerName;
		for (Friend friend : playerFriends) {
			this.playerFriends.put(friend.getFriendName(), friend);
		}
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public boolean isFriendsWith(String name) {
		return this.playerFriends.containsKey(name) && this.playerFriends.get(name).isAccepted();
	}

	public void addFriend(Friend friendToAdd) {
		this.playerFriends.put(friendToAdd.getFriendName(), friendToAdd);
	}

	public void removeFriend(String name) {
		this.playerFriends.remove(name);
	}

	public List<Friend> getFriends() {
		return new ArrayList<Friend>(playerFriends.values());
	}

	public List<Friend> getUnacceptedFriends() {
		List<Friend> unacceptedFriends = new ArrayList<Friend>();
		for (Friend friend : getFriends()) {
			if (!friend.isAccepted()) {
				unacceptedFriends.add(friend);
			}
		}
		return unacceptedFriends;
	}

	public Map<String, Friend> getFriendsMap() {
		return this.playerFriends;
	}
}
