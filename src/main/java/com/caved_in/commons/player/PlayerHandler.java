package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Formatting.ColorCode;
import com.caved_in.commons.entity.EntityUtility;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.location.LocationHandler;
import com.caved_in.commons.potions.PotionHandler;
import com.caved_in.commons.potions.PotionType;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.warps.Warp;
import com.caved_in.commons.world.WorldHeight;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.Map;

public class PlayerHandler {
	private static final int MAX_BLOCK_TARGET_DISTANCE = 30;
	private static Map<String, PlayerWrapper> playerData = new HashMap<String, PlayerWrapper>();

	public static final int DEPTH_EQUILZE_NUMBER = 63;

	public static boolean hasData(String Name) {
		return playerData.containsKey(Name);
	}

	private static boolean hasData(Player player) {
		return playerData.containsKey(player.getName());
	}

	/**
	 * Gets the playerwrapper instance for a player with the given name
	 *
	 * @param playerName name of the player to get the playerwrapper instance for
	 * @return PlayerWrapper for the given player, otherwise null if none exists
	 * @see com.caved_in.commons.player.PlayerWrapper
	 * @since 1.0
	 */
	public static PlayerWrapper getData(String playerName) {
		return playerData.get(playerName);
	}

	/**
	 * Gets the playerwrapper instance for the given player
	 *
	 * @param player player to get the wrapped data for
	 * @return PlayerWrapper for the given player, otherwise null if none exists
	 * @see com.caved_in.commons.player.PlayerWrapper
	 * @since 1.0
	 */
	public static PlayerWrapper getData(Player player) {
		return playerData.get(player.getName());
	}

	/**
	 * Loads a {@link com.caved_in.commons.player.PlayerWrapper} for the given player.
	 * <p>
	 * If no data exists in the database for the given player, default data will be inserted
	 * into the database and an object will be returned based on the default data.
	 * </p>
	 *
	 * @param player player to initiate the data for
	 * @since 1.0
	 */
	public static void addData(Player player) {
		String playerName = player.getName();

		PlayerWrapper PlayerWrapper;

		if (Commons.playerDatabase.hasData(playerName)) {
			Commons.messageConsole("&a" + playerName + " has data, attempting to load it.");
			if (hasData(playerName)) {
				playerData.remove(playerName);
				Commons.messageConsole("&aRemoved pre-cached data for " + playerName);
			}
		} else {
			Commons.messageConsole("&e" + playerName + " has no data, inserting defaults.");
			Commons.playerDatabase.insertDefaults(playerName);
			Commons.messageConsole("&aInserted defaults for " + playerName + ", and loaded the default values");
		}
		PlayerWrapper = Commons.playerDatabase.getPlayerWrapper(playerName);
		PlayerWrapper.setTagColor(getNameTagColor(player));
		Commons.messageConsole("&aLoaded data for " + playerName);
		playerData.put(playerName, PlayerWrapper);
	}

	/**
	 * Synchronizes the playerwrapper object to their corresponding database entry
	 *
	 * @param playerWrapper wrapped player data to synchronize to the database
	 * @see com.caved_in.commons.player.PlayerWrapper
	 * @since 1.0
	 */
	public static void updateData(PlayerWrapper playerWrapper) {
		playerData.put(playerWrapper.getName(), playerWrapper);
		Commons.playerDatabase.syncPlayerWrapperData(playerWrapper);
	}

	/**
	 * Removes the {@link com.caved_in.commons.player.PlayerWrapper} object for a player.
	 * <p>
	 * Calling this method will synchronize the playerwrapper data to the database
	 * before removing it.
	 * </p>
	 *
	 * @param playerName name of the player to remove the data for
	 * @since 1.0
	 */
	public static void removeData(String playerName) {
		if (hasData(playerName)) {
			Commons.messageConsole("&aPreparing to sync " + playerName + "'s data to database");
			Commons.playerDatabase.syncPlayerWrapperData(playerData.get(playerName));
			playerData.remove(playerName);
			Commons.messageConsole("&a" + playerName + "'s data has been synchronized");
		}
	}

