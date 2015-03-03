package com.caved_in.commons;

import com.caved_in.commons.block.Direction;
import com.caved_in.commons.cuboid.Cuboid;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.world.Arena;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Coords;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.location.PreTeleportLocation;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.Str;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Date;
import java.util.Map;

public class Messages {
	public static final String MESSAGE_PREFIX = "";
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

	public static final String NO_TELEPORT_BACK_LOCATION = "&eThere's no going back now...";

	public static final String MESSAGE_REQUIRED = "&cYou must enter a message to reply with";

	public static final String NO_RECENT_MESSAGES = "&cYou haven't received any messages from anybody";

	public static final String ERROR_RETRIEVING_PLAYER_DATA = "&cThere was an error retrieving the players data";

	public static final String YELLOW_INDENT_ARROW = " &e- &r";

	public static final String HAT_EQUIPPED = "&aEnjoy your hat!";

	public static final String HAT_UNEQUIPPED = "&7*Poof* &e&oYou're no longer wearing a hat!";

	public static final String NO_WARPS = "&eNo Warps have been set; Create a warp with &c/setwarp <name>";

	public static final String DEBUG_ACTION_REQUIRES_HAND_ITEM = "You require an item in your hand to use this debug action";

	public static final String GADGET_RELOADED = "&7Your gadget's been reloaded.";

	public static final String ERROR_RELOADING_GADGET = "&7Failed to reload your gadget.";

	public static final String COMMAND_DISABLED = "&cSorry, this command is disabled.";

	public static final String CANT_AS_CONSOLE = "You can't do this as console";

	private static final String INSUFFICIENT_PERMISSION_MESSAGE = "&cYou don't have the required permissions to %s.";

	public static final String GOD_MODE_ENABLED = "&6God Mode&e has been &a&lENABLED!";
	public static final String GOD_MODE_DISABLED = "&6God Mode&e has been &a&lDISABLED!";

	public static String arenaAdded(String world) {
		return String.format("&aThe world '&e%s'&a has been added as an arena!", world);
	}

	public static String arenaAdded(World world) {
		return arenaAdded(world.getName());
	}

	public static String arenaAddError(String world) {
		return String.format("&cThere was an error while adding the arena '&e%s&c'", world);
	}

	public static String arenaAddError(World world) {
		return arenaAdded(world.getName());
	}

	public static String arenaAlreadyExists(String world) {
		return String.format("&cArena '&e%s'&c already exists", world);
	}

	public static String arenaAlreadyExists(World world) {
		return arenaAlreadyExists(world.getName());
	}

	public static String arenaSpawnAdded(Arena arena, Location loc) {
		return String.format("&aSpawn point added to &e%s&a at &6%s", arena.getArenaName(), locationCoords(loc));
	}

	public static String cuboidDescription(Cuboid cuboid) {
		return String.format("Cuboid: %s,%s,%s,%s => %s,%s,%s", cuboid.getWorldName(), cuboid.getLowerX(), cuboid.getLowerY(), cuboid.getLowerZ(), cuboid.getUpperX(), cuboid.getUpperY(), cuboid.getUpperZ());
	}

	public static String gadgetExpired(Gadget gadget) {
		return String.format("&eThe '&6%s&e' gadget has degraded fully.", Items.getName(gadget.getItem()));
	}

	public static String gadgetReloaded(Gadget item) {
		return String.format("&7&l'&r&7%s&r&7&l' has been reloaded", Items.getName(item.getItem()));
	}

	public static String gunNameAmmoFormat(String name, int ammo) {
		return String.format("%s &r&d<&f%s&d>", name, ammo);
	}

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

	public static String playerFacingDirection(Direction dir) {
		return String.format("&aYou're facing &e%s", dir.name());
	}

