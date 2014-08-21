package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.block.Direction;
import com.caved_in.commons.config.ColorCode;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.inventory.ArmorInventory;
import com.caved_in.commons.inventory.ArmorSlot;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.permission.Permission;
import com.caved_in.commons.plugin.game.world.Arena;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.sound.Sounds;
import com.caved_in.commons.threading.tasks.KickPlayerThread;
import com.caved_in.commons.threading.tasks.NameFetcherCallable;
import com.caved_in.commons.threading.tasks.UuidFetcherCallable;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.world.WorldHeight;
import com.caved_in.commons.world.Worlds;
import com.google.common.collect.Sets;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

import static com.caved_in.commons.Commons.messageConsole;


public class Players {
	private static final Field channelField = ReflectionUtilities.getField(ReflectionUtilities.getNMSClass("NetworkManager"), "k");
	public static final String DEFAULT_PREFIX = "Member";
	public static final int DEPTH_EQUALIZE_NUMBER = 63;
	private static final int MAX_BLOCK_TARGET_DISTANCE = 30;
//	private static Map<String, PlayerWrapper> playerData = new HashMap<>();

	private static Map<UUID, MinecraftPlayer> playerData = new HashMap<>();

	private static Gson gson = new Gson();

	/**
	 * Check if a player has loaded data
	 *
	 * @param playerId UUID to check if data exists for
	 * @return true if the player has loaded data, false otherwise
	 */
	public static boolean hasData(UUID playerId) {
		return playerData.containsKey(playerId);
	}

	/**
	 * Gets the playerwrapper instance for a player with the given name
	 *
	 * @param playerId uuid of the player to get the playerwrapper for
	 * @return PlayerWrapper for the given player, otherwise null if none exists
	 * @see MinecraftPlayer
	 * @since 1.0
	 */
	public static MinecraftPlayer getData(UUID playerId) {
		return playerData.get(playerId);
	}

	/**
	 * Gets the playerwrapper instance for the given player
	 *
	 * @param player player to get the wrapped data for
	 * @return PlayerWrapper for the given player, otherwise null if none exists
	 * @see MinecraftPlayer
	 * @since 1.0
	 */
	public static MinecraftPlayer getData(Player player) {
		return playerData.get(player.getUniqueId());
	}

	/**
	 * Loads a {@link MinecraftPlayer} for the given player.
	 * <p>
	 * If no data exists in the database for the given player, default data will be inserted
	 * into the database and an object will be returned based on the default data.
	 * </p>
	 *
	 * @param player player to initiate the data for
	 * @since 1.0
	 */
	@SuppressWarnings("deprecation")
	public static void addData(Player player) {
		UUID playerId = player.getUniqueId();
		String playerName = player.getName();

		MinecraftPlayer minecraftPlayer;

		//If there's no SQL backend for commons, then just load a 'null' / default wrapper
		if (!Commons.hasSqlBackend()) {
			minecraftPlayer = new MinecraftPlayer(playerName, 0);
			playerData.put(playerId, minecraftPlayer);
			return;
		}

		//If the player doesn't have data in the database, and the insertion of defaults didnt work send them a message
		if (!Commons.database.hasData(playerId)) {
			Commons.database.insertDefaultData(player);
		}
		messageConsole(Messages.playerDataLoadAttempt(playerName));
		//If the player has any cached data: remove it
		if (hasData(playerId)) {
			playerData.remove(playerId);
			messageConsole(Messages.playerDataRemoveCache(playerName));
		}

		//Create a player wrapper from the data in the database and load it to the cache
		minecraftPlayer = Commons.database.getPlayerWrapper(playerId);
		minecraftPlayer.setTagColor(getNameTagColor(player));
		Commons.messageConsole("&aLoaded data for " + playerName);
		playerData.put(playerId, minecraftPlayer);
	}

	public static void giveMoney(Player player, int amount) {
		giveMoney(player, amount, true);
	}

	public static void giveMoney(Player player, int amount, boolean message) {
		if (!Commons.hasSqlBackend()) {
			return;
		}

		MinecraftPlayer minecraftPlayer = getData(player);
		minecraftPlayer.addCurrency(minecraftPlayer.isPremium() ? ((double) amount) * 2 : (double) amount);
		updateData(minecraftPlayer);
		if (message) {
			Players.sendMessage(player, Messages.playerEarnedExperience(amount));
		}
	}

