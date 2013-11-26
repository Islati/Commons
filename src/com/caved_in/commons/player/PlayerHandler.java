package com.caved_in.commons.player;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Formatting.ColorCode;
import com.caved_in.commons.config.TunnelsPermissions;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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

	/**
	 * @param player
	 * @return
	 */
	private static PlayerWrapper getData(Player player) {
		return playerData.get(player.getName());
	}

	/**
	 * @param player
	 */
	public static void addData(Player player) {
		String playerName = player.getName();

		PlayerWrapper PlayerWrapper = null;

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
	 * @param playerWrapper
	 */
	public static void updateData(PlayerWrapper playerWrapper) {
		playerData.put(playerWrapper.getName(), playerWrapper);
		Commons.playerDatabase.syncPlayerWrapperData(playerWrapper);
	}

	/**
	 * @param playerName
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
	 * @param playerName
	 * @return
	 */
	public static boolean isOnline(String playerName) {
		return Bukkit.getPlayerExact(playerName) != null;
	}

	/**
	 * @param playerName
	 * @return
	 */
	public static boolean isOnlineFuzzy(String playerName) {
		return Bukkit.getPlayer(playerName) != null;
	}

	/**
	 * @param playerName
	 * @return
	 */
	public static Player getPlayer(String playerName) {
		return Bukkit.getPlayer(playerName);
	}

	/**
	 * @param reason
	 */
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

	/**
	 * @param permission
	 * @param reason
	 */
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

	/**
	 * @param messages
	 */
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

	/**
	 * @param permission
	 * @param messages
	 */
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

	/**
	 * @param permission
	 * @param messages
	 */
	public static void sendMessageToAllPlayersWithoutPermission(String permission, String... messages) {
		for (Player Player : Bukkit.getOnlinePlayers()) {
			if (!Player.hasPermission(permission)) {
				for (String message : messages) {
					Player.sendMessage(StringUtil.formatColorCodes(message));
				}
			}
		}
	}

	/**
	 * @param player
	 * @param messages
	 */
	public static void sendMessageToPlayer(Player player, String... messages) {
		for (String message : messages) {
			player.sendMessage(StringUtil.formatColorCodes(message));
		}
	}

	public static void sendMessageToPlayer(Player player, String message) {
		player.sendMessage(StringUtil.formatColorCodes(message));
	}


	/**
	 * Sends message(s) to a player while auto-formatting the color codes
	 *
	 * @param player
	 * @param messages
	 */
	public static void sendMessage(Player player, String... messages) {
		sendMessageToPlayer(player, messages);
	}

	public static void sendMessage(Player player, String message) {
		sendMessageToPlayer(player, message);
	}

	/**
	 * @param player
	 * @return
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
	 * Checks if the player can chat while the chat is in silence mode
	 *
	 * @param player
	 * @return
	 */
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

	/**
	 * Check if a player is premium
	 *
	 * @param playerName
	 * @return
	 */
	public static boolean isPremium(String playerName) {
		return playerData.get(playerName).isPremium();
	}

	/**
	 * Check if a player is premium
	 *
	 * @param player
	 * @return
	 */
	public static boolean isPremium(Player player) {
		return isPremium(player.getName());
	}

	/**
	 * Clear a players inventory (armor included)
	 *
	 * @param player
	 */
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
}
