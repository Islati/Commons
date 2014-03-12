package com.caved_in.commons;

import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.player.PlayerHandler;
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
	public static final String MAINTENANCE_MODE_ENABLED = "&aMaintenance mode is now &eenabled&a, to disable it do &e/maintenance off&a or &e/Maintenance toggle";
	public static final String MAINTENANCE_MODE_DISABLED = "&aMaintenancemode is now &edisabled&a, to enable it do &e/maintenance on&a or &e/Maintenance toggle";
	public static final String INVALID_POTION_TYPE = "&cSorry, but that's not a valid potion type.";
	public static final String NPC_NAME_LIMIT_REACHED = "NPC's can't have names longer than 16 characters!";
	public static final String FAILED_TO_CREATE_DATAWATCHER = "Failed to create new DataWatcher!";
	public static final String CANT_CREATE_NULL_PACKETS = "Cannot create a Packet with a NULL handle!";

	public static String PLAYER_OFFLINE(String playerName) {
		return String.format("&e%s&cis offline", playerName);
	}

	public static String PLAYER_DATA_NOT_FOUND(String playerName) {
		return String.format("&eUnable to find data for %s; Try again?", playerName);
	}

	public static String PREMIUM_STATUS_PROMOTED(String playerName) {
		return String.format("&aSuccessfully promoted &e%s&a &ato premium status!", playerName);
	}

	public static String PREMIUM_STATUS_DEMOTED(String playerName) {
		return String.format("&aSuccessfully demoted &e%s&a &afrom premium status!", playerName);
	}

	public static String TELEPORTED_TO_PLAYER(String playerName) {
		return String.format("&eYou were teleported to &a%s", playerName);
	}

	public static String ITEM_DOESNT_EXIST(String itemName) {
		return String.format("&cSorry, but &e%s&c isn't a valid item", itemName);
	}

	public static String INVALID_ITEM_DATA(String input) {
		return String.format("&cSorry; &e%s&c isn't a valid data value", input);
	}

	public static String PROPER_USAGE(String usage) {
		return String.format("&ePlease use &a%s", usage);
	}

	public static String FRIEND_REQUEST_RECEIVED(String playerRequesting) {
		return String.format("&b%s&a has added you as a friend, do &e/friends accept %s &ato accept, or &e/friends deny %s&a to deny them", playerRequesting, playerRequesting, playerRequesting);
	}

	public static String FRIEND_REQUEST_SENT(String playerName) {
		return String.format("&aYour friend request to &e%s&a has been sent", playerName);
	}

	public static String FRIEND_DOESNT_EXIST(String playerName) {
		return String.format("&e%s&c isn't on your friends list", playerName);
	}

	public static String FRIEND_REQUEST_DENIED(String playerName) {
		return String.format("&aYou've denied the friend request from &e%s", playerName);
	}

	public static String FRIEND_DENIED_REQUEST(String playerName) {
		return String.format("&e%s&c has denied your friend request", playerName);
	}

	public static String FRIEND_ALREADY_EXISTS(String playerName) {
		return String.format("&cYou're already friends with &e%s", playerName);
	}

	public static String FRIEND_REQUEST_ALREADY_EXISTS(String playerName) {
		return String.format("&cYou've already sent &e%s&c a friend request", playerName);
	}

	public static String FRIEND_DELETED_FROM_FRIEND(String playerName) {
		return String.format("&b%s&e has removed you from their friends list", playerName);
	}

	public static String FRIEND_DELETED(String playerName) {
		return String.format("&aYou've removed &e%s &afrom your friends list", playerName);
	}

	public static String FRIEND_NO_REQUEST(String playerName) {
		return String.format("&cYou don't have a friend request from &e%s", playerName);
	}

	public static String FRIEND_ACCEPTED_REQUEST(String playerName) {
		return String.format("&aYou've accepted the friend request from &b%s", playerName);
	}

	public static String FRIEND_REQUEST_ACCEPTED(String playerName) {
		return String.format("&b%s&a has accepted your friend request!", playerName);
	}

	public static String TUNNELS_XP_BALANCE(Player player) {
		return String.format("&aYou have &e%s&a Tunnels XP", (int) PlayerHandler.getData(player).getCurrency());
	}

	public static String INVALID_COMMAND_USAGE(String... requiredArguments) {
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

	public static String TELEPORTED_TO(String description) {
		return String.format("&eYou've been teleported to &a%S", description);
	}

	public static String TELEPORTED_TO(String item, String target) {
		return String.format("&eYou've teleported &a%s&e to &a%s", item, target);
	}

	public static String WARPED_TO(String warpName) {
		return String.format("&aYou've warped to &e%s", warpName);
	}

	public static String ITEM_GIVEN_COMMAND(String item, int amount) {
		return String.format("&aAdded &e%s &aof &e%s&a to your inventory", amount, item);
	}

	public static String ITEM_GIVEN_COMMAND(String item) {
		return ITEM_GIVEN_COMMAND(item, 1);
	}

	public static String SPEED_UPDATED(boolean isFlying, double speed) {
		return String.format("&aYou've set your &e%s&a speed to &e%s&a; to reset it to default use &e/speed", isFlying ? "fly" : "walk", speed);
	}

	public static String SPEED_RESET(boolean isFlying) {
		return String.format("&aYou've reset your &e%s&a speed to default", isFlying ? "fly" : "walk");
	}

	public static String WORLD_DOESNT_EXIST(String worldName) {
		return String.format("&cThe world &e%s&c doesn't exist, or isn't loaded", worldName);
	}

	public static String TIME_CHANGED(String worldName, String time) {
		return String.format("&aThe time for the world &7%s&a has been set to &e%s", worldName, time);
	}

	public static String ADDED_XP(String playerName, int amount) {
		return String.format("&aYou've added &e%s&a tunnels xp to &b%s", amount, playerName);
	}

	public static String ITEM_ENCHANTED(String enchantmentName) {
		return String.format("&aYou've added the '&e%s&a' enchantment to your item", enchantmentName);
	}

	public static String ITEM_ENCHANTED(String enchantmentName, int level) {
		return String.format("&aYou've added level &c%s &a'&e%s&a' enchantment to your item", level, enchantmentName);
	}

	public static String ENCHANTMENT_DOESNT_EXIST(String enchantmentName) {
		return String.format("&e'&c%s&e' isn't a valid enchantment", enchantmentName);
	}

	public static String PLAYER_FED(String playerName) {
		return String.format("&e%s&a has been fed", playerName);
	}

	public static String FURNACE_RECIPE(ItemStack smeltResult, ItemStack itemRequired) {
		return String.format("&e%s&a is produced by smelting &e%s", ItemHandler.getFormattedMaterialName(smeltResult), ItemHandler.getFormattedMaterialName(itemRequired));
	}

	public static String RECIPE_NOT_FOUND(ItemStack itemStack) {
		return String.format("&eUnable to find recipe for &c%s", ItemHandler.getFormattedMaterialName(itemStack));
	}

	public static String INVALID_MOB_TYPE(String mobType) {
		return String.format("&c%s&e is an invalid mob type", mobType);
	}

	public static String HEALED_PLAYER(String playerName) {
		return String.format("&e%s&a has been healed!", playerName);
	}

	public static String[] PLAYER_BANNED_GLOBAL_MESSAGE(String playerName, String banIssuer, String reason, String duration) {
		return new String[]{
				String.format("&e%s&a was banned by &e%s", playerName, banIssuer),
				String.format("&e - Reason: &c%s", reason),
				String.format("&e - Expires: &c%s", duration)
		};
	}

	public static String PLAYER_UNBANNED(String playerName, String pardonIssuer) {
		return String.format("&a%s&e has been unbanned by &a%s", playerName, pardonIssuer);
	}

	public static String PLAYER_NOT_BANNED(String playerName) {
		return String.format("&e%s&c is not banned", playerName);
	}

	public static String WARP_DOESNT_EXIST(String warpName) {
		return String.format("&eThe warp '&c%s&e' doesn't exist", warpName);
	}

	public static String WARP_CREATED(String warpName) {
		return String.format("&eThe warp '&a%s&e' has been created!", warpName);
	}

	public static String WARP_ALREADY_EXISTS(String warpName) {
		return String.format("&eThe warp '&c%s&e' already exists", warpName);
	}

	public static String FAILED_TO_GET_NPC_ID(int id) {
		return String.format("Failed to get the NPC with id [%s]", id);
	}

	public static String NPC_NAME_SHORTENED(String from, String to) {
		return String.format("Name '%s' has been shortened to '%s'", from, to);
	}

	public static String FAILED_TO_RETRIEVE_PACKET(String protocol, String sender, int id) {
		return String.format("Failed to retrieve the packet object for: %s, %s, %s", protocol, sender, id);
	}

	public static String PLAYER_KICKED(String player, String reason) {
		return String.format("&e%s &ahas been kicked with the reason being: '&e%s&a'", player, reason);
	}

}
