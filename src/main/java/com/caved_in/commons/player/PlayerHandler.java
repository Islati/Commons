package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Formatting.ColorCode;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.location.LocationHandler;
import com.caved_in.commons.potions.PotionHandler;
import com.caved_in.commons.potions.PotionType;
import com.caved_in.commons.utilities.StringUtil;
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

	public static PlayerWrapper getData(String Name) {
		return playerData.get(Name);
	}

	public static PlayerWrapper getData(Player player) {
		return playerData.get(player.getName());
	}

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

	public static void updateData(PlayerWrapper playerWrapper) {
		playerData.put(playerWrapper.getName(), playerWrapper);
		Commons.playerDatabase.syncPlayerWrapperData(playerWrapper);
	}

	public static void removeData(String playerName) {
		if (hasData(playerName)) {
			Commons.messageConsole("&aPreparing to sync " + playerName + "'s data to database");
			Commons.playerDatabase.syncPlayerWrapperData(playerData.get(playerName));
			playerData.remove(playerName);
			Commons.messageConsole("&a" + playerName + "'s data has been synchronized");
		}
	}

	public static boolean isOnline(String playerName) {
		return isOnline(playerName, false);
	}

	public static boolean isOnlineExact(String playerName) {
		return Bukkit.getPlayerExact(playerName) != null;
	}

	public static boolean isOnline(String playerName, boolean isExact) {
		return isExact ? isOnlineExact(playerName) : isOnlineFuzzy(playerName);
	}

	public static boolean isOnlineFuzzy(String playerName) {
		return Bukkit.getPlayer(playerName) != null;
	}

	public static Player getPlayer(String playerName) {
		return Bukkit.getPlayer(playerName);
	}

	public static Player getPlayer(PlayerWrapper playerWrapper) {
		return Bukkit.getPlayer(playerWrapper.getName());
	}

	/**
	 * Get players exact name based on the partial name passed. Calls <i>Bukkit.getPlayer(partialPlayerName)</i>
	 *
	 * @param partialPlayerName Partial name of the player to get the full name of
	 * @return An exact players name if there's a player online which matches the partial name passed; Otherwise
	 * returns the partial name passed
	 */
	public static String getName(String partialPlayerName) {
		return isOnline(partialPlayerName) ? getPlayer(partialPlayerName).getName() : partialPlayerName;
	}

	public static void kickAllPlayers(String... reason) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			for (String kickReason : reason) {
				player.kickPlayer(StringUtil.formatColorCodes(kickReason));
			}
		}
	}

	public static void kickAllPlayers(String reason) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.kickPlayer(StringUtil.formatColorCodes(reason));
		}
	}

	public static void kickAllPlayersWithoutPermission(String permission, String... reason) {
		if (permission != null && !permission.isEmpty()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.hasPermission(permission)) {
					for (String kickReason : reason) {
						player.kickPlayer(StringUtil.formatColorCodes(kickReason));
					}
				}
			}
		} else {
			kickAllPlayers(reason);
		}
	}

	public static void kickAllPlayersWithoutPermission(String permission, String reason) {
		if (permission != null && !permission.isEmpty()) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.hasPermission(permission)) {
					player.kickPlayer(StringUtil.formatColorCodes(reason));
				}
			}
		} else {
			kickAllPlayers(reason);
		}
	}

	public static void sendMessageToAllPlayers(String... messages) {
		for (Player Player : Bukkit.getOnlinePlayers()) {
			for (String message : messages) {
				Player.sendMessage(StringUtil.formatColorCodes(message));
			}
		}
	}

	public static void sendMessageToAllPlayers(String message) {
		for (Player Player : Bukkit.getOnlinePlayers()) {
			Player.sendMessage(StringUtil.formatColorCodes(message));
		}
	}

	public static void sendMessageToAllPlayersWithPermission(String permission, String... messages) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(permission)) {
				for (String message : messages) {
					player.sendMessage(StringUtil.formatColorCodes(message));
				}
			}
		}
	}

	public static void sendMessageToAllPlayersWithoutPermission(String permission, String message) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission(permission)) {
				player.sendMessage(StringUtil.formatColorCodes(message));
			}
		}
	}

	public static void sendMessageToAllPlayersWithoutPermission(String permission, String... messages) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.hasPermission(permission)) {
				for (String message : messages) {
					sendMessage(player, message);
				}
			}
		}
	}

	public static void sendMessage(CommandSender commandSender, String... messages) {
		for (String message : messages) {
			commandSender.sendMessage(StringUtil.formatColorCodes(message));
		}
	}

	public static void sendMessage(CommandSender commandSender, String message) {
		commandSender.sendMessage(StringUtil.formatColorCodes(message));
	}

	public static void teleport(Player player, Entity target) {
		player.teleport(target, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}

	/**
	 * Teleport the player to a location
	 * @param player player to teleport
	 * @param location location to teleport the player to
	 */
	public static void teleport(Player player, Location location) {
		player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}

	/**
	 * Teleport the player to xyz co-ordinates in their current world.
	 * @param player player to teleport
	 * @param xyz co-ordinates to teleport the player to
	 */
	public static void teleport(Player player, double[] xyz) {
		player.teleport(LocationHandler.getLocation(player.getWorld(), xyz));
	}

	/**
	 * Forces the player to chat the given message
	 *
	 * @param player  player who we want to say this
	 * @param message what they'll be saying
	 */
	public static void playerChat(Player player, String message) {
		player.chat(message);
	}

	/**
	 * Force all players on the server to chat the message given
	 *
	 * @param message message for the players to say
	 */
	public static void allPlayersChat(String message) {
		for (Player player : getOnlinePlayers()) {
			playerChat(player, message);
		}
	}

	/**
	 * Gets the readable IP address for the player
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
	 *     Defaults to {@link org.bukkit.ChatColor#AQUA} if the player is operator
	 *     or no permissions have been assigned
	 * </p>
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
	 * @param player player to check permissions for
	 * @return true if they can chat while its silenced (has premium), false otherwise
	 */
	public static boolean canChatWhileSilenced(Player player) {
		return (isPremium(player.getName()));
	}

	/**
	 * Check whether or not the player is premium
	 * @param playerName name of the player to check the premium status of
	 * @return true if a player with the requested name has premium, false if they have no data, or are not premium
	 */
	public static boolean isPremium(String playerName) {
		if (playerData.containsKey(playerName)) {
			return playerData.get(playerName).isPremium();
		}
		return false;
	}

	/**
	 * Check whether or not the player is premium.
	 * @param player player to check the premium status of
	 * @return true if they have premium status, false otherwise
	 * @see #isPremium(String)
	 */
	public static boolean isPremium(Player player) {
		return isPremium(player.getName());
	}

	/**
	 * Clear the players entire inventory, armor slots included.
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
	 * @param player player to give an item to
	 * @param itemStack itemstack to give to the player
	 * @since 1.0
	 */
	public static void giveItem(Player player, ItemStack itemStack) {
		player.getInventory().addItem(itemStack);
	}

	/**
	 * Places items into the players inventory without calling an update method
	 * @param player player to give the items to
	 * @param items items to give the player
	 * @since 1.0
	 */
	public static void giveItem(Player player, ItemStack... items) {
		for(ItemStack itemStack : items) {
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
	 */
	public static void removePotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	/**
	 * Give a player a potion effect of the given type
	 * This method is a soft reference to EntityUtility.addPotionEffect(*)
	 *
	 * @param player       player to give the potion effect to
	 * @param potionEffect the potion effect in which to give the player
	 */
	public static void addPotionEffect(Player player, PotionEffect potionEffect) {
		player.addPotionEffect(potionEffect);
	}

	/**
	 * Give this player a potion effect of the given type for a specific duration
	 * This method is a soft reference to {@code EntityUtility.addPotionEffect()}
	 *
	 * @param player          player to give the potion effect to
	 * @param potionType      effect type to give the player
	 * @param durationInTicks duration of the potion effect (in ticks. 20 ticks = 1 second)
	 * @see com.caved_in.commons.entity.EntityUtility#addPotionEffect(org.bukkit.entity.LivingEntity, com.caved_in.commons.potions.PotionType, int)
	 */
	public static void addPotionEffect(Player player, PotionType potionType, int durationInTicks) {
		addPotionEffect(player, PotionHandler.getPotionEffect(potionType, durationInTicks));
	}

	/**
	 * The amount of players that are currently online
	 * @return
	 */
	public static int getOnlinePlayersCount() {
		return Bukkit.getOnlinePlayers().length;
	}

	/**
	 * @return an array of players who are currently online
	 */
	public static Player[] getOnlinePlayers() {
		return Bukkit.getOnlinePlayers();
	}

	/**
	 * Check if there's atleast the given amount of players online
	 *
	 * @param amount Amount to check against
	 * @return true if amount is greater or equal to the amount of players online, false otherwise
	 */
	public static boolean hasOnlineCount(int amount) {
		return getOnlinePlayersCount() >= amount;
	}

	/**
	 * Gets the players depth on the y-axis
	 * @param player player to get the depth of
	 * @return the players block-level depth
	 */
	public static int getDepth(Player player) {
		return player.getLocation().getBlockY();
	}

	/**
	 * Gets the players position in the world (Above/At/Below sea level)
	 * @param player the player to get the {@link com.caved_in.commons.world.WorldHeight} of
	 * @return {@link com.caved_in.commons.world.WorldHeight} based on the players y-axis position
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

	public static int getEquilizedPlayerDepth(Player player) {
		return getDepth(player) - DEPTH_EQUILZE_NUMBER;
	}

	public static boolean isAboveSeaLevel(Player player) {
		return getEquilizedPlayerDepth(player) > 0;
	}

	public static boolean isBelowSeaLevel(Player player) {
		return getEquilizedPlayerDepth(player) < 0;
	}

	public static boolean isAtSeaLevel(Player player) {
		return getEquilizedPlayerDepth(player) == 0;
	}

	public static void feedPlayer(Player player, int amount) {
		player.setFoodLevel(amount);
		player.setSaturation(10);
		player.setExhaustion(0);
	}

	public static void feedPlayer(Player player) {
		feedPlayer(player, 20);
	}

	public static void repairItems(Player player) {
		repairItems(player, false);
	}

	public static void repairItems(Player player, boolean repairArmor) {
		PlayerInventory inventory = player.getInventory();
		ItemHandler.repairItems(inventory.getContents());
		if (repairArmor) {
			ItemHandler.repairItems(inventory.getArmorContents());
		}
	}

	public static boolean hasItemInHand(Player player) {
		return player.getItemInHand() != null && player.getItemInHand().getType() != Material.AIR;
	}

	public static Location getTargetLocation(Player player) {
		return LocationHandler.getNormalizedLocation(player.getTargetBlock(null,MAX_BLOCK_TARGET_DISTANCE).getLocation());
	}
}
