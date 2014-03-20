package com.caved_in.commons.friends;

import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.is;

public class FriendList {
	private String playerName = "";
	private Map<String, Friend> playerFriends = new HashMap<String, Friend>();
	private Set<Friend> unacceptedFriends = null;
	private boolean modified = false;

	public FriendList(String playerName) {
		this.playerName = playerName;
	}

	public FriendList(String playerName, Collection<Friend> playerFriends) {
		this.playerName = playerName;
		//Create an indexed map of playerFriends of the Friend class where the key is the friends name
		this.playerFriends = index(playerFriends, on(Friend.class).getFriendName());
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public boolean isFriendsWith(String name) {
		return playerFriends.containsKey(name) && playerFriends.get(name).isAccepted();
	}

	public void addFriend(Friend friendToAdd) {
		playerFriends.put(friendToAdd.getFriendName(), friendToAdd);
		modified = true;
	}

	public void removeFriend(String name) {
		playerFriends.remove(name);
		modified = true;
	}

	public void acceptFriend(String name) {
		playerFriends.get(name).setAccepted(true);
		modified = true;
	}

	public Set<Friend> getFriends() {
		return Sets.newHashSet(playerFriends.values());
	}

	public Set<Friend> getUnacceptedFriends() {
		if (modified || unacceptedFriends == null) {
			unacceptedFriends = Sets.newHashSet(select(getFriends(), having(on(Friend.class).isAccepted(), is(true))));
			modified = false;
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
