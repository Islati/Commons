package com.caved_in.commons.friends;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FriendStatus {
	REQUESTED("Requested", 1),
	ACCEPTED("Accepted", 2),
	DENIED("Request denied", 3),
	BLOCKED("Blocked", 4),
	ALREADY_FRIENDS("Already friends", 5),
	ALREADY_PENDING("Already pending", 6),
	NO_REQUEST("No request", 7),
	ERROR("Error", 8);

	private static Map<Integer, FriendStatus> friendStatusMap = new HashMap<>();

	static {
		for (FriendStatus friendStatus : EnumSet.allOf(FriendStatus.class)) {
			friendStatusMap.put(friendStatus.id, friendStatus);
		}
	}

	private String status = "";
	private int id;

	FriendStatus(String status, int id) {
		this.status = status;
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public static boolean isValidFriendStatus(int id) {
		return friendStatusMap.containsKey(id);
	}

	public static FriendStatus getStatusById(int id) {
		return friendStatusMap.get(id);
	}
}
