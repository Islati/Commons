package com.caved_in.commons.permission;

public class Perms {
	/* Used to allow joining during maintenance mode */
	public static final String MAINTENANCE_WHITELIST = "commons.maintenance.join";
	/* Used to see through player disguises */
	public static final String BYPASS_DISGUISED_PLAYERS = "commons.disguise.nickvisible";
	public static final String BYPASS_CHAT_SILENCE = ("commons.silence.bypass");
	/* All commands and their permissions */

	public static final String COMMAND_BACK = ("commons.command.back");

	public static final String COMMAND_FEED = ("commons.command.feed");

	public static final String COMMAND_FIREWORKS = ("commons.command.fireworks");

	public static final String COMMAND_BLOCKTEXT = ("commons.command.blocktext");

	public static final String COMMAND_BUYPREMIUM = ("commons.command.buypremium");

	public static final String BAN = ("commons.command.ban");

	public static final String CLEAR_INVENTORY = ("commons.clearinventory");
	public static final String CLEAR_INVENTORY_OTHER = ("commons.clearinventory.other");
	/* NameTag Permissions */
	public static final String BLACK_NAMETAG = ("commons.nametag.black");
	public static final String YELLOW_NAMETAG = ("commons.nametag.yellow");
	public static final String LIGHT_PURPLE_NAMETAG = ("commons.nametag.lightpurple");
	public static final String DARK_GRAY_NAMETAG = ("commons.nametag.darkgray");
	public static final String DARK_GREEN_NAMETAG = ("commons.nametag.darkgreen");
	public static final String GREEN_NAMETAG = ("commons.nametag.green");
	public static final String GRAY_NAMETAG = ("commons.nametag.gray");
	public static final String GOLD_NAMETAG = ("commons.nametag.gold");
	public static final String PURPLE_NAMETAG = ("commons.nametag.purple");
	public static final String DARK_RED_NAMETAG = ("commons.nametag.darkred");
	public static final String RED_NAMETAG = ("commons.nametag.red");
	public static final String DARK_BLUE_NAMETAG = ("commons.nametag.darkblue");
	public static final String BLUE_NAMETAG = ("commons.nametag.blue");
	public static final String AQUA_NAMETAG = ("commons.nametag.aqua");
	public static final String WHITE_NAMETAG = ("commons.nametag.white");
	/* Debug mode allows player to get feedback on the things they're doing */
	public static final String DEBUG_MODE = ("commons.debugmode");
	/* Action-Related Permissions */
	public static final String BLOCK_BREAK = ("commons.block.break");
	public static final String BLOCK_PLACE = ("commons.block.place");
	public static final String TELEPORT_BACK_ON_DEATH = ("commons.back.death");
	public static final String TELEPORT_BACK_END_PORTAL = ("commons.back.endportal");
	public static final String TELEPORT_BACK_NETHER_PORTAL = ("commons.back.netherportal");
	public static final String TELEPORT_BACK_ON_WARP = ("commons.back.warp");
}
