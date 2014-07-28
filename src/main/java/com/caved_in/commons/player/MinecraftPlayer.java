package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.threading.tasks.GetPlayerPunishmentsCallable;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Root(name = "Player")
public class MinecraftPlayer implements PlayerWrapper {
	@Element(name = "player-name")
	private String playerName = "";

	@Element(name = "last-online")
	private long lastOnline = 0L;

	@Element(name = "is-premium")
	private boolean isPremium = false;
	//	private FriendList friendsList;

	/* If the players in debug mode, they'll receive messages when they do practically anything. */
	@Element(name = "debug-mode")
	private boolean debugMode = false;

	@Element(name = "in-staff-chat")
	private boolean inStaffChat = false;

	@Element(name = "has-custom-walk-speed")
	private boolean customWalkSpeed = false;

	@Element(name = "has-custom-fly-speed")
	private boolean customFlySpeed = false;

	@Element(name = "hiding-other-players")
	private boolean hidingOtherPlayers = false;

	private boolean viewingRecipe = false;

	private double walkSpeed = 0.22;
	private double flySpeed = 0.1;

	private Set<Punishment> punishments = new HashSet<>();

	public static final double defaultWalkSpeed = 0.22;
	public static final double defaultFlySpeed = 0.1;

	private UUID id;

	private String currentServer = "";

	@Element(name = "currency-amount")
	private double currencyAmount = 0.0D;

	private ChatColor tagColor = ChatColor.WHITE;

	@Element(name = "prefix")
	private String prefix = "";

	/**
	 * Location the player was before their last teleport
	 */
	private Location preTeleportLocation;

	/**
	 * PlayerWrapper  initialization with assigning their currency to {@param currencyAmount}
	 *
	 * @param playerName     name of the player to be instanced
	 * @param currencyAmount currency the player has
	 */
	@Deprecated
	public MinecraftPlayer(String playerName, double currencyAmount) {
		this.playerName = playerName;
		this.currencyAmount = currencyAmount;
		initWrapper();
	}

	public MinecraftPlayer(UUID id) {
		this.id = id;
		this.playerName = Players.getPlayer(id).getName();
		initWrapper();
	}

	public void dispose() {
//		nametagEntity.die();
//		nametagEntity = null;
		punishments = null;
	}

	private void initWrapper() {
		currentServer = Commons.getConfiguration().getServerName();
		lastOnline = System.currentTimeMillis();
		id = Players.getPlayer(playerName).getUniqueId();
		if (!Commons.hasSqlBackend()) {
//			friendsList = new FriendList(id);
			//TODO: Assign default prefix to user
			return;
		}

		//Create an async future to get the punishments for this player (and load them into the player wrapper instance)
		ListenableFuture<Set<Punishment>> punishmentListenable = Commons.asyncExecutor.submit(new GetPlayerPunishmentsCallable(id));
		Futures.addCallback(punishmentListenable, new FutureCallback<Set<Punishment>>() {
			@Override
			public void onSuccess(Set<Punishment> punishmentSet) {
				if (punishmentSet == null) {
					return;
				}
				punishments = punishmentSet;
			}

			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
			}
		});

//Create an async future to get the UUID of the player
		/*
		ListenableFuture<UUID> playerIdListenable = Commons.asyncExecutor.submit(new CallableGetPlayerUuid(getName()));
		Futures.addCallback(playerIdListenable, new FutureCallback<UUID>() {
			@Override
			public void onSuccess(UUID uuid) {
				id = uuid;
			}

			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
			}
		});
		*/

//		prefix = Commons.database.getPrefix(this);
//
//		if (Commons.friendDatabase.hasData(playerName)) {
//			friendsList = new FriendList(id, Commons.friendDatabase.getFriends(playerName));
//		} else {
//			friendsList = new FriendList(id);
//		}
	}

	public Player getPlayer() {
		return Players.getPlayer(id);
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
		return Bukkit.getPlayer(id) != null;
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
		return currencyAmount;
	}

	/**
	 * Set the players amount of currency
	 *
	 * @param amount what the players currency is being set to
	 */
	public void setCurrency(double amount) {
		currencyAmount = amount;
	}

	/**
	 * Get the players current server
	 *
	 * @return
	 */
	public String getServer() {
		return currentServer;
	}

	/**
	 * Check if the player is a premium member
	 *
	 * @return
	 */
	public boolean isPremium() {
		return isPremium;
	}

	/**
	 * Set the users premium status
	 *
	 * @param isPremium
	 * @return
	 */
	public void setPremium(boolean isPremium) {
		if (!Commons.hasSqlBackend()) {
			return;
		}

		this.isPremium = isPremium;
//		Commons.database.updatePlayerPremium(this);
	}

	public ChatColor getTagColor() {
		return tagColor;
	}

	public void setTagColor(ChatColor tagColor) {
		this.tagColor = tagColor;
	}

//	/**
//	 * Gets the players friends list
//	 *
//	 * @return a FriendList object which contains the players friends; If there are no
//	 * Friend objects, then the friendslist is still returned though with no friend objects
//	 */
//	public FriendList getFriendsList() {
//		return friendsList;
//	}


	public boolean hasCustomWalkSpeed() {
		return customWalkSpeed;
	}

	public boolean hasCustomFlySpeed() {
		return customFlySpeed;
	}

	public double getWalkSpeed() {
		return walkSpeed;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setWalkSpeed(double walkSpeed) {
		this.walkSpeed = walkSpeed;
		getPlayer().setWalkSpeed((float) walkSpeed);
	}

	public double getFlySpeed() {
		return flySpeed;
	}

	public void setFlySpeed(double flySpeed) {
		this.flySpeed = flySpeed;
		getPlayer().setFlySpeed((float) flySpeed);
	}

	public boolean isViewingRecipe() {
		return viewingRecipe;
	}

	public void setViewingRecipe(boolean viewingRecipe) {
		this.viewingRecipe = viewingRecipe;
	}

	/**
	 * @return the players location prior to their most recent teleport
	 */
	public Location getPreTeleportLocation() {
		return preTeleportLocation;
	}

	/**
	 * @param preTeleportLocation
	 */
	public void setPreTeleportLocation(Location preTeleportLocation) {
		this.preTeleportLocation = preTeleportLocation;
	}

	public boolean isInDebugMode() {
		return debugMode;
	}

	public void setInDebugMode(boolean value) {
		debugMode = value;
	}


	@Override
	public UUID getId() {
		return id;
	}

	/**
	 * @return a set of the active punishments the player has
	 */
	public Set<Punishment> getPunishments() {
		return punishments;
	}

	public boolean isHidingOtherPlayers() {
		return hidingOtherPlayers;
	}

	public void setHidingOtherPlayers(boolean hidingOtherPlayers) {
		this.hidingOtherPlayers = hidingOtherPlayers;
	}
}
