package com.caved_in.commons.commands.moderation;

import com.caved_in.commons.Commons;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.config.Messages;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeHandler.TimeType;
import com.caved_in.commons.player.PlayerHandler;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishmentCommands {

	@CommandHandler(name = "ban", description = "Bans a player permanately, or temporarily across all servers", permission = "tunnels.common.ban", usage = "/ban [Name] [Reason] <Time>")
	public void onBanCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String playerName = args[0];
			if (!Commons.bansDatabase.isBanned(playerName)) {
				String banReason = "";
				String bannedBy = "";
				int timeArg = 0;
				long banExpires = 0L;
				boolean PermBan = false;
				if (sender instanceof Player) {
					bannedBy = ((Player) sender).getName();
				} else {
					bannedBy = "Console";
				}
				if (args.length >= 1) {
					if (args[1] != null) {
						for (int I = 1; I < args.length; I++) {
							if (!args[I].startsWith("t:")) {
								banReason += args[I] + " ";
							} else {
								timeArg = I;
								break;
							}
						}
					} else {
						sender.sendMessage(ChatColor.GREEN + "[Tunnels Network] " + ChatColor.RED + "You need to give a reason when banning someone.");
						return;
					}

					if (timeArg != 0) {
						String TimeOrReason = args[timeArg];
						if (TimeOrReason.toLowerCase().contains("t:")) {
							String TimeUnit = "";
							int TimeLength = 0;
							for (char Char : StringUtils.substringAfter(TimeOrReason.toLowerCase(), "t:").toCharArray()) {
								if (!Character.isDigit(Char)) {
									TimeUnit = String.valueOf(Char);
									String TimeBetween = StringUtils.substringBetween(TimeOrReason.toLowerCase(), "t:", TimeUnit.toLowerCase());
									if (StringUtils.isNumeric(TimeBetween)) {
										TimeLength = Integer.parseInt(TimeBetween);
										switch (TimeUnit) {
											case "y":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Years));
												break;
											case "m":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Months));
												break;
											case "w":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Weeks));
												break;
											case "d":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Days));
												break;
											case "h":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Hours));
												break;
											default:
												break;
										}
										break;
									} else {
										sender.sendMessage(TimeOrReason + " is not a valid measurement of time...");
										return;
									}
								}
							}
							if (TimeLength == 0 || TimeUnit == "") {
								sender.sendMessage("Error processing Command [--" + TimeOrReason + "--]");
								return;
							}
						}
					}

					if (banExpires == 0L) {
						banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(10, TimeType.Years));
						PermBan = true;
					}

					if (Bukkit.getPlayer(playerName) != null) {
						Player bPlayer = Bukkit.getPlayer(playerName);
						playerName = bPlayer.getName();
						bPlayer.kickPlayer(banReason);
						Commons.bansDatabase.insertPunishment(PunishmentType.BAN, playerName, banReason, bannedBy, banExpires);
						PlayerHandler.sendMessageToAllPlayers(ChatColor.GREEN + Messages.messagePrefix + ChatColor.YELLOW + playerName + ChatColor.GREEN + " was banned by " + ChatColor.YELLOW + bannedBy, ChatColor.RED + " - Reason: " + banReason, ChatColor.RED + " - Expires: " + (PermBan ? "Never" : (TimeHandler.getDurationBreakdown(banExpires - System.currentTimeMillis()))));
					} else {
						OfflinePlayer bPlayer = Bukkit.getOfflinePlayer(playerName);
						if (bPlayer.hasPlayedBefore()) {
							Commons.bansDatabase.insertPunishment(PunishmentType.BAN, playerName, banReason, bannedBy, banExpires);
							PlayerHandler.sendMessageToAllPlayers(ChatColor.GREEN + Messages.messagePrefix + ChatColor.YELLOW + playerName + ChatColor.GREEN + " was banned by " + ChatColor.YELLOW + bannedBy, ChatColor.RED + " - Reason: " + banReason, ChatColor.RED + " - Expires: " + (PermBan ? "Never" : (TimeHandler.getDurationBreakdown(banExpires - System.currentTimeMillis()))));
						} else {
							sender.sendMessage(ChatColor.GREEN + Messages.messagePrefix + ChatColor.YELLOW + bPlayer.getName() + ChatColor.GREEN + " isn't a " +
									"valid player; They've never played on this server.");
						}
					}
				} else {
					sender.sendMessage("You need to provide a reason for banning");
				}
			}
		}
	}

	@CommandHandler(name = "unban", description = "Unban / pardon a user from their ban", permission = "tunnels.common.unban", usage = "/unban [Name]")
	public void UnbanCommand(CommandSender Sender, String[] Args) {
		if (Args.length > 0) {
			String Unban = Args[0];
			String Unbanner = "";
			if (Sender instanceof Player) {
				Unbanner = ((Player) Sender).getName();
			} else {
				Unbanner = "Console";
			}
			if (Commons.bansDatabase.pardonPlayer(Unban, Unbanner)) {
				PlayerHandler.sendMessageToAllPlayers("&a" + Messages.messagePrefix + "&e" + Unban + "&a was unbanned by &e" + Unbanner);
			} else {
				Sender.sendMessage(Unban + " has failed to be unbanned; They're either not banned, or an error was made");
			}
		} else {
			Sender.sendMessage("You need to include a name to pardon / unban");
		}
	}

	@CommandHandler(name = "pardon", description = "Unban / pardon a user from their ban", permission = "tunnels.common.unban", usage = "/pardon [Name]")
	public void PardonCommand(CommandSender Sender, String[] Args) {
		if (Args.length > 0) {
			String Unban = Args[0];
			String Unbanner = "";
			if (Sender instanceof Player) {
				Unbanner = ((Player) Sender).getName();
			} else {
				Unbanner = "Console";
			}
			if (Commons.bansDatabase.pardonPlayer(Unban, Unbanner)) {
				PlayerHandler.sendMessageToAllPlayers("&a" + Messages.messagePrefix + "&e" + Unban + "&a was unbanned by &e" + Unbanner);
				Sender.sendMessage(Unban + " has been successfully unbanned.");
			} else {
				Sender.sendMessage(Unban + " has failed to be unbanned; They're either not banned, or an error was made");
			}
		} else {
			Sender.sendMessage("You need to include a name to pardon / unban");
		}
	}

}
