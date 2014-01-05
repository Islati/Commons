package com.caved_in.commons;

public class Messages {
	public static final String INVENTORY_CLEARED = "&aYour inventory has been cleared";
	public static final String PLAYER_OFFLINE = "&cThe requested player is offline";

	public static String PLAYER_OFFLINE(String playerName) {
		return "&e" + playerName + " &cis offline";
	}

	public static String TELEPORTED_TO_PLAYER(String playerName) {
		return "&eYou were teleported to &a" + playerName;
	}

	public static String ITEM_DOESNT_EXIST(String itemName) {
		return "&cSorry, but &e" + itemName + "&c isn't a valid item";
	}

	public static String INVALID_ITEM_DATA(String input) {
		return "&cSorry; &e" + input + "&c isn't a valid data value";
	}

	public static String INVALID_COMMAND_USAGE(String... requiredArguments) {
		String[] requiredArgs = requiredArguments.clone();
		String returnString = "&cThis command requires the following arguments: ";
		if (requiredArgs.length > 0) {
			for (int I = 0; I < requiredArgs.length; I++) {
				returnString += "&e[" + requiredArgs[I] + "]&r" + (I >= (requiredArgs.length - 1) ? ", " : "");
			}
			return returnString;
		} else {
			return "&cPlease validate the syntax of the command you performed";
		}
	}
}
