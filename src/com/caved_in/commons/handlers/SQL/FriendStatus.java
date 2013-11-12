package com.caved_in.commons.handlers.SQL;

public enum FriendStatus
{
	ACCEPTED("Accepted"),
	DENIED("Request denied"),
	BLOCKED("Blocked"),
	NO_REQUEST("No request"),
	ERROR("Error"),
	REQUESTED("Requested"),
	ALREADY_FRIENDS("Already friends"),
	ALREADY_PENDING("Already pending");

	String message = "";

	FriendStatus(String message)
	{
		this.message = message;
	}
}