	public static String playerFacingDirection(Player player) {
		return playerFacingDirection(Players.getDirection(player));
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

	public static String playerTeleportedToYou(String playerName) {
		return String.format("&a%s&e teleported to you!", playerName);
	}

	public static String playerTeleportedTo(String description) {
		return String.format("&eYou've been teleported to &a%S", description);
	}

	public static String playerTeleportedTo(double[] xyz) {
		return playerTeleportedTo(new Coords(xyz));
	}

	public static String playerTeleportedTo(Coords xyz) {
		return String.format("&eYou've been teleported to &a%sx,%sy,%sz", xyz.getX(), xyz.getY(), xyz.getZ());
	}

	public static String playerXpBalance(Player player) {
		return String.format("&aYou have &e%s&a Tunnels XP", (int) Commons.getInstance().getPlayerHandler().getData(player).getCurrency());
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
		return String.format("&aYou've set your &e%s&a speed to &e%s", isFlying ? "fly" : "walk", speed);
	}

	public static String playerSpeedReset(boolean isFlying) {
		return String.format("&aYou've reset your &e%s&a speed to default", isFlying ? "fly" : "walk");
	}

	public static String playerKicked(String player, String reason) {
		return String.format("&e%s &ahas been kicked with the reason being: '&e%s&a'", player, reason);
	}

	public static String playerDebugModeChange(MinecraftPlayer minecraftPlayer) {
		return String.format("&aYou're &e%s&a in debug mode.", minecraftPlayer.isInDebugMode() ? "now" : "no longer");
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

	public static String playerSmited(Player target, Player smiter) {
		return String.format("&e&l%s&r&e has been smited by &6%s", target.getName(), smiter.getName());
	}

	public static String entityRemovedEntities(int amount) {
		return String.format("&eRemoved &c%s&e mobs", amount);
	}

	public static String insufficientPreTeleportPermissions(PreTeleportLocation loc) {
		String action = "";
		switch (loc.getType()) {
			case END_PORTAL:
				action = "teleport back after entering the end";
				break;
			case ENDER_PEARL:
				action = "teleport back after throwing an ender pearl";
				break;
			case NETHER_PORTAL:
				action = "teleport back after entering the nether.";
				break;
			case DEATH:
				action = "teleport back on death";
				break;
			case WARP:
				action = "teleport back after warping";
				break;
			default:
				break;
		}
		return String.format(INSUFFICIENT_PERMISSION_MESSAGE, action);
	}

	public static String invalidArmorSet(String name) {
		return String.format("&e%s&c is not a valid type of armor-set.", name);
	}

	public static String itemEnchantmentAdded(String enchantmentName) {
		return String.format("&aYou've added the '&e%s&a' enchantment to your item", enchantmentName);
	}

	public static String itemEnchantmentAdded(String enchantmentName, int level) {
		return String.format("&aYou've added level &c%s &a'&e%s&a' enchantment to your item", level, enchantmentName);
	}

	public static String itemId(ItemStack item) {
		return String.format("&eThe id for &o%s&r&e is &a%s", Items.getName(item), item.getTypeId());
	}

	public static String itemId(String name, Material material) {
		return String.format("&aThe id for &e&o%s&r&e is &a%s.", name, material.getId());
	}

	public static String[] itemInfo(ItemStack itemStack) {
		String itemLore = "&7No Lore";
		if (Items.hasLore(itemStack)) {
			itemLore = StringUtil.joinString(Items.getLore(itemStack), String.format("\n%s%s", YELLOW_INDENT_ARROW, YELLOW_INDENT_ARROW), 0);
		}
		String itemName = Items.getName(itemStack);
		String enchantments = "&7No Enchantments";
		if (Items.hasEnchantments(itemStack)) {
			enchantments = itemEnchantments(itemStack);
		}
		Material type = itemStack.getType();
		short durability = itemStack.getDurability();
		int amount = itemStack.getAmount();
		return new String[]{
				String.format("&e[--&6Item Information&e--]"),
				String.format("%sItem Name: %s", YELLOW_INDENT_ARROW, itemName),
				String.format("%sItem ID: %s", YELLOW_INDENT_ARROW, type.getId()),
				String.format("%sItem Type: %s", YELLOW_INDENT_ARROW, type.name()),
				String.format("%sItem Enchantments: %s", YELLOW_INDENT_ARROW, enchantments),
				String.format("%sItem Durability: %s", YELLOW_INDENT_ARROW, durability),
				String.format("%sItem Amount: %s", YELLOW_INDENT_ARROW, amount),
				String.format("%sItem Lore: %s", YELLOW_INDENT_ARROW, itemLore),
		};
	}


	public static String itemEnchantments(ItemStack itemStack, String format) {
		Map<Enchantment, Integer> itemEnchants = itemStack.getEnchantments();
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<Enchantment, Integer> entry : itemEnchants.entrySet()) {
			stringBuilder.append(String.format("&e%s&o&7(lvl%s)&r", entry.getKey().getName(), String.valueOf(entry.getValue()))).append(", ");
		}
		return stringBuilder.toString();
	}

	public static String itemEnchantments(ItemStack itemStack) {
		return itemEnchantments(itemStack, "&e%s&o&7(lvl%i)&r");
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

	public static String[] locationInfo(Location location) {
		int[] xyz = Locations.getXYZ(location);
		return new String[]{
				"&aLocation information:",
				String.format("%s&aWorld Name: &l%s", YELLOW_INDENT_ARROW, Worlds.getWorldName(location)),
				String.format("%s&X: &l%s", YELLOW_INDENT_ARROW, xyz[0]),
				String.format("%s&Y: &l%s", YELLOW_INDENT_ARROW, xyz[1]),
				String.format("%s&Z: &l%s", YELLOW_INDENT_ARROW, xyz[2]),
		};
	}

	public static String locationCoords(Location loc) {
		return locationCoords("(%sx,%sy,%sz)", loc);
	}

	public static String locationCoords(String format, Location loc) {
		return String.format(format, loc.getX(), loc.getY(), loc.getZ());
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

	public static String[] exceptionInfo(Exception e) {
		return new String[]{
				String.format("&cException Occurred @ &e%s", new Date()),
				String.format("%s%s", YELLOW_INDENT_ARROW, e.getLocalizedMessage()),
				String.format("%s%s", YELLOW_INDENT_ARROW, Str.getStackStr(e))
		};
	}

	public static String packetRetrieveFail(String protocol, String sender, int id) {
		return String.format("Failed to retrieve the packet object for: %s, %s, %s", protocol, sender, id);
	}

	public static String potionInfo(PotionEffect effect) {
		return String.format("&ePotion Type: &6%s\n&eLevel: &6%s", effect.getType().getName(), effect.getAmplifier());
	}

	public static String playerError(Player player, String error) {
		return String.format("&cPlayer Error: &e%s @ (%s)\n&r%s&r &7%s", player.getName(), locationCoords(player.getLocation()), YELLOW_INDENT_ARROW, error);
	}

	public static String properUsage(String usage) {
		return String.format("&ePlease use &a%s", usage);
	}

	public static String permissionRequired(String permissionNode) {
		return String.format("&eYou don't have the permission required &7(%s)&e to perform this action", permissionNode);
	}

	public static String timeUpdated(String worldName, String time) {
		return String.format("&aThe time for the world &7%s&a has been set to &e%s", worldName, time);
	}

	public static String invalidCommandUsage(String... requiredArguments) {
		String[] requiredArgs = requiredArguments.clone();
		String returnString = "&cThis command requires the following arguments: ";
		if (requiredArgs.length > 0) {
			for (int i = 0; i < requiredArgs.length; i++) {
				returnString += "&e[" + requiredArgs[i] + "]&r" + (i < (requiredArgs.length - 1) ? ", " : "");
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
