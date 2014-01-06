package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.friends.FriendList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * User: Brandon
 */
public class PlayerWrapper {
	private FriendList friendsList;
	private boolean inStaffChat = false;

	private String playerName = "";
	private String currentServer = "";

	private long lastOnline = 0L;

	private boolean isPremium = false;

	private double currencyAmount = 0.0D;

	private ChatColor tagColor = ChatColor.WHITE;

	/**
	 * PlayerWrapper  initialization with assigning their currency to
	 * <b>currencyAmount</b>
	 *
	 * @param playerName
	 * @param currencyAmount
	 */
	public PlayerWrapper(String playerName, double currencyAmount) {
		this.playerName = playerName;
		this.currencyAmount = currencyAmount;
		initWrapper();
	}

	private void initWrapper() {
		this.currentServer = Commons.getConfiguration().getServerName();
		this.lastOnline = System.currentTimeMillis();
		//Load the players friends list
		friendsList = Commons.friendDatabase.hasData(playerName) ? new FriendList(playerName, Commons.friendDatabase.getFriends(playerName)) : new FriendList(playerName);
	}

	/**
	 * Players username
	 *
	 * @return the players username
	 */
	public String getName() {
		return this.playerName;
	}

	/**
	 * Whether or not the player is online
	 *
	 * @return true if the player is online, false otherwise
	 */
	public boolean isOnline() {
		return Bukkit.getPlayer(this.getName()) != null;
	}

	/**
	 * Change if the player is in the staff chat
	 *
	 * @param inStaffChat true if they're in staff chat, false otherwise
	 */
	public void setInStaffChat(boolean inStaffChat) {
		this.inStaffChat = inStaffChat;
	}

	/**
	 * Whether or not the player's in the staff chat
	 *
	 * @return true if they are, false otherwise
	 */
	public boolean isInStaffChat() {
		return inStaffChat;
	}

	/**
	 * Adds an amount of currency defined by <i>amountToAdd</i> to the player
	 *
	 * @param amountToAdd how much currency to add to the player
	 * @return The players currency after having <i>amountToAdd</i> added to it
	 */
	public double addCurrency(double amountToAdd) {
		currencyAmount += amountToAdd;
		return currencyAmount;
	}

	/**
	 * Remove an amount of currency defined by <i>amountToRemove</i> from the player
	 *
	 * @param amountToRemove how much currency to remove from the player
	 * @return The players currency after having <i>amountToRemove</i> from it
	 */
	public double removeCurrency(double amountToRemove) {
		currencyAmount -= amountToRemove;
		return currencyAmount;
	}

	/**
	 * Check if the player has atleast the amount of currency defined by <i>amount</i>
	 *
	 * @param amount amount to check
	 * @return true if the player has the same or more than <i>amount</i>, false otherwise
	 */
	public boolean hasCurrency(double amount) {
		return currencyAmount >= amount;
	}

	/**
	 * @return Players current currency amount
	 */
	public double getCurrency() {
		return this.currencyAmount;
	}

	/**
	 * Set the players amount of currency
	 *
	 * @param amountToSet what the players currency is being set to
	 */
	public void setCurrency(double amountToSet) {
		this.currencyAmount = amountToSet;
	}

	/**
	 * Get the players current server
	 *
	 * @return
	 */
	public String getServer() {
		return this.currentServer;
	}

	/**
	 * Check if the player is a premium member
	 *
	 * @return
	 */
	public boolean isPremium() {
		return this.isPremium;
	}

	/**
	 * Set the users premium status
	 *
	 * @param isPremium
	 * @return
	 */
	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
		Commons.playerDatabase.updatePlayerPremium(this);
	}

	public ChatColor getTagColor() {
		return tagColor;
	}

	public void setTagColor(ChatColor tagColor) {
		this.tagColor = tagColor;
	}

	/**
	 * Gets the players friends list
	 *
	 * @return a FriendList object which contains the players friends; If there are no
	 * Friend objects, then the friendslist is still returned though with no friend objects
	 */
	public FriendList getFriendsList() {
		return friendsList;
	}
}
