package com.caved_in.commons;

import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Messages {
	public static final String MESSAGE_PREFIX = "[Tunnels Network] ";
	public static final String INVENTORY_CLEARED = "&aYour inventory has been cleared";
	public static final String PLAYER_OFFLINE = "&cThe requested player is offline";
	public static final String CHAT_SILENCED = "&7Chat is currently silenced, you are only able to chat if you have the required permissions";
	public static final String HELP_INCLUDE_PAGE_NUMBER = "&cPlease include a page number for the help menu";
	public static final String NO_PENDING_FRIENDS = "&eYou don't have any pending friend requests";
	public static final String PLAYER_HEALED = "&aYou've been healed!";
	public static final String OUTDATED_VERSION = "&eYour bukkit version is outdated; Commons required the latest bukkit version";
	public static final String ITEM_IN_HAND_REQUIRED = "&eYou need to have an item in your hand";
	public static final String FAILED_TO_ENCHANT_ITEM = "&cFailed to enchant your item; Is it a valid enchantment for the item?";
	public static final String PLAYER_FED = "&aYou've been fed!";
	public static final String PLAYER_COMMAND_SENDER_REQUIRED = "&eThis command requires a player to issue it";
	public static final String ITEMS_REPAIRED = "&aYour item(s) has been repaired";
	public static final String CHAT_UNSILENCED = "&eThe chat has been unsilenced.";
	public static final String MAINTENANCE_MODE_ENABLED = "&aMaintenance mode is now &eenabled&a, to disable it do &e/maintenance off&a or &e/Maintenance " +
			"toggle";
	public static final String MAINTENANCE_MODE_DISABLED = "&aMaintenancemode is now &edisabled&a, to enable it do &e/maintenance on&a or &e/Maintenance " +
			"toggle";
	public static final String INVALID_POTION_TYPE = "&cSorry, but that's not a valid potion type.";
	public static final String NPC_NAME_LIMIT_REACHED = "NPC's can't have names longer than 16 characters!";
	public static final String FAILED_TO_CREATE_DATAWATCHER = "Failed to create new DataWatcher!";
	public static final String CANT_CREATE_NULL_PACKETS = "Cannot create a Packet with a NULL handle!";

	public static final String ERROR_RETRIEVING_PLAYER_DATA = "&cThere was an error retrieving the players data";

	public static String playerDataLoadAttempt(String playerName) {
		return String.format("&e%s&a has data, attempting to load it.", playerName);
	}

	public static String playerDataLoaded(String playerName) {
		return String.format("&aLoaded data for &e%s", playerName);
	}

	public static String playerDataDefaultCreated(String playerName) {
		return String.format("&aCreated defaults for &e%s", playerName);
	}

	public static String playerDataRemoveCache(String playerName) {
		return String.format("&e%s had cached data so it's been removed", playerName);
	}

	public static String playerNeverPlayed(String playerName) {
		return invalidPlayer(playerName);
	}

	public static String playerOffline(String playerName) {
		return String.format("&e%s&cis offline", playerName);
	}

	public static String premiumPlayerPromoted(String playerName) {
		return String.format("&aSuccessfully promoted &e%s&a &ato premium status!", playerName);
	}

	public static String premiumPlayerDemoted(String playerName) {
		return String.format("&aSuccessfully demoted &e%s&a &afrom premium status!", playerName);
	}

	public static String playerTeleportedToPlayer(String playerName) {
		return String.format("&eYou were teleported to &a%s", playerName);
	}

	public static String playerTeleportedTo(String description) {
		return String.format("&eYou've been teleported to &a%S", description);
	}

	public static String playerXpBalance(Player player) {
		return String.format("&aYou have &e%s&a Tunnels XP", (int) Players.getData(player).getCurrency());
	}

	public static String playerTeleportedTo(String item, String target) {
		return String.format("&eYou've teleported &a%s&e to &a%s", item, target);
	}

	public static String playerWarpedTo(String warpName) {
		return String.format("&aYou've warped to &e%s", warpName);
	}

	public static String playerItemsGiven(String item, int amount) {
		return String.format("&aAdded &e%s &aof &e%s&a to your inventory", amount, item);
	}

	public static String playerItemsGiven(String item) {
		return playerItemsGiven(item, 1);
	}

	public static String playerAddedXp(String playerName, int amount) {
		return String.format("&aYou've added &e%s&a tunnels xp to &b%s", amount, playerName);
	}

	public static String playerEarnedExperience(int amount) {
		return String.format("&aYou've earned &o%s&r&a xp!", amount);
	}

	public static String playerSpeedUpdated(boolean isFlying, double speed) {
		return String.format("&aYou've set your &e%s&a speed to &e%s&a; to reset it to default use &e/speed", isFlying ? "fly" : "walk", speed);
	}

	public static String playerSpeedReset(boolean isFlying) {
		return String.format("&aYou've reset your &e%s&a speed to default", isFlying ? "fly" : "walk");
	}

	public static String playerKicked(String player, String reason) {
		return String.format("&e%s &ahas been kicked with the reason being: '&e%s&a'", player, reason);
	}

	public static String playerDebugModeChange(PlayerWrapper playerWrapper) {
		return String.format("&aYou're &e%s&a in debug mode.", playerWrapper.isInDebugMode() ? "now" : "no longer");
	}

	public static String playerFed(String playerName) {
		return String.format("&e%s&a has been fed", playerName);
	}

	public static String playerHealed(String playerName) {
		return String.format("&e%s&a has been healed!", playerName);
	}

	public static String[] playerBannedGlobalMessage(String playerName, String banIssuer, String reason, String duration) {
		return new String[]{
				String.format("&e%s&a was banned by &e%s", playerName, banIssuer),
				String.format("&e - Reason: &c%s", reason),
				String.format("&e - Expires: &c%s", duration)
		};
	}

	public static String playerUnbanned(String playerName, String pardonIssuer) {
		return String.format("&a%s&e has been unbanned by &a%s", playerName, pardonIssuer);
	}

	public static String playerPardoned(String playerName) {
		return String.format("&e%s&a has been pardoned", playerName);
	}

	public static String playerNotBanned(String playerName) {
		return String.format("&e%s&c is not banned", playerName);
	}

	public static String itemEnchantmentAdded(String enchantmentName) {
		return String.format("&aYou've added the '&e%s&a' enchantment to your item", enchantmentName);
	}

	public static String itemEnchantmentAdded(String enchantmentName, int level) {
		return String.format("&aYou've added level &c%s &a'&e%s&a' enchantment to your item", level, enchantmentName);
	}


	public static String friendRequestReceived(String playerRequesting) {
		return String.format("&b%s&a has added you as a friend, do &e/friends accept %s &ato accept, or &e/friends deny %s&a to deny them", playerRequesting,
				playerRequesting, playerRequesting);
	}

	public static String friendRequestSent(String playerName) {
		return String.format("&aYour friend request to &e%s&a has been sent", playerName);
	}

	public static String friendRequestDenied(String playerName) {
		return String.format("&aYou've denied the friend request from &e%s", playerName);
	}

	public static String friendDeniedRequest(String playerName) {
		return String.format("&e%s&c has denied your friend request", playerName);
	}

	public static String friendAlreadyExists(String playerName) {
		return String.format("&cYou're already friends with &e%s", playerName);
	}

	public static String friendRequestAlreadyExists(String playerName) {
		return String.format("&cYou've already sent &e%s&c a friend request", playerName);
	}

	public static String friendDeletedFriend(String playerName) {
		return String.format("&b%s&e has removed you from their friends list", playerName);
	}

	public static String friendDeleted(String playerName) {
		return String.format("&aYou've removed &e%s &afrom your friends list", playerName);
	}

	public static String friendRequestAccept(String playerName) {
		return String.format("&aYou've accepted the friend request from &b%s", playerName);
	}

	public static String friendRequestAccepted(String playerName) {
		return String.format("&b%s&a has accepted your friend request!", playerName);
	}

	public static String recipeFurnace(ItemStack smeltResult, ItemStack itemRequired) {
		return String.format("&e%s&a is produced by smelting &e%s", Items.getFormattedMaterialName(smeltResult), Items.getFormattedMaterialName(itemRequired));
	}

	public static String warpCreated(String warpName) {
		return String.format("&eThe warp '&a%s&e' has been created!", warpName);
	}

	public static String duplicateWarp(String warpName) {
		return String.format("&eThe warp '&c%s&e' already exists", warpName);
	}

	public static String npcNameShortened(String from, String to) {
		return String.format("Name '%s' has been shortened to '%s'", from, to);
	}

	public static String packetRetrieveFail(String protocol, String sender, int id) {
		return String.format("Failed to retrieve the packet object for: %s, %s, %s", protocol, sender, id);
	}

	public static String properUsage(String usage) {
		return String.format("&ePlease use &a%s", usage);
	}

	public static String timeUpdated(String worldName, String time) {
		return String.format("&aThe time for the world &7%s&a has been set to &e%s", worldName, time);
	}

	public static String invalidCommandUsage(String... requiredArguments) {
		String[] requiredArgs = requiredArguments.clone();
		String returnString = "&cThis command requires the following arguments: ";
		if (requiredArgs.length > 0) {
			for (int I = 0; I < requiredArgs.length; I++) {
				returnString += "&e[" + requiredArgs[I] + "]&r" + (I < (requiredArgs.length - 1) ? ", " : "");
			}
			return returnString;
		} else {
			return "&cPlease validate the syntax of the command you performed";
		}
	}

	public static String invalidItem(String itemName) {
		return String.format("&cSorry, but &e%s&c isn't a valid item", itemName);
	}

	public static String invalidItemData(String input) {
		return String.format("&cSorry; &e%s&c isn't a valid data value", input);
	}

	public static String invalidRecipe(ItemStack itemStack) {
		return String.format("&eUnable to find recipe for &c%s", Items.getFormattedMaterialName(itemStack));
	}

	public static String invalidFriendRequest(String playerName) {
		return String.format("&cYou don't have a friend request from &e%s", playerName);
	}

	public static String invalidWarp(String warpName) {
		return String.format("&eThe warp '&c%s&e' doesn't exist", warpName);
	}

	public static String invalidNpcId(int id) {
		return String.format("Failed to get the NPC with id [%s]", id);
	}

	public static String invalidMobType(String mobType) {
		return String.format("&c%s&e is an invalid mob type", mobType);
	}

	public static String invalidEnchantment(String enchantmentName) {
		return String.format("&e'&c%s&e' isn't a valid enchantment", enchantmentName);
	}

	public static String invalidWorld(String worldName) {
		return String.format("&cThe world &e%s&c doesn't exist, or isn't loaded", worldName);
	}

	public static String invalidPlayerData(String playerName) {
		return String.format("&eUnable to find data for %s; Try again?", playerName);
	}

	public static String invalidPlayer(String playerName) {
		return String.format("&c%s&e has not played on this server, sorry.", playerName);
	}
}
