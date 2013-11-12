package com.caved_in.commons.handlers.Friends;

public class Friend
{
	private String friendName = "";
	private String friendOf = "";
	private long lastSeenOnline = 0L;
	private boolean isAccepted = false;

	/**
	 * 
	 * @param friendOf
	 *            Player whos friend this is
	 * @param friendName
	 *            Name of friend
	 * @param isAccepted
	 *            if they're accepted
	 */
	public Friend(String friendOf, String friendName, boolean isAccepted)
	{
		this.friendOf = friendOf;
		this.friendName = friendName;
		this.isAccepted = isAccepted;
	}

	public String getFriendName()
	{
		return this.friendName;
	}

	public String getFriendOf()
	{
		return this.friendOf;
	}

	public boolean isAccepted()
	{
		return this.isAccepted;
	}

	public void setAccepted(boolean isAccepted)
	{
		this.isAccepted = isAccepted;
	}
}
