package com.caved_in.commons.friends;

import com.google.common.collect.Sets;

import java.util.*;

import static ch.lambdaj.Lambda.*;
import static org.hamcrest.Matchers.is;

public class FriendList {
	private UUID playerId;
	private Map<UUID, Friend> playerFriends = new HashMap<>();
	private Set<Friend> unacceptedFriends = null;
	private boolean modified = false;

	public FriendList(UUID playerId) {
		this.playerId = playerId;
	}

	public FriendList(UUID playerId, Collection<Friend> playerFriends) {
		this.playerId = playerId;
		//Create an indexed map of playerFriends of the Friend class where the key is the friends name
		this.playerFriends = index(playerFriends, on(Friend.class).getFriendId());
	}

	public UUID getPlayerId() {
		return playerId;
	}

	public boolean isFriendsWith(UUID id) {
		return playerFriends.containsKey(id) && playerFriends.get(id).isAccepted();
	}

	public void addFriend(Friend friendToAdd) {
		playerFriends.put(friendToAdd.getFriendId(), friendToAdd);
		modified = true;
	}

	public void removeFriend(UUID id) {
		playerFriends.remove(id);
		modified = true;
	}

	public void acceptFriend(UUID id) {
		playerFriends.get(id).setAccepted(true);
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

	public boolean hasRequest(UUID playerId) {
		return playerFriends.containsKey(playerId) && !playerFriends.get(playerId).isAccepted();
	}

}
