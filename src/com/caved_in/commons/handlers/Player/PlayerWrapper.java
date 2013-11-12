package com.caved_in.commons.handlers.Player;

import com.caved_in.commons.Commons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * User: Brandon
 */
public class PlayerWrapper
{
	private boolean inStaffChat = false;

	private String playerName = "";
	private String currentServer = "";

	private long lastOnline = 0L;

	private boolean isPremium = false;

	private double currencyAmount = 0.0D;

	private ChatColor tagColor = ChatColor.WHITE;

	/**
	 * vPlayer Initialization
	 * 
	 * @param playerName
	 */
	public PlayerWrapper(String playerName)
	{
		this.playerName = playerName;
		this.currentServer = Commons.getConfiguration().getServerName();
		this.lastOnline = System.currentTimeMillis();
	}

	/**
	 * VPLayer initialization with assigning their currency to
	 * <b>currencyAmount</b>
	 * 
	 * @param playerName
	 * @param currencyAmount
	 */
	public PlayerWrapper(String playerName, double currencyAmount)
	{
		this.playerName = playerName;
		this.currentServer = Commons.getConfiguration().getServerName();
		this.lastOnline = System.currentTimeMillis();
		this.currencyAmount = currencyAmount;
	}

	/**
	 * VPlayer Initialization
	 * 
	 * @param player
	 */
	public PlayerWrapper(Player player)
	{
		this.playerName = player.getName();
		this.currentServer = Commons.getConfiguration().getServerName();
		this.lastOnline = System.currentTimeMillis();
	}

	/**
	 * VPLayer initialization with assigning their currency to
	 * <b>currencyAmount</b>
	 * 
	 * @param player
	 * @param currencyAmount
	 */
	public PlayerWrapper(Player player, double currencyAmount)
	{
		this.playerName = player.getName();
		this.currentServer = Commons.getConfiguration().getServerName();
		this.lastOnline = System.currentTimeMillis();
		this.currencyAmount = currencyAmount;
	}

	/**
	 * Get the instanced players name
	 * 
	 * @return
	 */
	public String getName()
	{
		return this.playerName;
	}

	/**
	 * If the player is currently online on the server with this plugin
	 * instanced
	 * 
	 * @return
	 */
	public boolean isOnline()
	{
		return Bukkit.getPlayer(this.getName()) != null;
	}

	/**
	 * Move this player to-or-from Staff Chat
	 * 
	 * @param Value
	 */
	public void setInStaffChat(boolean Value)
	{
		this.inStaffChat = Value;
	}

	/**
	 * Is the player currently talking in staff chat
	 * 
	 * @return
	 */
	public boolean isInStaffChat()
	{
		return this.inStaffChat;
	}

	/**
	 * Add the amount defined by <b>amountToAdd</b> to the instanced player
	 * 
	 * @param amountToAdd
	 * @return The players currency after having <b>amountToAdd</b> added to it
	 */
	public double addCurrency(double amountToAdd)
	{
		this.currencyAmount += amountToAdd;
		return this.currencyAmount;
	}

	/**
	 * Remove the amount defined by <b>amountToRemove</b> from the instanced
	 * player
	 * 
	 * @param amountToRemove
	 * @return The players currency after having <b>amountToRemove</b> from it
	 */
	public double removeCurrency(double amountToRemove)
	{
		this.currencyAmount -= amountToRemove;
		return this.currencyAmount;
	}

	/**
	 * Return if the player has atleast the amount of currency specified in
	 * <b>amount</b>
	 * 
	 * @param amount
	 * @return true if they player has atleast <b>amount</b>, false otherwise
	 */
	public boolean hasCurrency(double amount)
	{
		return this.currencyAmount >= amount;
	}

	/**
	 * Get the players current currency amount
	 * 
	 * @return Players current currency amount
	 */
	public double getCurrency()
	{
		return this.currencyAmount;
	}

	/**
	 * Set the players currency to <b>amountToSet</b>
	 * 
	 * @param amountToSet
	 */
	public void setCurrency(double amountToSet)
	{
		this.currencyAmount = amountToSet;
	}

	/**
	 * Get the players current server
	 * 
	 * @return
	 */
	public String getServer()
	{
		return this.currentServer;
	}

	/**
	 * Check if the player is a premium member
	 * @return
	 */
	public boolean isPremium()
	{
		return this.isPremium;
	}

	/**
	 * Set the users premium status
	 * @param isPremium
	 * @return
	 */
	public void setPremium(boolean isPremium)
	{
		this.isPremium = isPremium;
		Commons.playerDatabase.updatePlayerPremium(this);
	}

	public ChatColor getTagColor()
	{
		return tagColor;
	}

	public void setTagColor(ChatColor tagColor)
	{
		this.tagColor = tagColor;
	}
}