	public static int getMoney(Player player) {
		if (!Commons.hasSqlBackend()) {
			return 0;
		}

		return getData(player).getCurrency();
	}

	public static void removeMoney(Player player, int amount) {
		if (!Commons.hasSqlBackend()) {
			return;
		}

		getData(player).removeCurrency(amount);
	}

	/**
	 * Synchronizes the playerwrapper object to their corresponding database entry
	 *
	 * @param minecraftPlayer wrapped player data to synchronize to the database
	 * @see MinecraftPlayer
	 * @since 1.0
	 */
	public static void updateData(MinecraftPlayer minecraftPlayer) {
		playerData.put(minecraftPlayer.getId(), minecraftPlayer);
		//If the commons is being backed
		if (Commons.hasSqlBackend()) {
			Commons.database.syncPlayerWrapperData(minecraftPlayer);
		}
	}

	public static void updateData(Player player) {
		if (!Commons.hasSqlBackend()) {
			return;
		}

		Commons.database.syncPlayerWrapperData(getData(player));
	}

	/**
	 * Removes the {@link MinecraftPlayer} object for a player.
	 * <p>
	 * Calling this method will synchronize the playerwrapper data to the database
	 * before removing it.
	 * </p>
	 *
	 * @param playerId name of the player to remove the data for
	 * @since 1.0
	 */
	public static void removeData(UUID playerId) {
		if (Commons.hasSqlBackend() && hasData(playerId)) {
			Commons.messageConsole("&aPreparing to sync " + playerId + "'s data to database");
			Commons.database.syncPlayerWrapperData(playerData.get(playerId));
			Commons.messageConsole("&a" + playerId + "'s data has been synchronized");
		}

		MinecraftPlayer wrapper = getData(playerId);
		wrapper.dispose();
		playerData.remove(playerId);
	}

