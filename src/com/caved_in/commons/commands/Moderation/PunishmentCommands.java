package com.caved_in.commons.commands.Moderation;

import com.caved_in.commons.Commons;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.config.Messages;
import com.caved_in.commons.handlers.Data.Bans.PunishmentType;
import com.caved_in.commons.handlers.Misc.TimeHandler;
import com.caved_in.commons.handlers.Misc.TimeHandler.TimeType;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import com.caved_in.commons.handlers.Utilities.CommonUtils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PunishmentCommands
{

	@CommandHandler(name = "ban", description = "Bans a player permanately, or temporarily across all servers", permission = "tunnels.common.ban", usage = "/ban [Name] [Reason] <Time>")
	public void BanCommand(CommandSender Sender, String[] Args)
	{
		if (Args.length > 0)
		{
			String Name = Args[0];
			if (Commons.bansDatabase.isBanned(Name) == false)
			{
				String Reason = "";
				String BannedBy = "";
				int TimeArg = 0;
				long BanExpires = 0L;
				boolean PermBan = false;
				if (Sender instanceof Player)
				{
					BannedBy = ((Player) Sender).getName();
				}
				else
				{
					BannedBy = "Console";
				}
				if (Args.length >= 1)
				{
					if (Args[1] != null)
					{
						for (int I = 1; I < Args.length; I++)
						{
							if (!Args[I].startsWith("t:"))
							{
								Reason += Args[I] + " ";
							}
							else
							{
								TimeArg = I;
								break;
							}
						}
					}
					else
					{
						Sender.sendMessage(ChatColor.GREEN + "[Tunnels Network] " + ChatColor.RED + "You need to give a reason when banning someone.");
						return;
					}

					if (TimeArg != 0)
					{
						String TimeOrReason = Args[TimeArg];
						if (TimeOrReason.toLowerCase().contains("t:"))
						{
							String TimeUnit = "";
							int TimeLength = 0;
							for (char Char : StringUtils.substringAfter(TimeOrReason.toLowerCase(), "t:").toCharArray())
							{
								if (!Character.isDigit(Char))
								{
									TimeUnit = String.valueOf(Char);
									String TimeBetween = StringUtils.substringBetween(TimeOrReason.toLowerCase(), "t:", TimeUnit.toLowerCase());
									if (StringUtils.isNumeric(TimeBetween))
									{
										TimeLength = Integer.parseInt(TimeBetween);
										switch (TimeUnit)
										{
										case "y":
											BanExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Years));
											break;
										case "m":
											BanExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Months));
											break;
										case "w":
											BanExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Weeks));
											break;
										case "d":
											BanExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Days));
											break;
										case "h":
											BanExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(TimeLength, TimeType.Hours));
											break;
										default:
											break;
										}
										break;
									}
									else
									{
										Sender.sendMessage(TimeOrReason + " is not a valid measurement of time...");
										return;
									}
								}
							}
							if (TimeLength == 0 || TimeUnit == "")
							{
								Sender.sendMessage("Error processing Command [--" + TimeOrReason + "--]");
								return;
							}
						}
					}

					if (BanExpires == 0L)
					{
						BanExpires = ((System.currentTimeMillis()) + TimeHandler.getTimeInMilles(10, TimeType.Years));
						PermBan = true;
					}

					if (Bukkit.getPlayer(Name) != null)
					{
						Player bPlayer = Bukkit.getPlayer(Name);
						Name = bPlayer.getName();
						bPlayer.kickPlayer(Reason);
						Commons.bansDatabase.InsertPunishment(PunishmentType.Ban, Name, Reason, BannedBy, BanExpires);
						PlayerHandler.sendMessageToAllPlayers(ChatColor.GREEN + Messages.messagePrefix + ChatColor.YELLOW + Name + ChatColor.GREEN + " was banned by " + ChatColor.YELLOW + BannedBy, ChatColor.RED + " - Reason: " + Reason, ChatColor.RED + " - Expires: " + (PermBan ? "Never" : (TimeHandler.getDurationBreakdown(BanExpires - System.currentTimeMillis()))));
					}
					else
					{
						OfflinePlayer bPlayer = Bukkit.getOfflinePlayer(Name);
						if (bPlayer.hasPlayedBefore())
						{
							Commons.bansDatabase.InsertPunishment(PunishmentType.Ban, Name, Reason, BannedBy, BanExpires);
							PlayerHandler.sendMessageToAllPlayers(ChatColor.GREEN + Messages.messagePrefix  + ChatColor.YELLOW + Name + ChatColor.GREEN + " was banned by " + ChatColor.YELLOW + BannedBy, ChatColor.RED + " - Reason: " + Reason, ChatColor.RED + " - Expires: " + (PermBan ? "Never" : (TimeHandler.getDurationBreakdown(BanExpires - System.currentTimeMillis()))));
						}
						else
						{
							Sender.sendMessage(ChatColor.GREEN + Messages.messagePrefix  + ChatColor.YELLOW + bPlayer.getName() + ChatColor.GREEN + " isn't a valid player; They've never played on this server.");
						}
					}
				}
				else
				{
					Sender.sendMessage("You need to provide a reason for banning");
				}
			}
		}
	}

	@CommandHandler(name = "unban", description = "Unban / pardon a user from their ban", permission = "tunnels.common.unban", usage = "/unban [Name]")
	public void UnbanCommand(CommandSender Sender, String[] Args)
	{
		if (Args.length > 0)
		{
			String Unban = Args[0];
			String Unbanner = "";
			if (Sender instanceof Player)
			{
				Unbanner = ((Player) Sender).getName();
			}
			else
			{
				Unbanner = "Console";
			}
			if (Commons.bansDatabase.Pardon(Unban, Unbanner))
			{
				PlayerHandler.sendMessageToAllPlayers("&a" + Messages.messagePrefix + "&e" + Unban + "&a was unbanned by &e" + Unbanner);
			}
			else
			{
				Sender.sendMessage(Unban + " has failed to be unbanned; They're either not banned, or an error was made");
			}
		}
		else
		{
			Sender.sendMessage("You need to include a name to pardon / unban");
		}
	}

	@CommandHandler(name = "pardon", description = "Unban / pardon a user from their ban", permission = "tunnels.common.unban", usage = "/pardon [Name]")
	public void PardonCommand(CommandSender Sender, String[] Args)
	{
		if (Args.length > 0)
		{
			String Unban = Args[0];
			String Unbanner = "";
			if (Sender instanceof Player)
			{
				Unbanner = ((Player) Sender).getName();
			}
			else
			{
				Unbanner = "Console";
			}
			if (Commons.bansDatabase.Pardon(Unban, Unbanner))
			{
				PlayerHandler.sendMessageToAllPlayers("&a" + Messages.messagePrefix + "&e" + Unban + "&a was unbanned by &e" + Unbanner);
				Sender.sendMessage(Unban + " has been successfully unbanned.");
			}
			else
			{
				Sender.sendMessage(Unban + " has failed to be unbanned; They're either not banned, or an error was made");
			}
		}
		else
		{
			Sender.sendMessage("You need to include a name to pardon / unban");
		}
	}

}
