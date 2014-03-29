package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeHandler;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:38 PM
 */
public class BanCommand {
	@CommandController.CommandHandler(name = "ban", description = "Bans a player permanately, or temporarily across all servers", permission = "tunnels.common.ban", usage = "/ban [Name] [Reason] <Time>")
	public void onBanCommand(CommandSender sender, String[] args) {

		if (args.length > 0) {
			String playerName = args[0];
			if (!Commons.bansDatabase.isBanned(playerName)) {
				String banReason = "";
				String bannedBy = "";
				int timeArg = 0;
				long banExpires = 0L;
				boolean isPermanent = false;
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
						Players.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("ban reason"));
						return;
					}

					if (timeArg != 0) {
						String timeOrReason = args[timeArg];
						if (timeOrReason.toLowerCase().contains("t:")) {
							String timeUnit = "";
							int timeLength = 0;
							for (char argChar : StringUtils.substringAfter(timeOrReason.toLowerCase(), "t:").toCharArray()) {
								if (!Character.isDigit(argChar)) {
									timeUnit = String.valueOf(argChar);
									String timeBetween = StringUtils.substringBetween(timeOrReason.toLowerCase(), "t:", timeUnit.toLowerCase());
									if (StringUtils.isNumeric(timeBetween)) {
										timeLength = Integer.parseInt(timeBetween);
										switch (timeUnit) {
											case "y":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(timeLength, TimeHandler.TimeType.Years));
												break;
											case "m":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(timeLength, TimeHandler.TimeType.Months));
												break;
											case "w":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(timeLength, TimeHandler.TimeType.Weeks));
												break;
											case "d":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(timeLength, TimeHandler.TimeType.Days));
												break;
											case "h":
												banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(timeLength, TimeHandler.TimeType.Hours));
												break;
											default:
												break;
										}
										break;
									} else {
										Players.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("time / reason"));
										return;
									}
								}
							}
							if (timeLength == 0 || timeUnit.equals("")) {
								Players.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("time / reason"));
								return;
							}
						}
					}

					if (banExpires == 0L) {
						banExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(10, TimeHandler.TimeType.Years));
						isPermanent = true;
					}

					if (Players.isOnline(playerName)) {
						Player bPlayer = Players.getPlayer(playerName);
						playerName = bPlayer.getName();
						bPlayer.kickPlayer(banReason);
						Commons.bansDatabase.insertPunishment(PunishmentType.BAN, playerName, banReason, bannedBy, banExpires);
						Players.messageAll(Messages.PLAYER_BANNED_GLOBAL_MESSAGE(playerName, bannedBy, banReason, isPermanent ? "Never" : TimeHandler.getDurationBreakdown(banExpires - System.currentTimeMillis())));
					} else {
						OfflinePlayer bPlayer = Bukkit.getOfflinePlayer(playerName);
						if (bPlayer.hasPlayedBefore()) {
							Commons.bansDatabase.insertPunishment(PunishmentType.BAN, playerName, banReason, bannedBy, banExpires);
							Players.messageAll(Messages.PLAYER_BANNED_GLOBAL_MESSAGE(playerName, bannedBy, banReason, isPermanent ? "Never" : TimeHandler.getDurationBreakdown(banExpires - System.currentTimeMillis())));
						} else {
							Players.sendMessage(sender, Messages.PLAYER_DATA_NOT_FOUND(playerName));
						}
					}
				} else {
					Players.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("ban reason"));
				}
			} else {
				Players.sendMessage(sender, "&e" + playerName + " &cis already banned");
			}
		}
	}
}
