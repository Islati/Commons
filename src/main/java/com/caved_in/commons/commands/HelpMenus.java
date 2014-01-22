package com.caved_in.commons.commands;

import com.caved_in.commons.friends.Friend;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.ChatColor;

import java.util.Map;
import java.util.Set;

public class HelpMenus {

	enum ItemFormat {
		DOUBLE_DASH("<name> -- <desc>"),
		SINGLE_DASH("<name> - <desc>"),
		IS("<name> is <desc>"),
		FRIEND_REQUEST("<name> wants to add you as a friend!");

		private String formatting;

		private ItemFormat(String formatting) {
			this.formatting = formatting;
		}

		@Override
		public String toString() {
			return this.formatting;
		}
	}

	public enum PageDisplay {
		DEFAULT("<name> (Page <page> of <maxpage>)"),
		SHORTHAND("<name> (P.<page>/<maxpage>)");

		private String formatting;

		private PageDisplay(String formatting) {
			this.formatting = formatting;
		}

		@Override
		public String toString() {
			return this.formatting;
		}
	}

	public static HelpScreen generateHelpScreen(String menuName, PageDisplay pageDisplay, ItemFormat itemFormat, ChatColor flipColorEven, ChatColor flipColorOdd) {
		HelpScreen helpScreen = new HelpScreen(menuName);
		helpScreen.setHeader(pageDisplay.toString());
		helpScreen.setFormat(itemFormat.toString());
		helpScreen.setFlipColor(flipColorEven, flipColorOdd);
		return helpScreen;
	}

	public static HelpScreen generateHelpScreen(String menuName, PageDisplay pageDisplay, ItemFormat itemFormat, ChatColor flipColorEven, ChatColor flipColorOdd, Map<String, String> helpItems) {
		HelpScreen helpScreen = new HelpScreen(menuName);
		helpScreen.setHeader(pageDisplay.toString());
		helpScreen.setFormat(itemFormat.toString());
		helpScreen.setFlipColor(flipColorEven, flipColorOdd);
		for (Map.Entry<String, String> menuItem : helpItems.entrySet()) {
			helpScreen.setEntry(menuItem.getKey(), menuItem.getValue());
		}
		return helpScreen;
	}

	private static HelpScreen getNickHelpScreen() {
		HelpScreen nicknameHelpsScreen = generateHelpScreen("Nickname Command Help", PageDisplay.DEFAULT, ItemFormat.SINGLE_DASH, ChatColor.GREEN, ChatColor.DARK_GREEN);
		nicknameHelpsScreen.setEntry("/nick help", "Shows the help menu");
		nicknameHelpsScreen.setEntry("/nick off [player]", "Turns the nickname off for yourself, or another player");
		nicknameHelpsScreen.setEntry("/nick <Name>", "Disguise yourself as another player");
		nicknameHelpsScreen.setEntry("/nick <player> <Name>", "Disguise another player");
		return nicknameHelpsScreen;
	}

	public static HelpScreen getFriendsListScreen(Set<Friend> friendsList) {
		HelpScreen requestScreen = generateHelpScreen("Your friends list", PageDisplay.DEFAULT, ItemFormat.IS, ChatColor.WHITE, ChatColor.WHITE);
		for (Friend friend : friendsList) {
			boolean isOnline = PlayerHandler.isOnline(friend.getFriendName());
			ChatColor friendColor = isOnline ? ChatColor.GREEN : ChatColor.RED;
			requestScreen.setEntry(friendColor + friend.getFriendName() + ChatColor.YELLOW, "currently " + friendColor + (isOnline ? "online" : "offline") + ".");
		}
		return requestScreen;
	}

	public static HelpScreen getFriendRequestsHelpScreen(Set<Friend> friendsList) {
		HelpScreen requestScreen = generateHelpScreen("Friend Requests", PageDisplay.DEFAULT, ItemFormat.FRIEND_REQUEST, ChatColor.GREEN, ChatColor.DARK_GREEN);
		for (Friend friend : friendsList) {
			requestScreen.setEntry(friend.getFriendName(), "");
		}
		return requestScreen;
	}

	public static HelpScreen getFriendsCommandHelpScreen() {
		HelpScreen friendScreen = generateHelpScreen("Friends Command-Help", PageDisplay.DEFAULT, ItemFormat.SINGLE_DASH, ChatColor.GREEN, ChatColor.DARK_GREEN);
		friendScreen.setEntry("/friends add <username>", "Send a player a friend request with the given message");
		friendScreen.setEntry("/friends help", "This help-menu");
		friendScreen.setEntry("/friends accept <Username>", "Accept the friend request from this user (if you have one)");
		friendScreen.setEntry("/friends block <Username>", "Block this user from sending you friend requests");
		friendScreen.setEntry("/friends requests", "See all of the friend-requests you have.");
		friendScreen.setEntry("/friends list", "See a list of all your friends");
		friendScreen.setEntry("/friends remove <Username>", "Remove this user from you friends");
		friendScreen.setEntry("/friends deny <Username>", "Deny a friend request from this user");
		return friendScreen;
	}
}