	/**
	 * Checks whether or not a player is online whos name is similar to {@param playerName}
	 *
	 * @param playerName name of the player to check whether-or-not they're online
	 * @return true if there's a player online with a similar name, false otherwise
	 * @see #isOnlineFuzzy(String)
	 * @see #isOnline(String, boolean)
	 * @see #isOnlineExact(String)
	 * @since 1.0
	 */
	public static boolean isOnline(String playerName) {
		return isOnline(playerName, false);
	}

	/**
	 * Checks if there's a player currently online whos name matches {@param playerName} exactly
	 *
	 * @param playerName name of the player to check whether-or-not they're online
	 * @return true if there's a player online with the request name, false otherwise
	 * @see Bukkit#getPlayerExact(String)
	 * @since 1.0
	 */
	public static boolean isOnlineExact(String playerName) {
		return Bukkit.getPlayerExact(playerName) != null;
	}

	/**
	 * Checks whether or not a player is online.
	 * <p>
	 * If doing an exact search, this method will call <a href="#isOnlineExact">isOnlineExact</a>
	 * otherwise it calls <a href="#isOnlineFuzzy">isOnlineFuzzy</a>
	 * </p>
	 *
	 * @param playerName name of the player to check whether-or-not they're online
	 * @param isExact    whether or not to do an exact, or fuzzy, search for the player
	 * @return true if a player with the requested name is online, false otherwise
	 * @see #isOnlineExact(String)
	 * @see #isOnlineFuzzy(String)
	 * @since 1.0
	 */
	public static boolean isOnline(String playerName, boolean isExact) {
		return isExact ? isOnlineExact(playerName) : isOnlineFuzzy(playerName);
	}

	/**
	 * Uses <a href="#getPlayer">getPlayer</a> to check if any online player matches the name requested.
	 *
	 * @param playerName name of the player to check whether-or-not they're online
	 * @return true if any player has a name relatively similar to the one requested, false otherwise
	 * @see Bukkit#getPlayerExact(String)
	 * @since 1.0
	 */
	public static boolean isOnlineFuzzy(String playerName) {
		return Bukkit.getPlayer(playerName) != null;
	}

	/**
	 * Gets an online player based on the name passed.
	 *
	 * @param playerName name of the player to get the player-object for
	 * @return Player whos name matched the name checked for; Null if they're not online/doesn't exist
	 * @see Bukkit#getPlayer(String)
	 * @since 1.0
	 */
	public static Player getPlayer(String playerName) {
		return Bukkit.getPlayer(playerName);
	}

	/**
	 * Gets a player based on the {@link com.caved_in.commons.player.PlayerWrapper} passed
	 *
	 * @param playerWrapper wrapped player object to get the actual player object of
	 * @return Player that was wrapped by the playerwrapper; null if there is no player online with matching credentials
	 * @see com.caved_in.commons.player.PlayerWrapper
	 * @since 1.0
	 */
	public static Player getPlayer(PlayerWrapper playerWrapper) {
		return getPlayer(playerWrapper.getName());
	}

	/**
	 * Gets a players exact name based on the partial name passed.
	 * <p>
	 * If there's no player with a name similar to the name passed currently online
	 * it returns the partial name passed
	 * </p>
	 *
	 * @param partialPlayerName Partial name of the player to get the full name of
	 * @return An exact players name if there's a player online which matches the partial name passed; Otherwise
	 * returns the partial name passed
	 * @see Bukkit#getPlayer(String)
	 * @since 1.0
	 */
	public static String getName(String partialPlayerName) {
		return isOnline(partialPlayerName) ? getPlayer(partialPlayerName).getName() : partialPlayerName;
	}

	/**
	 * Kick all players online with a specific reason
	 *
	 * @param reason reason to kick the players for
	 * @since 1.0
	 */
	public static void kickAllPlayers(String reason) {
		for (Player player : getOnlinePlayers()) {
			player.kickPlayer(StringUtil.formatColorCodes(reason));
		}
	}

