package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.config.Permission;
import com.caved_in.commons.config.formatting.ColorCode;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.world.WorldHeight;
import com.caved_in.commons.world.Worlds;
import com.google.common.collect.Sets;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public class Players {
	private static final Field channelField = ReflectionUtilities.getField(ReflectionUtilities.getNMSClass("NetworkManager"), "k");

	public static final int DEPTH_EQUILZE_NUMBER = 63;
	private static final int MAX_BLOCK_TARGET_DISTANCE = 30;
	private static Map<String, PlayerWrapper> playerData = new HashMap<>();

	/**
	 * Check if a player with the given name has a loaded {@link com.caved_in.commons.player.PlayerWrapper}
	 *
	 * @param playerName name to check if data exists for
	 * @return true if the player has loaded data, false otherwise
	 */
	public static boolean hasData(String playerName) {
		return playerData.containsKey(playerName);
	}

	/**
	 * Check if the player has a loaded {@link com.caved_in.commons.player.PlayerWrapper}
	 *
	 * @param player player to check if data exists for
	 * @return true if the player has loaded data, false otherwise
	 */
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

		PlayerWrapper playerWrapper;

		if (Commons.hasSqlBackend()) {
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
			playerWrapper = Commons.playerDatabase.getPlayerWrapper(playerName);
			playerWrapper.setTagColor(getNameTagColor(player));
			Commons.messageConsole("&aLoaded data for " + playerName);
			playerData.put(playerName, playerWrapper);
		} else {
			playerWrapper = new PlayerWrapper(playerName, 0);
			playerData.put(playerName,playerWrapper);
		}
	}

	public static void addXp(Player player, int amount) {
		addXp(player, amount, true);
	}

	public static void addXp(Player player, int amount, boolean message) {
		if (!Commons.hasSqlBackend()) {
			return;
		}

		PlayerWrapper playerWrapper = getData(player);
		playerWrapper.addCurrency(playerWrapper.isPremium() ? ((double) amount) * 2 : (double) amount);
		updateData(playerWrapper);
		if (message) {
			Players.sendMessage(player, Messages.playerEarnedExperience(amount));
		}
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
		//If the commons is being backed
		if (Commons.hasSqlBackend()) {
			Commons.playerDatabase.syncPlayerWrapperData(playerWrapper);
		}
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
		if (Commons.hasSqlBackend() && hasData(playerName)) {
			Commons.messageConsole("&aPreparing to sync " + playerName + "'s data to database");
			Commons.playerDatabase.syncPlayerWrapperData(playerData.get(playerName));
			Commons.messageConsole("&a" + playerName + "'s data has been synchronized");
		}
		playerData.remove(playerName);
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
		return getPlayer(playerName) != null;
	}

	public static boolean isOnline(UUID uniqueId) {
		return getPlayer(uniqueId) != null;
	}

	/**
	 * Gets an online player based on the name passed.
	 *
	 * @param playerName name of the player to get the player-object for
	 * @return Player whos name matched the name checked for; Null if they're not online/doesn't exist
	 * @see Bukkit#getPlayer(String)
	 * @since 1.0
	 */
	@Deprecated
	public static Player getPlayer(String playerName) {
		return Bukkit.getPlayer(playerName);
	}

	public static Player getPlayer(UUID uniqueId) {
		return Bukkit.getPlayer(uniqueId);
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

	public static UUID getUniqueId(Player player) {
		return player.getUniqueId();
	}

	/**
	 * Kicks the player for the reason defined
	 * <p>
	 * Calling this method automatically formats for color codes in the kick message
	 * </p>
	 *
	 * @param player player to kick
	 * @param reason reason to kick the player for
	 */
	public static void kick(Player player, String reason) {
		player.kickPlayer(StringUtil.formatColorCodes(reason));
	}

	/**
	 * Kick all players online with a specific reason
	 *
	 * @param reason reason to kick the players for
	 * @since 1.0
	 */
	public static void kickAll(String reason) {
		for (Player player : allPlayers()) {
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
	public static void kickAllWithoutPermission(String permission, String reason) {
		if (permission != null && !permission.isEmpty()) {
			for (Player player : allPlayers()) {
				if (!player.hasPermission(permission)) {
					player.kickPlayer(StringUtil.formatColorCodes(reason));
				}
			}
		} else {
			kickAll(reason);
		}
	}

	public static void kickAllWithoutPermission(Permission permission, String reason) {
		kickAllWithoutPermission(permission.toString(), reason);
	}

	/**
	 * Sends messages to all online players
	 *
	 * @param messages messages to send
	 * @see #sendMessage(org.bukkit.command.CommandSender, String)
	 * @since 1.0
	 */
	public static void messageAll(String... messages) {
		for (Player player : allPlayers()) {
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
	public static void messageAll(String message) {
		for (Player player : allPlayers()) {
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
	public static void messageAllWithPermission(String permission, String... messages) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(permission)) {
				sendMessage(player, messages);
			}
		}
	}

	public static void messageAllWithPermission(Permission permission, String message) {
		messageAllWithPermission(permission.toString(), message);
	}

	public static void messageAllWithPermission(Permission permission, String... messages) {
		messageAllWithPermission(permission.toString(), messages);
	}

	/**
	 * Sends a message to all players <i>without</i> a specific permission
	 *
	 * @param permission permission to check for on players
	 * @param message    message to send
	 * @see #sendMessage(org.bukkit.command.CommandSender, String)
	 * @since 1.0
	 */
	public static void messageAllWithoutPermission(String permission, String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(permission)) {
				sendMessage(player, message);
			}
		}
	}

	public static void messageAllWithoutPermission(Permission permission, String message) {
		messageAllWithoutPermission(permission.toString(), message);
	}

	public static void messageAllWithoutPermission(Permission permission, String... messages) {
		messageAllWithoutPermission(permission.toString(), messages);
	}

	/**
	 * Sends messages to all players <i>without</i> a specific permission
	 *
	 * @param permission permission to check for on players
	 * @param messages   messages to send to the player
	 * @since 1.0
	 */
	public static void messageAllWithoutPermission(String permission, String... messages) {
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
	 * @param messageReceiver commandsender to send the message to
	 * @param messages      messages to send
	 * @since 1.0
	 */
	public static void sendMessage(CommandSender messageReceiver, String... messages) {
		for (String message : messages) {
			sendMessage(messageReceiver, message);
		}
	}

	/**
	 * Sends a message to the commandsender; Automagically formats '&' to their {@link org.bukkit.ChatColor} correspondants
	 *
	 * @param messageReceiver commandsender to send the message to
	 * @param message       message to send
	 * @since 1.0
	 */
	public static void sendMessage(CommandSender messageReceiver, String message) {
		messageReceiver.sendMessage(StringUtil.formatColorCodes(message));
	}


	/**
	 * Sends a message to the receiver
	 * @param messageReceiver
	 * @param message
	 * @param messageAmount
	 */
	public static void sendRepeatedMessage(CommandSender messageReceiver, String message, int messageAmount) {
		for(int i = 0; i < messageAmount; i++) {
			sendMessage(messageReceiver,message);
		}
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
		player.teleport(Locations.getLocation(player.getWorld(), xyz));
	}

	/**
	 * Teleports the player to the location of the warp
	 *
	 * @param player player to teleport
	 * @param warp   warp to teleport the player to
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
	public static void chat(Player player, String message) {
		player.chat(message);
	}

	/**
	 * Force all players on the server to chat the message given
	 *
	 * @param message message for the players to say
	 * @see #chat(org.bukkit.entity.Player, String)
	 * @since 1.0
	 */
	public static void allChat(String message) {
		for (Player player : allPlayers()) {
			chat(player, message);
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

	public static boolean hasPermission(Player player, String permission) {
		return player.hasPermission(permission);
	}

	public static boolean hasPermission(Player player, Permission permission) {
		return hasPermission(player, permission.toString());
	}

	public static boolean hasPlayed(String playerName) {
		return Commons.hasSqlBackend() && Commons.playerDatabase.hasData(playerName);
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
	 * @see #isPremium(String)
	 * @since 1.0
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
		return playerData.containsKey(playerName) && playerData.get(playerName).isPremium();
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

	public static void setItem(Player player, int slot, ItemStack item) {
		Inventories.setItem(player.getInventory(), slot, item);
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

	public static void setArmor(Player player, ArmorSlot armorSlot, ItemStack itemStack) {
		PlayerInventory inventory = player.getInventory();
		switch (armorSlot) {
			case HELMET:
				inventory.setHelmet(itemStack);
				break;
			case CHEST_PLATE:
				inventory.setChestplate(itemStack);
				break;
			case LEGGINGS:
				inventory.setLeggings(itemStack);
				break;
			case BOOTS:
				inventory.setBoots(itemStack);
				break;
			default:
				break;
		}
	}

	public static ItemStack[] getArmor(Player player) {
		return player.getInventory().getArmorContents();
	}

	public static ItemStack getArmor(Player player, ArmorSlot armorSlot) {
		PlayerInventory playerInventory = player.getInventory();
		ItemStack itemStack = null;
		switch (armorSlot) {
			case HELMET:
				itemStack = playerInventory.getHelmet();
				break;
			case CHEST_PLATE:
				itemStack = playerInventory.getChestplate();
				break;
			case LEGGINGS:
				itemStack = playerInventory.getLeggings();
				break;
			case BOOTS:
				itemStack = playerInventory.getBoots();
				break;
			default:
				break;
		}
		return itemStack;
	}

	/**
	 * Sets the players armor to the armor itemstacks
	 *
	 * @param player player to set armor on
	 * @param armor  itemstack array of the armor we're equipping the player with
	 * @since 1.0
	 */
	public static void setArmor(Player player, ItemStack[] armor) {
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
	 * @see com.caved_in.commons.entity.Entities#addPotionEffect(org.bukkit.entity.LivingEntity, org.bukkit.potion.PotionEffect)
	 * @since 1.0
	 */
	public static void addPotionEffect(Player player, PotionEffect potionEffect) {
		Entities.addPotionEffect(player, potionEffect);
	}

	/**
	 * @return amount of players that are currently online
	 * @since 1.0
	 */
	public static int getOnlineCount() {
		return allPlayers().length;
	}

	/**
	 * @return an array of players who are currently online
	 * @since 1.0
	 */
	public static Player[] allPlayers() {
		return Bukkit.getOnlinePlayers();
	}

	public static Player getRandomPlayer() {
		Player[] players = allPlayers();
		return players[new Random().nextInt(players.length)];
	}

	public static Collection<Player> allPlayers(World world) {
		return world.getEntitiesByClass(Player.class);
	}

	/**
	 * Get all the online players excluding those who are to be excluded
	 *
	 * @param excludedPlayers names of the players to exclude from the set
	 * @return set of all players
	 */
	public static Set<Player> allPlayersExcept(String... excludedPlayers) {
		Set<Player> players = new HashSet<>();
		Set<String> names = Sets.newHashSet(excludedPlayers);
		for (Player player : allPlayers()) {
			if (!names.contains(player.getName())) {
				players.add(player);
			}
		}
		return players;
	}

	/**
	 * Check if there's atleast the given amount of players online
	 *
	 * @param amount Amount to check against
	 * @return true if amount is greater or equal to the amount of players online, false otherwise
	 * @since 1.0
	 */
	public static boolean isOnline(int amount) {
		return getOnlineCount() >= amount;
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
		int equalizedDepth = getEqualizedDepth(player);
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
	 *
	 * @param player player to get the depth of
	 * @return 0-based y-axis position
	 * @since 1.0
	 */
	private static int getEqualizedDepth(Player player) {
		return getDepth(player) - DEPTH_EQUILZE_NUMBER;
	}

	/**
	 * Checks whether or not the player is above sea level
	 *
	 * @param player player to check the depth of
	 * @return true if they're above sea level, false if they're at or below sea level
	 * @since 1.0
	 */
	public static boolean isAboveSeaLevel(Player player) {
		return getEqualizedDepth(player) > 0;
	}

	/**
	 * Checks whether or not the player is below sea level
	 *
	 * @param player player to check the depth of
	 * @return true if the player is below sea level, false if they're at or above sea level
	 * @since 1.0
	 */
	public static boolean isBelowSeaLevel(Player player) {
		return getEqualizedDepth(player) < 0;
	}

	/**
	 * Check whether or not the player is at sea level
	 *
	 * @param player player to check the depth of
	 * @return true if they're at sea level, false if they're above or below sea level
	 * @since 1.0
	 */
	public static boolean isAtSeaLevel(Player player) {
		return getEqualizedDepth(player) == 0;
	}

	/**
	 * Replenishes a players food level to the amount given.
	 * <p>
	 * Sets saturation to 10 and exhaustion to 0.
	 * </p>
	 *
	 * @param player player to feed
	 * @param amount amount of hunger to restore
	 * @since 1.0
	 */
	public static void feed(Player player, int amount) {
		player.setFoodLevel(amount);
		player.setSaturation(10);
		player.setExhaustion(0);
	}

	/**
	 * Replenishes the players food level to full (20).
	 * <p>
	 * The players exhaustion will also be set to 0, along with
	 * saturation set to 10
	 * </p>
	 *
	 * @param player player to feed
	 * @see #feed(org.bukkit.entity.Player, int)
	 * @since 1.0
	 */
	public static void feed(Player player) {
		feed(player, 20);
	}

	/**
	 * Repair all the items in a players inventory to full durability, excluding their armor.
	 *
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
	 * If {@param repairArmor} is true, then the players armor will also
	 * be repaired to full.
	 * </p>
	 *
	 * @param player      player to repair the items of
	 * @param repairArmor whether or not to repair the armor of the player aswell
	 * @since 1.0
	 */
	public static void repairItems(Player player, boolean repairArmor) {
		PlayerInventory inventory = player.getInventory();
		Items.repairItems(inventory.getContents());
		if (repairArmor) {
			Items.repairItems(inventory.getArmorContents());
		}
	}

	/**
	 * Checks whether or not a player has an item in their hand.
	 *
	 * @param player player to check an item in hand for
	 * @return true if the player has an item in their hand, and that item isn't AIR, otherwise false
	 * @since 1.0
	 */
	public static boolean hasItemInHand(Player player) {
		return player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR;
	}

	public static boolean hasItemInHand(Player player, ItemStack compare) {
		return player.getItemInHand().isSimilar(compare);
	}

	/**
	 * Check the players inventory for an item with a specific material and name
	 * Uses a fuzzy search to determine if the item is in their inventory
	 *
	 * @param player   player who's inventory we're checking
	 * @param material The material type were checking for
	 * @param name     The name we're doing a fuzzy search against for
	 * @return true if they have the item, false otherwise
	 * @see com.caved_in.commons.inventory.Inventories#contains(org.bukkit.inventory.Inventory, org.bukkit.Material, String)
	 */
	public static boolean hasItem(Player player, Material material, String name) {
		return Inventories.contains(player.getInventory(), material, name);
	}

	public static boolean hasItem(Player player, ItemStack item) {
		return Inventories.contains(player.getInventory(), item);
	}

	public static boolean hasItem(Player player, Material material) {
		return Inventories.contains(player.getInventory(), material);
	}

	/**
	 * Gets the players targeted location (on their cursor) of up-to 30 blocks in distance
	 *
	 * @param player player to get the target-location of
	 * @return location that the player's targeting with their cursor; If greater than 30 blocks
	 * in distance, then returns the location at the cursor 30 blocks away
	 * @since 1.0
	 */
	public static Location getTargetLocation(Player player) {
		return Locations.getNormalizedLocation(player.getTargetBlock(null, MAX_BLOCK_TARGET_DISTANCE).getLocation());
	}

	/**
	 * Gets the players world name
	 *
	 * @param player player to get the name of the world for
	 * @return name of the players world
	 * @see {@link com.caved_in.commons.world.Worlds#getWorldName(org.bukkit.entity.Entity)}
	 * @since 1.2
	 */
	public static String getWorldName(Player player) {
		return Worlds.getWorldName(player);
	}

	public static void playSoundAll(Sound sound, int volume, float f) {
		for(Player p : allPlayers()) {
			Sounds.playSound(p,sound,volume,f);
		}
	}

	/**
	 * Sends the packet to the players connection.
	 *
	 * @param player player to send the packet to
	 * @param packet packet to send to the player
	 */
	public static void sendPacket(Player player, Object packet) {
		Method sendPacket = ReflectionUtilities.getMethod(ReflectionUtilities.getNMSClass("PlayerConnection"), "sendPacket", ReflectionUtilities.getNMSClass
				("Packet"));
		Object playerConnection = getConnection(player);

		try {
			sendPacket.invoke(playerConnection, packet);
		} catch (Exception e) {
			Commons.messageConsole("Failed to send a packet to: " + player.getName());
			e.printStackTrace();
		}
	}

	/**
	 * Get the EntityPlayer from a player
	 *
	 * @param player player to get the EntityPlayer handle of
	 * @return EntityPlayer handle for the player object
	 */
	public static Object toEntityPlayer(Player player) {
		Method getHandle = ReflectionUtilities.getMethod(player.getClass(), "getHandle");
		try {
			return getHandle.invoke(player);
		} catch (Exception e) {
			Commons.messageConsole("Failed retrieve the NMS Player-Object of:" + player.getName());
			return null;
		}
	}

	/**
	 * Get the connection instance for the player object
	 *
	 * @param player player to get the connection for
	 * @return connection for the player, or null if none exists
	 */
	public static Object getConnection(Player player) {
		Object connection = ReflectionUtilities.getField(ReflectionUtilities.getNMSClass("EntityPlayer"), "playerConnection", toEntityPlayer(player));
		return connection;
	}

	/**
	 * Get the network manager for a player
	 *
	 * @param player player to get the network manager of
	 * @return network manager object for the player, or null if unable to retrieve
	 */
	public static Object getNetworkManager(Player player) {
		try {
			return ReflectionUtilities.getField(getConnection(player).getClass(), "networkManager").get(getConnection(player));
		} catch (IllegalAccessException e) {
			Commons.messageConsole("Failed to get the NetworkManager of player: " + player.getName());
			return null;
		}
	}

	/**
	 * Get the network channel of a player
	 *
	 * @param player player to get the channel of
	 * @return {@link net.minecraft.util.io.netty.channel.Channel} which manages the player
	 */
	public static Channel getChannel(Player player) {
		try {
			return (Channel) channelField.get(getNetworkManager(player));
		} catch (IllegalAccessException e) {
			Commons.messageConsole("Failed to get the channel of player: " + player.getName());
			return null;
		}
	}
}
