package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Formatting.ColorCode;
import com.caved_in.commons.config.TunnelsPermissions;
import com.caved_in.commons.location.LocationHandler;
import com.caved_in.commons.potions.PotionHandler;
import com.caved_in.commons.potions.PotionType;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.Map;

public class PlayerHandler {
	private static Map<String, PlayerWrapper> playerData = new HashMap<String, PlayerWrapper>();

	public PlayerHandler() {
	}

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

	/**
	 * Get players exact name based on the partial name passed. Calls <i>Bukkit.getPlayer(partialPlayerName)</i>
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

	public static void teleport(Player player, Location location) {
		player.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
	}

	public static void teleport(Player player, double[] xyz) {
		player.teleport(LocationHandler.getLocation(player.getWorld(), xyz));
	}

	/**
	 * Forces a player to chat the given message
	 *
	 * @param player  player who we want to say this
	 * @param message what they'll be saying
	 */
	public static void playerChat(Player player, String message) {
		player.chat(message);
	}

	/**
	 * Force all players on the server to chat the given message
	 *
	 * @param message message for the players to say
	 */
	public static void allPlayersChat(String message) {
		for (Player player : getOnlinePlayers()) {
			playerChat(player, message);
		}
	}

	public static String getIPAddress(Player player) {
		return player.getAddress().getHostName();
	}

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

	public static boolean canChatWhileSilenced(Player player) {
		return (isPremium(player.getName()));
	}


	public static boolean isInStaffChat(String Player) {
		return (playerData.get(Player).isInStaffChat());
	}

	public static void setPlayerInStaffChat(String Player, boolean InChat) {
		playerData.get(Player).setInStaffChat(InChat);
	}

	public static void sendToAllStaff(String Player, String Message) {
		sendMessageToAllPlayersWithPermission(ChatColor.RED + "[Staff Chat] " + Player + ChatColor.RESET + ": " + Message, TunnelsPermissions.STAFF_PERMISSION);
	}

	public static boolean isPremium(String playerName) {
		return playerData.get(playerName).isPremium();
	}

	public static boolean isPremium(Player player) {
		return isPremium(player.getName());
	}

	public static void clearInventory(Player player) {
		clearInventory(player, true);
	}

	/**
	 * Clear a players inventory
	 *
	 * @param player     player to clear inventory of
	 * @param clearArmor whether or not to clear the players armor slots
	 */
	public static void clearInventory(Player player, boolean clearArmor) {
		player.getInventory().clear();
		if (clearArmor) {
			player.getInventory().setArmorContents(new ItemStack[]{null, null, null, null});
		}
	}

	public static void giveItem(Player player, ItemStack itemStack) {
		player.getInventory().addItem(itemStack);
	}

	/**
	 * Set the armor on a player
	 *
	 * @param player player to set armor on
	 * @param armor  itemstack array of the armor we're equiping the player with
	 */
	public static void setPlayerArmor(Player player, ItemStack[] armor) {
		player.getInventory().setArmorContents(armor);
	}

	/**
	 * Remove all potion effects from a player
	 *
	 * @param player
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
	 * Give a player a potion effect of the given type for a specific duration
	 * This method is a soft reference to EntityUtility.addPotionEffect(*)
	 *
	 * @param player          player to give the potion effect to
	 * @param potionType      effect type to give the player
	 * @param durationInTicks duration of the potion effect (in ticks. 20 ticks = 1 second)
	 */
	public static void addPotionEffect(Player player, PotionType potionType, int durationInTicks) {
		addPotionEffect(player, PotionHandler.getPotionEffect(potionType, durationInTicks));
	}

	public static int getOnlinePlayersCount() {
		return Bukkit.getOnlinePlayers().length;
	}

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
}