	/**
	 * Checks whether or not a player is online who's name is similar to {@param playerName}
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
	@SuppressWarnings("deprecation")
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
	@SuppressWarnings("deprecation")
	public static boolean isOnlineFuzzy(String playerName) {
		return getPlayer(playerName) != null;
	}

	public static boolean isOnline(UUID uniqueId) {
		return getPlayer(uniqueId) != null;
	}

	public static OfflinePlayer getOfflinePlayer(UUID id) {
		return Bukkit.getOfflinePlayer(id);
	}

	public static OfflinePlayer getOfflinePlayer(String name) {
		return Bukkit.getOfflinePlayer(name);
	}

	/**
	 * Gets an online player based on the name passed.
	 *
	 * @param playerName name of the player to get the player-object for
	 * @return Player whos name matched the name checked for; Null if they're not online/doesn't exist
	 * @since 1.2.2
	 */
	@Deprecated
	@SuppressWarnings("deprecation")
	public static Player getPlayer(String playerName) {
		for (Player player : allPlayers()) {
			if (playerName.equalsIgnoreCase(player.getName())) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Gets a player with the correlating unique ID
	 *
	 * @param uniqueId id of the player to get
	 * @return player with the matching id, or null if the player's not online
	 * @see Bukkit#getPlayer(java.util.UUID)
	 * @since 1.2.2
	 */
	public static Player getPlayer(UUID uniqueId) {
		return Bukkit.getPlayer(uniqueId);
	}

	/**
	 * Gets a player based on the {@link MinecraftPlayer} passed
	 *
	 * @param minecraftPlayer wrapped player object to get the actual player object of
	 * @return Player that was wrapped by the playerwrapper; null if there is no player online with matching credentials
	 * @see MinecraftPlayer
	 * @since 1.2.2
	 */
	public static Player getPlayer(MinecraftPlayer minecraftPlayer) {
		return getPlayer(minecraftPlayer.getId());
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
	@SuppressWarnings("deprecation")
	public static String getName(String partialPlayerName) {
		return isOnline(partialPlayerName) ? getPlayer(partialPlayerName).getName() : null;
	}

	public static String getNameFromUUID(UUID uuid) throws Exception {
		return NameFetcherCallable.getNameFromUUID(uuid);
	}

	public static String getNameFromUUID(String uuid) throws Exception {
		return getNameFromUUID(UUID.fromString(uuid));
	}

	public static UUID getUUIDFromName(String name) throws Exception {
		return UuidFetcherCallable.getUUIDOf(name);
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


	public static void kick(Player player, String reason, boolean thread) {
		if (!thread) {
			kick(player, reason);
			return;
		}
		Commons.threadManager.runTaskOneTickLater(new KickPlayerThread(player.getUniqueId(), reason));
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

	public static void messageOps(String... messages) {
		messageAll(onlineOperators(), messages);
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

	public static void messageAllExcept(String message, Player... exceptions) {
		UUID[] playerIds = getIdArray(exceptions);
		messageAll(allPlayersExcept(playerIds), message);
	}

	private static UUID[] getIdArray(Player[] players) {
		UUID[] ids = new UUID[players.length - 1];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = players[i].getUniqueId();
		}
		return ids;
	}

	/**
	 * Set the players level.
	 *
	 * @param player player to change the level of
	 * @param lvl    level to set the player
	 */
	public static void setLevel(Player player, int lvl) {
		player.setLevel(lvl);
	}

	/**
	 * Remove the given amount of levels from the player.
	 *
	 * @param player player to remove the levels from.
	 * @param amount the amount of levels to decrease by
	 */
	public static void removeLevel(Player player, int amount) {
		player.setLevel(player.getLevel() - amount);
	}

	/**
	 * Decrease the players level by 1.
	 *
	 * @param player player to decrease the level of.
	 */
	public static void removeLevel(Player player) {
		removeLevel(player, 1);
	}

	/**
	 * Increase the players level
	 *
	 * @param player player to increase the level of
	 * @param amount the amount to increase the players level by
	 */
	public static void addLevel(Player player, int amount) {
		player.setLevel(player.getLevel() + amount);
	}

	/**
	 * Increase the players level by one.
	 *
	 * @param player the player to increase the level of
	 */
	public static void addLevel(Player player) {
		addLevel(player, 1);
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
			if (player == null) {
				continue;
			}
			sendMessage(player, message);
		}
	}

	public static void messageAll(Collection<Player> receivers, String... messages) {
		for (Player player : receivers) {
			if (player == null) {
				continue;
			}
			sendMessage(player, messages);
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
		for (Player player : allPlayers()) {
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
		for (Player player : allPlayers()) {
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
		for (Player player : allPlayers()) {
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
	 * @param messages        messages to send
	 * @since 1.0
	 */
	public static void sendMessage(CommandSender messageReceiver, String... messages) {
		for (String message : messages) {
			sendMessage(messageReceiver, message);
		}
	}

	public static void sendMessage(CommandSender receiver, List<String> messages) {
		for (String message : messages) {
			sendMessage(receiver, message);
		}
	}

	public static void sendSoundedMessage(Player receiver, Sound sound, int delay, String... messages) {
		int index = 1;
		for (String message : messages) {
			Commons.threadManager.runTaskLater(new DelayedMessage(receiver, message, sound), TimeHandler.getTimeInTicks(index * delay, TimeType.SECOND));
			index += 1;
		}
	}

	public static void sendDelayedMessage(Player receiver, int delay, final String... messages) {
		int index = 1;
		for (String message : messages) {
			Commons.threadManager.runTaskLater(new DelayedMessage(receiver, message), TimeHandler.getTimeInTicks(index * delay, TimeType.SECOND));
			index += 1;
		}
	}

	/**
	 * Delayed message, used to send a player a message after a specific duration of time, and in order.
	 */
	private static class DelayedMessage implements Runnable {

		private String message;
		private Sound sound = null;
		private UUID receiverId;

		public DelayedMessage(Player player, String message) {
			this.receiverId = player.getUniqueId();
			this.message = StringUtil.colorize(message);
		}

		public DelayedMessage(Player player, String message, Sound sound) {
			this(player, message);
			this.sound = sound;
		}

		@Override
		public void run() {
			Players.sendMessage(Players.getPlayer(receiverId), message);
			if (sound != null) {
				Sounds.playSound(Players.getPlayer(receiverId), sound);
			}
		}
	}

	/**
	 * Sends a message to the commandsender; Automagically formats '&' to their {@link org.bukkit.ChatColor} correspondants
	 *
	 * @param messageReceiver receiver of the message
	 * @param message         message to send
	 * @since 1.0
	 */
	public static void sendMessage(CommandSender messageReceiver, String message) {
		if (messageReceiver == null || message == null) {
			return;
		}
		messageReceiver.sendMessage(StringUtil.formatColorCodes(message));
	}


	/**
	 * Sends a message to the receiver a specific number of times;
	 *
	 * @param messageReceiver the receiver of this message
	 * @param message         message to send to the receiver
	 * @param messageAmount   how many times the message is to be sent
	 */
	public static void sendRepeatedMessage(CommandSender messageReceiver, String message, int messageAmount) {
		for (int i = 0; i < messageAmount; i++) {
			sendMessage(messageReceiver, message);
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

	public static void teleportToSpawn(Player player, World world) {
		teleport(player, Worlds.getSpawn(world));
	}

	public static void teleportToSpawn(Player player, Arena arena) {
		teleportToSpawn(player, arena.getWorld());
	}

	public static void teleportAllToSpawn(Arena arena) {
		stream().forEach(p -> teleportToSpawn(p, arena));
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

	public static boolean hasActivePunishment(UUID uniqueId, PunishmentType type) {
		Set<Punishment> punishments = Commons.database.getActivePunishments(uniqueId);
		for (Punishment punishment : punishments) {
			if (punishment.getPunishmentType() == type) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasActivePunishment(Player player, PunishmentType type) {
		return hasActivePunishment(player.getUniqueId(), type);
	}

	public static boolean hasPlayed(UUID playerId) {
		boolean online = isOnline(playerId);
		if (Commons.hasSqlBackend() && !online) {
			return Commons.database.hasData(playerId);
		}
		return online;
	}

	public static boolean hasPlayed(String name) {
		boolean online = isOnline(name);
		if (Commons.hasSqlBackend() && !online) {
			return Commons.database.hasData(name);
		}
		return online;
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
	 * @since 1.2.2
	 */
	public static boolean canChatWhileSilenced(Player player) {
		return (hasPermission(player, Permission.CHAT_WHILE_SILENCED));
	}

	/**
	 * Check whether or not the player is premium
	 *
	 * @param playerId uuid of the player to check the premium status of
	 * @return true if the player is premium, false if not.
	 * @since 1.0
	 */
	public static boolean isPremium(UUID playerId) {
		return playerData.containsKey(playerId) && playerData.get(playerId).isPremium();
	}

	/**
	 * Check whether or not the player is premium.
	 *
	 * @param player player to check the premium status of
	 * @return true if they have premium status, false otherwise
	 * @see #isPremium(java.util.UUID)
	 * @since 1.0
	 */
	public static boolean isPremium(Player player) {
		return isPremium(player.getUniqueId());
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

	public static void setInventory(Player player, Map<Integer, ItemStack> itemMap, boolean clearInventory) {
		if (clearInventory) {
			clearInventory(player, true);
		}

		PlayerInventory inventory = player.getInventory();
		for (Map.Entry<Integer, ItemStack> itemEntry : itemMap.entrySet()) {
			inventory.setItem(itemEntry.getKey(), itemEntry.getValue());
		}
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

	public static void dropInventory(Player player) {
		ItemStack[] inventoryContents = player.getInventory().getContents();
		Players.clearInventory(player);

		for (ItemStack item : inventoryContents) {
			if (item == null) {
				continue;
			}
			Worlds.dropItemNaturally(player, item);
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

	public static boolean giveItem(Player player, ItemStack itemStack, boolean drop) {
		PlayerInventory inventory = player.getInventory();
		if (inventory.firstEmpty() == -1) {
			if (drop) {
				Worlds.dropItem(player, itemStack, false);
				return true;
			}
			return false;
		}
		inventory.addItem(itemStack);
		return true;
	}

	public static void setItem(Player player, int slot, ItemStack item) {
		Inventories.setItem(player.getInventory(), slot, item);
	}

	/**
	 * Changes the active slot a player has selected on their hotbar. (Between 0 & 8)
	 *
	 * @param player player to switch slots of
	 * @param slot   slot to switch the player to.
	 */
	public static void setHotbarSelection(Player player, int slot) {
		if (slot > 8) {
			return;
		}

		player.getInventory().setHeldItemSlot(slot);
	}

	/**
	 * Sets an item in the players hotbar to the item given
	 *
	 * @param player player to give the item to
	 * @param item   item to set in slot
	 * @param slot   slot to change
	 */
	public static void setHotbarItem(Player player, ItemStack item, int slot) {
		if (slot > 8) {
			return;
		}

		setItem(player, slot, item);
	}

	public static void setHotbarContents(Player player, ItemStack... items) {
		for (int i = 0; i < items.length; i++) {
			setHotbarItem(player,items[i],i);
		}
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
			giveItem(player, itemStack, true);
		}
	}

	public static void setArmor(Player player, ArmorSlot armorSlot, ItemStack itemStack) {
		if (itemStack == null || armorSlot == null) {
			return;
		}

		PlayerInventory inventory = player.getInventory();
		switch (armorSlot) {
			case HELMET:
				inventory.setHelmet(itemStack);
				break;
			case CHEST:
				inventory.setChestplate(itemStack);
				break;
			case LEGGINGS:
				inventory.setLeggings(itemStack);
				break;
			case BOOTS:
				inventory.setBoots(itemStack);
				break;
			case WEAPON:
				inventory.setItemInHand(itemStack);
				break;
			default:
				break;
		}
	}

	/**
	 * Get the items a player has equipped.
	 *
	 * @param player player to get the armor of
	 * @return
	 */
	public static ItemStack[] getArmor(Player player) {
		return player.getInventory().getArmorContents();
	}

	/**
	 * Get the equipped armor of a player in a specific slot.
	 * @param player player to get the armor of.
	 * @param armorSlot which armor slot to get the armor from.
	 * @return the item equipped in the given slot, or null if none's equipped
	 */
	public static ItemStack getArmor(Player player, ArmorSlot armorSlot) {
		PlayerInventory playerInventory = player.getInventory();
		ItemStack itemStack = null;
		switch (armorSlot) {
			case HELMET:
				itemStack = playerInventory.getHelmet();
				break;
			case CHEST:
				itemStack = playerInventory.getChestplate();
				break;
			case LEGGINGS:
				itemStack = playerInventory.getLeggings();
				break;
			case BOOTS:
				itemStack = playerInventory.getBoots();
				break;
			case WEAPON:
				itemStack = playerInventory.getItemInHand();
				break;
			default:
				break;
		}
		return itemStack;
	}

	/**
	 * Sets the players armor.
	 *
	 * @param player player to set armor on
	 * @param armor  itemstack array of the armor we're equipping the player with
	 * @since 1.0
	 */
	public static void setArmor(Player player, ItemStack[] armor) {
		player.getInventory().setArmorContents(armor);
	}

	/**
	 * Equip the player with armor.
	 *
	 * @param player player to parent.
	 * @param armor  armor-inventory to assign to the player
	 */
	public static void setArmor(Player player, ArmorInventory armor) {
		for (Map.Entry<ArmorSlot, ItemStack> entry : armor.getArmor().entrySet()) {
			Players.setArmor(player, entry.getKey(), entry.getValue());
		}
	}
	/**
	 * Removes all the potion effects from the player
	 *
	 * @param player player to remove the potion effects from
	 * @since 1.0
	 */
	public static void removePotionEffects(Player player) {
		Entities.removePotionEffects(player);
	}

	/**
	 * Give a player a potion effect of the given type
	 *
	 * @param player       player to give the potion effect to
	 * @param potionEffect the potion effect in which to give the player
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

	public static Stream<Player> stream() {
		return Stream.of(allPlayers());
	}

	public static Set<Player> onlineOperators() {
		Set<Player> players = new HashSet<>();
		for (Player player : allPlayers()) {
			if (player.isOp()) {
				players.add(player);
			}
		}
		return players;
	}

	public static Player getRandomPlayer() {
		Player[] players = allPlayers();
		return players[new Random().nextInt(players.length)];
	}

	public static Collection<Player> allPlayers(World world) {
		return world.getEntitiesByClass(Player.class);
	}

	public static Collection<MinecraftPlayer> allPlayerWrappers() {
		return playerData.values();
	}

	public static Set<Player> getAllDebugging() {
		Set<Player> players = new HashSet<>();
		for (MinecraftPlayer wrapper : playerData.values()) {
			if (wrapper.isOnline() && wrapper.isInDebugMode()) {
				players.add(getPlayer(wrapper));
			}
		}
		return players;
	}

	public static Set<Player> getPlayers(Collection<UUID> ids) {
		Set<Player> players = new HashSet<>();
		for (UUID id : ids) {
			if (!isOnline(id)) {
				continue;
			}
			players.add(getPlayer(id));
		}
		return players;
	}

	/**
	 * Get all the online players excluding those who are to be excluded
	 *
	 * @param excludedPlayers names of the players to exclude from the set
	 * @return set of all players
	 */
	@SuppressWarnings("deprecation")
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

	public static Set<Player> allPlayersExcept(UUID... playerIds) {
		Set<Player> players = Sets.newHashSet(allPlayers());
		Set<UUID> uniqueIds = Sets.newHashSet(playerIds);
		for (Player player : allPlayers()) {
			if (uniqueIds.contains(player.getUniqueId())) {
				players.remove(player);
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
		return getDepth(player) - DEPTH_EQUALIZE_NUMBER;
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
	 * Heal the player to full health, remove any potion effects they have, and clear them from all
	 * ticking damages.
	 *
	 * @param player player to heal
	 */
	public static void heal(Player player) {
		Players.removePotionEffects(player);
		removeFire(player);
		Entities.setHealth(player, Entities.getMaxHealth(player));
	}

	public static void removeFire(Player player) {
		Entities.removeFire(player);
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

	public static boolean handIsEmpty(Player player) {
		return !hasItemInHand(player);
	}

	public static void clearHand(Player player) {
		player.setItemInHand(null);
	}

	public static void removeFromHand(Player player, int amount) {
		if (!Players.hasItemInHand(player)) {
			return;
		}

		ItemStack hand = Items.removeFromStack(player.getItemInHand(), amount);
		player.setItemInHand(hand);
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

	public static void hidePlayers(Player player, Collection<Player> targets) {
		targets.forEach(player::hidePlayer);
	}

	public static void unhidePlayers(Player player, Collection<Player> targets) {
		targets.forEach(player::showPlayer);
	}

	public static void hidePlayer(Collection<Player> players, Player target) {
		players.forEach(p -> p.hidePlayer(target));
	}

	public static void unhidePlayer(Collection<Player> players, Player target) {
		players.forEach(p -> p.showPlayer(target));
	}

	public static void hidePlayers(Player player) {
		for (Player p : allPlayers()) {
			player.hidePlayer(p);
		}
		getData(player).setHidingOtherPlayers(true);
	}

	public static void unhidePlayers(Player player) {
		for (Player p : allPlayers()) {
			player.showPlayer(p);
		}
		getData(player).setHidingOtherPlayers(false);
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

	public static void playSoundAll(Sound sound, float volume, float pitch) {
		for (Player p : allPlayers()) {
			Sounds.playSound(p, sound, volume, pitch);
		}
	}

	public static void playSoundAll(Sound sound, int volume) {
		playSoundAll(sound, volume, 1.0f);
	}

	/**
	 * Get the cardinal compass direction of a player.
	 *
	 * @param player
	 * @return
	 */
	public static Direction getCardinalDirection(Player player) {
		double rot = (player.getLocation().getYaw() - 90) % 360;
		if (rot < 0) {
			rot += 360.0;
		}
		return getDirection(rot);
	}

	public static Direction getDirection(Player player) {
		int dirCode = Math.round(player.getLocation().getYaw() / 90f);
		switch (dirCode) {
			case 0:
				return Direction.SOUTH;
			case 1:
				return Direction.WEST;
			case 2:
				return Direction.NORTH;
			case 3:
				return Direction.EAST;
			default:
				return Direction.SOUTH;
		}
	}

	/**
	 * Converts a rotation to a cardinal direction.
	 *
	 * @param rot
	 * @return
	 */
	private static Direction getDirection(double rot) {
		if (0 <= rot && rot < 22.5) {
			return Direction.NORTH;
		} else if (22.5 <= rot && rot < 67.5) {
			return Direction.NORTHEAST;
		} else if (67.5 <= rot && rot < 112.5) {
			return Direction.EAST;
		} else if (112.5 <= rot && rot < 157.5) {
			return Direction.SOUTHEAST;
		} else if (157.5 <= rot && rot < 202.5) {
			return Direction.SOUTH;
		} else if (202.5 <= rot && rot < 247.5) {
			return Direction.SOUTHWEST;
		} else if (247.5 <= rot && rot < 292.5) {
			return Direction.WEST;
		} else if (292.5 <= rot && rot < 337.5) {
			return Direction.NORTHWEST;
		} else if (337.5 <= rot && rot < 360.0) {
			return Direction.NORTH;
		} else {
			return null;
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
		return ReflectionUtilities.getField(ReflectionUtilities.getNMSClass("EntityPlayer"), "playerConnection", toEntityPlayer(player));
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
