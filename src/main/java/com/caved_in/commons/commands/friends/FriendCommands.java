package com.caved_in.commons.commands.friends;

import com.caved_in.commons.Commons;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.commands.CommandController.SubCommandHandler;
import com.caved_in.commons.menu.HelpScreen;
import com.caved_in.commons.friends.Friend;
import com.caved_in.commons.friends.FriendHandler;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.sql.FriendStatus;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class FriendCommands {
	@CommandHandler(name = "friends"/*, permission = "tunnels.common.friends"*/)
	public void friendsCommand(Player player, String[] commandArgs) {
		if (commandArgs.length == 0) {
			player.sendMessage(StringUtil.formatColorCodes("&ePlease use &a/friends help"));
		}
	}

	@SubCommandHandler(name = "help", parent = "friends")
	public void friendsHelpCommand(Player player, String commandArgs[]) {
		if (commandArgs.length > 1) {
			if (commandArgs.length >= 2) {
				if (commandArgs[1] != null && !commandArgs[1].isEmpty()) {
					String helpPage = commandArgs[1];
					if (StringUtils.isNumeric(helpPage)) {
						int page = Integer.parseInt(helpPage);
						getFriendsCommandHelpScreen().sendTo(player, page, 6);
					} else {
						player.sendMessage(StringUtil.formatColorCodes("&c Please include a page number for the help screen; &e" + helpPage + "&c is not a number"));
					}
				}
			} else {
				getFriendsCommandHelpScreen().sendTo(player, 1, 6);
			}
		} else {
			getFriendsCommandHelpScreen().sendTo(player, 1, 6);
		}
	}

	@SubCommandHandler(name = "add", parent = "friends")
	public void friendsAddCommand(Player player, String[] commandArgs) {
		String playerName = player.getName();
		if (commandArgs.length > 1) {
			String usernameToAdd = commandArgs[1];
			if (usernameToAdd != null && !usernameToAdd.isEmpty()) {
				if (!FriendHandler.getFriendList(player.getName()).isFriendsWith(usernameToAdd)) {
					FriendStatus insertStatus = Commons.friendDatabase.insertFriendRequest(playerName, usernameToAdd);
					switch (insertStatus) {
						case REQUESTED:
							if (PlayerHandler.isOnline(usernameToAdd)) {
								Bukkit.getPlayer(usernameToAdd).sendMessage(StringUtil.formatColorCodes("&b" + playerName + "&a has added you as a friend, do &e/friends accept " + player.getName() + "&a to accept, or &e/friends deny " + player.getName() + " &a to deny them."));
								FriendHandler.getFriendList(usernameToAdd).addFriend(new Friend(usernameToAdd, playerName, false));
							}
							FriendHandler.getFriendList(playerName).addFriend(new Friend(playerName, usernameToAdd, false));
							player.sendMessage(StringUtil.formatColorCodes("&aYour request has been sent to &e" + usernameToAdd));
							break;
						case ALREADY_FRIENDS:
							player.sendMessage(StringUtil.formatColorCodes("&cYou're already friends with &e" + usernameToAdd));
							break;
						case ALREADY_PENDING:
							player.sendMessage(StringUtil.formatColorCodes("&cYou've already sent &e" + usernameToAdd + "&c a friend request"));
							break;
						default:
							break;
					}
				} else {
					player.sendMessage(StringUtil.formatColorCodes("&c" + usernameToAdd + "&e is already on your friends list"));
				}
			} else {
				player.sendMessage(StringUtil.formatColorCodes("&cInvalid arguments. Please check &e/friends help&c for the proper syntax"));
			}
		} else {
			player.sendMessage(StringUtil.formatColorCodes("&cInvalid arguments. Please check &e/friends help&c for the proper syntax"));
		}
	}

	@SubCommandHandler(name = "remove", parent = "friends")
	public void friendsRemoveCommand(Player player, String[] commandArgs) {
		String playerName = player.getName();
		if (commandArgs.length > 1) {
			String usernameToRemove = commandArgs[1];
			if (usernameToRemove != null && !usernameToRemove.isEmpty()) {
				if (FriendHandler.getFriendList(playerName).isFriendsWith(usernameToRemove)) {
					Commons.friendDatabase.deleteFriendRequest(playerName, usernameToRemove);
					if (PlayerHandler.isOnline(usernameToRemove)) {
						Bukkit.getPlayer(usernameToRemove).sendMessage(StringUtil.formatColorCodes("&b" + playerName + "&e has removed you from their friends list"));
						FriendHandler.getFriendList(usernameToRemove).removeFriend(playerName);
					}
					FriendHandler.getFriendList(playerName).removeFriend(usernameToRemove);
					player.sendMessage(StringUtil.formatColorCodes("&aYou deleted &e" + usernameToRemove + "&a has been removed from your friends list"));
				} else {
					player.sendMessage(StringUtil.formatColorCodes("&c" + usernameToRemove + "&e is already on your friends list"));
				}
			} else {
				player.sendMessage(StringUtil.formatColorCodes("&cInvalid arguments. Please check &e/friends help&c for the proper syntax"));
			}
		} else {
			player.sendMessage(StringUtil.formatColorCodes("&cInvalid arguments. Please check &e/friends help&c for the proper syntax"));
		}
	}

	public void friendsBlockCommand(Player player, String[] commandArgs) {
		// TODO Write table in SQL for player blocks, seperate from player
	}

	@SubCommandHandler(name = "requests", parent = "friends")
	public void friendsRequestListCommand(Player player, String[] commandArgs) {
		String playerName = player.getName();
		List<Friend> playerFriends = FriendHandler.getFriendList(playerName).getUnacceptedFriends();
		if (playerFriends.size() > 0) {
			if (commandArgs.length >= 2) {
				if (commandArgs[1] != null && !commandArgs[1].isEmpty()) {
					String helpPage = commandArgs[1];
					if (StringUtils.isNumeric(helpPage)) {
						int page = Integer.parseInt(helpPage);
						getRequestsHelpScreen(playerFriends).sendTo(player, page, 6);
					}
				}
			} else {
				getRequestsHelpScreen(playerFriends).sendTo(player, 1, 6);
			}
		} else {
			player.sendMessage(StringUtil.formatColorCodes("&eYou don't have any pending friend requests"));
		}
	}

	@SubCommandHandler(name = "deny", parent = "friends")
	public void friendsDenyCommand(Player player, String[] commandArgs) {
		String playerName = player.getName();
		if (commandArgs.length >= 2) {
			String denyName = commandArgs[1];
			if (denyName != null && !denyName.isEmpty()) {
				if (Commons.friendDatabase.hasFriendRequest(playerName, denyName)) {
					Commons.friendDatabase.deleteFriendRequest(playerName, denyName);
					if (PlayerHandler.isOnlineFuzzy(denyName)) {
						String pDenyName = Bukkit.getPlayer(denyName).getName();
						Bukkit.getPlayer(pDenyName).sendMessage(StringUtil.formatColorCodes("&e" + playerName + "&c has denied your friend request"));
						FriendHandler.getFriendList(pDenyName).removeFriend(playerName);
					}
					FriendHandler.getFriendList(playerName).removeFriend(denyName);
					player.sendMessage(StringUtil.formatColorCodes("&aYou've denied the friend request from &e" + denyName));
				} else {
					player.sendMessage(StringUtil.formatColorCodes("&cYou don't have a pending friend request from &e" + denyName));
				}
			} else {
				player.sendMessage(StringUtil.formatColorCodes("&cInvalid command syntax, please use &e/friends help&c to see the proper syntax"));
			}
		} else {
			player.sendMessage(StringUtil.formatColorCodes("&cInvalid command syntax, please use &e/friends help&c to see the proper syntax"));
		}
	}

	@SubCommandHandler(name = "accept", parent = "friends")
	public void friendsAcceptCommand(Player player, String[] commandArgs) {
		String playerName = player.getName();
		if (commandArgs.length >= 2) {
			String acceptName = commandArgs[1];
			if (acceptName != null && !acceptName.isEmpty()) {
				if (Commons.friendDatabase.hasFriendRequest(playerName, acceptName)) {
					Commons.friendDatabase.acceptFriendRequest(playerName, acceptName);
					if (PlayerHandler.isOnlineFuzzy(acceptName)) {
						String playerAcceptName = Bukkit.getPlayer(acceptName).getName();
						Bukkit.getPlayer(acceptName).sendMessage(StringUtil.formatColorCodes("&b" + playerName + "&a has accepted your friend request!"));
						FriendHandler.getFriendList(playerAcceptName).addFriend(new Friend(acceptName, playerName, true));
					}
					player.sendMessage(StringUtil.formatColorCodes("&aYou've accepted the friend request from &b" + acceptName));
					FriendHandler.getFriendList(playerName).addFriend(new Friend(playerName, acceptName, true));
				} else {
					player.sendMessage(StringUtil.formatColorCodes("&cYou don't have a pending friend request from &e" + acceptName));
				}
			} else {
				player.sendMessage(StringUtil.formatColorCodes("&cInvalid command syntax, please use &e/friends help&c to see the proper syntax"));
			}
		} else {
			player.sendMessage(StringUtil.formatColorCodes("&cInvalid command syntax, please use &e/friends help&c to see the proper syntax"));
		}
	}

	@SubCommandHandler(name = "list", parent = "friends")
	public void friendsListCommand(Player player, String[] commandArgs) {
		String playerName = player.getName();
		List<Friend> playerFriends = FriendHandler.getFriendList(playerName).getFriends();
		if (commandArgs.length >= 2) {
			if (commandArgs[1] != null && !commandArgs[1].isEmpty()) {
				String helpPage = commandArgs[1];
				if (StringUtils.isNumeric(helpPage)) {
					int page = Integer.parseInt(helpPage);
					getFriendsListScreen(playerFriends).sendTo(player, page, 6);
				}
			}
		} else {
			getFriendsListScreen(playerFriends).sendTo(player, 1, 6);
		}
	}

	private HelpScreen getFriendsListScreen(List<Friend> friendsList) {
		HelpScreen requestScreen = new HelpScreen("Your friends list");
		requestScreen.setSimpleColor(ChatColor.WHITE);
		requestScreen.setHeader(ChatColor.YELLOW + "<name> Page <page> of <maxpage>");
		requestScreen.setFormat("<name> is <desc>");

		for (Friend friend : friendsList) {
			boolean isOnline = PlayerHandler.isOnline(friend.getFriendName());
			ChatColor friendColor = isOnline ? ChatColor.GREEN : ChatColor.RED;
			requestScreen.setEntry(friendColor + friend.getFriendName() + ChatColor.YELLOW, "currently " + friendColor + (isOnline ? "online" : "offline") + ".");
		}

		return requestScreen;
	}

	private HelpScreen getRequestsHelpScreen(List<Friend> friendsList) {
		HelpScreen requestScreen = new HelpScreen("Friend Requests");
		requestScreen.setFlipColor(ChatColor.GREEN, ChatColor.DARK_GREEN);
		requestScreen.setHeader(ChatColor.YELLOW + "<name> Page <page> of <maxpage>");
		requestScreen.setFormat("<name> wants to add you as a friend!");

		for (Friend friend : friendsList) {
			requestScreen.setEntry(friend.getFriendName(), "");
		}

		return requestScreen;
	}

	private HelpScreen getFriendsCommandHelpScreen() {
		HelpScreen friendScreen = new HelpScreen("Friends Command-Help");
		friendScreen.setFlipColor(ChatColor.GREEN, ChatColor.DARK_GREEN);
		friendScreen.setHeader(ChatColor.YELLOW + "<name> Page <page> of <maxpage>");
		friendScreen.setFormat("<name> - <desc>");
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