	/**
	 * Kick all players without who lack a specific permission node.
	 *
	 * @param permission permission to check for on players
	 * @param reason     reason to kick the players for
	 * @since 1.0
	 */
	public static void kickAllPlayersWithoutPermission(String permission, String reason) {
		if (permission != null && !permission.isEmpty()) {
			for (Player player : getOnlinePlayers()) {
				if (!player.hasPermission(permission)) {
					player.kickPlayer(StringUtil.formatColorCodes(reason));
				}
			}
		} else {
			kickAllPlayers(reason);
		}
	}

	/**
	 * Sends messages to all online players
	 *
	 * @param messages messages to send
	 * @see #sendMessage(org.bukkit.command.CommandSender, String)
	 * @since 1.0
	 */
	public static void sendMessageToAllPlayers(String... messages) {
		for (Player player : getOnlinePlayers()) {
			sendMessage(player, messages);
		}
	}

	/**
	 * Sends a message to all online players.
	 *
	 * @param message message to send
	 * @see #sendMessage(org.bukkit.command.CommandSender, String)
	 * @since 1.0
	 */
	public static void sendMessageToAllPlayers(String message) {
		for (Player player : getOnlinePlayers()) {
			sendMessage(player, message);
		}
	}

	/**
	 * Sends messages to all players <i>with</i> a specific permission
	 *
	 * @param permission permission to check for on players
	 * @param messages   messages to send to the players
	 * @see #sendMessage(org.bukkit.command.CommandSender, String...)
	 * @since 1.0
	 */
	public static void sendMessageToAllPlayersWithPermission(String permission, String... messages) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(permission)) {
				sendMessage(player, messages);
			}
		}
	}

	/**
	 * Sends a message to all players <i>without</i> a specific permission
	 *
	 * @param permission permission to check for on players
	 * @param message    message to send
	 * @see #sendMessage(org.bukkit.command.CommandSender, String)
	 * @since 1.0
	 */
	public static void sendMessageToAllPlayersWithoutPermission(String permission, String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(permission)) {
				sendMessage(player, message);
			}
		}
	}

	/**
	 * Sends messages to all players <i>without</i> a specific permission
	 *
	 * @param permission permission to check for on players
	 * @param messages   messages to send to the player
	 * @since 1.0
	 */
	public static void sendMessageToAllPlayersWithoutPermission(String permission, String... messages) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.hasPermission(permission)) {
				for (String message : messages) {
					sendMessage(player, message);
				}
			}
		}
	}

	/**
	 * Sends messages to the commandsender; Automagically formats '&' to their {@link org.bukkit.ChatColor} correspondants
	 *
	 * @param commandSender commandsender to send the message to
	 * @param messages      messages to send
	 * @since 1.0
	 */
	public static void sendMessage(CommandSender commandSender, String... messages) {
		for (String message : messages) {
			sendMessage(commandSender, message);
		}
	}

	/**
	 * Sends a message to the commandsender; Automagically formats '&' to their {@link org.bukkit.ChatColor} correspondants
	 *
	 * @param commandSender commandsender to send the message to
	 * @param message       message to send
	 * @since 1.0
	 */
	public static void sendMessage(CommandSender commandSender, String message) {
		commandSender.sendMessage(StringUtil.formatColorCodes(message));
	}

	/**
	 * Teleports the player to a location
	 *
	 * @param player player to teleport
	 * @param target location to teleport the player to
	 * @since 1.0
	 */
	public static void teleport(Player player, Entity target) {
		player.teleport(target, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}

	/**
	 * Teleports the player to a location
	 *
	 * @param player   player to teleport
	 * @param location location to teleport the player to
	 * @since 1.0
	 */
	public static void teleport(Player player, Location location) {
		player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}

	/**
	 * Teleports the player to xyz co-ordinates in their current world.
	 *
	 * @param player player to teleport
	 * @param xyz    co-ordinates to teleport the player to
	 * @since 1.0
	 */
	public static void teleport(Player player, double[] xyz) {
		player.teleport(LocationHandler.getLocation(player.getWorld(), xyz));
	}

	/**
	 * Teleports the player to the location of the warp
	 * @param player player to teleport
	 * @param warp warp to teleport the player to
	 * @since 1.0
	 */
	public static void teleport(Player player, Warp warp) {
		teleport(player, warp.getLocation());
	}

	/**
	 * Forces the player to chat the given message
	 *
	 * @param player  player who we want to say this
	 * @param message what they'll be saying
	 * @since 1.0
	 */
	public static void playerChat(Player player, String message) {
		player.chat(message);
	}

	/**
	 * Force all players on the server to chat the message given
	 *
	 * @param message message for the players to say
	 * @since 1.0
	 * @see #playerChat(org.bukkit.entity.Player, String)
	 */
	public static void allPlayersChat(String message) {
		for (Player player : getOnlinePlayers()) {
			playerChat(player, message);
		}
	}

	/**
	 * Gets the readable IP address for the player
	 *
	 * @param player player to get the ip address of
	 * @return the players ip address in plain decimal-formatted text, for example: {@code 127.0.0.1}
	 * @since 1.0
	 */
	public static String getIPAddress(Player player) {
		return player.getAddress().getHostName();
	}

	/**
	 * Gets the players chat name-tag color based on their permissions on the server.
	 * <p>
	 * Defaults to {@link org.bukkit.ChatColor#AQUA} if the player is operator
	 * or no permissions have been assigned
	 * </p>
	 *
	 * @param player player to get the nametag color of
	 * @return The players nametag color based on the permission. Defaults to {@link org.bukkit.ChatColor#AQUA}
	 * @since 1.0
	 */
	public static ChatColor getNameTagColor(Player player) {
		if (!player.isOp()) {
			for (ColorCode colorCode : ColorCode.values()) {
				if (player.hasPermission(colorCode.getPermission())) {
					return colorCode.getColor();
				}
			}
		}
		return ChatColor.AQUA;
	}

	/**
	 * Check whether or not the player can speak in the chat when it's silenced.
	 *
	 * @param player player to check permissions for
	 * @return true if they can chat while its silenced (has premium), false otherwise
	 * @since 1.0
	 * @see #isPremium(String)
	 */
	public static boolean canChatWhileSilenced(Player player) {
		return (isPremium(player.getName()));
	}

	/**
	 * Check whether or not the player is premium
	 *
	 * @param playerName name of the player to check the premium status of
	 * @return true if a player with the requested name has premium, false if they have no data, or are not premium
	 * @since 1.0
	 */
	public static boolean isPremium(String playerName) {
		if (playerData.containsKey(playerName)) {
			return playerData.get(playerName).isPremium();
		}
		return false;
	}

	/**
	 * Check whether or not the player is premium.
	 *
	 * @param player player to check the premium status of
	 * @return true if they have premium status, false otherwise
	 * @see #isPremium(String)
	 * @since 1.0
	 */
	public static boolean isPremium(Player player) {
		return isPremium(player.getName());
	}

	/**
	 * Clear the players entire inventory, armor slots included.
	 *
	 * @param player player to clear inventory of
	 * @see #clearInventory(org.bukkit.entity.Player, boolean)
	 * @since 1.0
	 */
	public static void clearInventory(Player player) {
		clearInventory(player, true);
	}

	/**
	 * Clear the players entire inventory, and optionally their armor slots.
	 *
	 * @param player     player to clear inventory of
	 * @param clearArmor whether or not to clear the players armor slots
	 * @since 1.0
	 */
	public static void clearInventory(Player player, boolean clearArmor) {
		player.getInventory().clear();
		if (clearArmor) {
			player.getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
		}
	}

	/**
	 * Places an item into the players inventory but does NOT call an update to their inventory
	 *
	 * @param player    player to give an item to
	 * @param itemStack itemstack to give to the player
	 * @since 1.0
	 */
	public static void giveItem(Player player, ItemStack itemStack) {
		player.getInventory().addItem(itemStack);
	}

	/**
	 * Places items into the players inventory without calling an update method
	 *
	 * @param player player to give the items to
	 * @param items  items to give the player
	 * @see #giveItem(org.bukkit.entity.Player, org.bukkit.inventory.ItemStack)
	 * @since 1.0
	 */
	public static void giveItem(Player player, ItemStack... items) {
		for (ItemStack itemStack : items) {
			giveItem(player, itemStack);
		}
	}

	/**
	 * Sets the players armor to the armor itemstacks
	 *
	 * @param player player to set armor on
	 * @param armor  itemstack array of the armor we're equipping the player with
	 * @since 1.0
	 */
	public static void setPlayerArmor(Player player, ItemStack[] armor) {
		player.getInventory().setArmorContents(armor);
	}

	/**
	 * Removes all the potion effects from this player
	 *
	 * @param player player to remove the potion effects from
	 * @since 1.0
	 */
	public static void removePotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	/**
	 * Give a player a potion effect of the given type
	 *
	 * @param player       player to give the potion effect to
	 * @param potionEffect the potion effect in which to give the player
	 * @since 1.0
	 * @see com.caved_in.commons.entity.EntityUtility#addPotionEffect(org.bukkit.entity.LivingEntity, org.bukkit.potion.PotionEffect)
	 */
	public static void addPotionEffect(Player player, PotionEffect potionEffect) {
		EntityUtility.addPotionEffect(player, potionEffect);
	}

	/**
	 * Give this player a potion effect of the given type for a specific duration
	 *
	 * @param player          player to give the potion effect to
	 * @param potionType      effect type to give the player
	 * @param durationInTicks duration of the potion effect (in ticks. 20 ticks = 1 second)
	 * @see com.caved_in.commons.entity.EntityUtility#addPotionEffect(org.bukkit.entity.LivingEntity, com.caved_in.commons.potions.PotionType, int)
	 * @since 1.0
	 */
	public static void addPotionEffect(Player player, PotionType potionType, int durationInTicks) {
		addPotionEffect(player, PotionHandler.getPotionEffect(potionType, durationInTicks));
	}

	/**
	 * @return amount of players that are currently online
	 * @since 1.0
	 */
	public static int getOnlinePlayersCount() {
		return Bukkit.getOnlinePlayers().length;
	}

	/**
	 * @return an array of players who are currently online
	 * @since 1.0
	 */
	public static Player[] getOnlinePlayers() {
		return Bukkit.getOnlinePlayers();
	}

	/**
	 * Check if there's atleast the given amount of players online
	 *
	 * @param amount Amount to check against
	 * @return true if amount is greater or equal to the amount of players online, false otherwise
	 * @since 1.0
	 */
	public static boolean hasOnlineCount(int amount) {
		return getOnlinePlayersCount() >= amount;
	}

	/**
	 * Gets the players depth on the y-axis
	 *
	 * @param player player to get the depth of
	 * @return the players block-level depth
	 * @since 1.0
	 */
	public static int getDepth(Player player) {
		return player.getLocation().getBlockY();
	}

	/**
	 * Gets the players position in the world (Above/At/Below sea level)
	 *
	 * @param player the player to get the {@link com.caved_in.commons.world.WorldHeight} of
	 * @return {@link com.caved_in.commons.world.WorldHeight} based on the players y-axis position
	 * @see com.caved_in.commons.world.WorldHeight
	 * @since 1.0
	 */
	public static WorldHeight getWorldHeight(Player player) {
		int equalizedDepth = getEquilizedPlayerDepth(player);
		if (equalizedDepth > 0) {
			return WorldHeight.ABOVE_SEA_LEVEL;
		} else if (equalizedDepth < 0) {
			return WorldHeight.BELOW_SEA_LEVEL;
		} else {
			return WorldHeight.AT_SEA_LEVEL;
		}
	}

	/**
	 * Gets the players depth on a 0-based number
	 * @param player player to get the depth of
	 * @return 0-based y-axis position
	 * @since 1.0
	 */
	private static int getEquilizedPlayerDepth(Player player) {
		return getDepth(player) - DEPTH_EQUILZE_NUMBER;
	}

	/**
	 * Checks whether or not the player is above sea level
	 * @param player player to check the depth of
	 * @return true if they're above sea level, false if they're at or below sea level
	 * @since 1.0
	 */
	public static boolean isAboveSeaLevel(Player player) {
		return getEquilizedPlayerDepth(player) > 0;
	}

	/**
	 * Checks whether or not the player is below sea level
	 * @param player player to check the depth of
	 * @return true if the player is below sea level, false if they're at or above sea level
	 * @since 1.0
	 */
	public static boolean isBelowSeaLevel(Player player) {
		return getEquilizedPlayerDepth(player) < 0;
	}

	/**
	 * Check whether or not the player is at sea level
	 * @param player player to check the depth of
	 * @return true if they're at sea level, false if they're above or below sea level
	 * @since 1.0
	 */
	public static boolean isAtSeaLevel(Player player) {
		return getEquilizedPlayerDepth(player) == 0;
	}

	/**
	 * Replenishes a players food level to the amount given.
	 * <p>
	 *     Sets saturation to 10 and exhaustion to 0.
	 * </p>
	 * @param player player to feed
	 * @param amount amount of hunger to restore
	 * @since 1.0
	 */
	public static void feedPlayer(Player player, int amount) {
		player.setFoodLevel(amount);
		player.setSaturation(10);
		player.setExhaustion(0);
	}

	/**
	 * Replenishes the players food level to full (20).
	 * <p>
	 *     The players exhaustion will also be set to 0, along with
	 *     saturation set to 10
	 * </p>
	 * @param player player to feed
	 * @see #feedPlayer(org.bukkit.entity.Player, int)
	 * @since 1.0
	 */
	public static void feedPlayer(Player player) {
		feedPlayer(player, 20);
	}

	/**
	 * Repair all the items in a players inventory to full durability, excluding their armor.
	 * @param player player to repair the items of
	 * @see #repairItems(org.bukkit.entity.Player, boolean)
	 * @since 1.0
	 */
	public static void repairItems(Player player) {
		repairItems(player, false);
	}

	/**
	 * Repair all the items in a player inventory to full durability.
	 * <p>
	 *     If {@param repairArmor} is true, then the players armor will also
	 *     be repaired to full.
	 * </p>
	 * @param player player to repair the items of
	 * @param repairArmor whether or not to repair the armor of the player aswell
	 * @since 1.0
	 */
	public static void repairItems(Player player, boolean repairArmor) {
		PlayerInventory inventory = player.getInventory();
		ItemHandler.repairItems(inventory.getContents());
		if (repairArmor) {
			ItemHandler.repairItems(inventory.getArmorContents());
		}
	}

	/**
	 * Checks whether or not a player has an item in their hand.

	 * @param player player to check an item in hand for
	 * @return true if the player has an item in their hand, and that item isn't AIR, otherwise false
	 * @since 1.0
	 */
	public static boolean hasItemInHand(Player player) {
		return player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR;
	}

	/**
	 * Gets the players targeted location (on their cursor) of up-to 30 blocks in distance
	 * @param player player to get the target-location of
	 * @return location that the player's targeting with their cursor; If greater than 30 blocks
	 * in distance, then returns the location at the cursor 30 blocks away
	 * @since 1.0
	 */
	public static Location getTargetLocation(Player player) {
		return LocationHandler.getNormalizedLocation(player.getTargetBlock(null, MAX_BLOCK_TARGET_DISTANCE).getLocation());
	}
}
