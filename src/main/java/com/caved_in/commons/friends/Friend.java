package com.caved_in.commons.friends;

public class Friend {
	private String friendName = "";
	private String friendOf = "";
	private long lastSeenOnline = 0L;
	private boolean isAccepted = false;

	/**
	 * @param player     name of the player who's friend this is
	 * @param friendName name of the players friend
	 * @param isAccepted if the friend is accepted or not
	 */
	public Friend(String player, String friendName, boolean isAccepted) {
		this.friendOf = player;
		this.friendName = friendName;
		this.isAccepted = isAccepted;
	}

	/**
	 * Creates a new friend object where the accepted status is defaulted to false
	 *
	 * @param player     name of the player who's friend this is
	 * @param friendName name of the players friend
	 */
	public Friend(String player, String friendName) {
		this(player, friendName, false);
	}

	public String getFriendName() {
		return this.friendName;
	}

	public String getFriendOf() {
		return this.friendOf;
	}

	public boolean isAccepted() {
		return this.isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}
}
