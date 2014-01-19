package com.caved_in.commons.commands.admin;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController.CommandHandler;
import com.caved_in.commons.config.TunnelsPermissions;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminCommands {
	@CommandHandler(name = "buyinfo", usage = "/buyinfo <player>", permission = "tunnels.common.buyinfo")
	public void playerBuyInfoCommand(Player player, String[] commandArgs) {
		if (commandArgs.length > 0) {
			if (commandArgs[0] != null) {
				String playerName = commandArgs[0];
				player.chat("/buycraft payments " + playerName);
			} else {
				player.sendMessage("Please include a name: /buyinfo <name>");
			}
		}
	}

	@CommandHandler(name = "addcurrency", usage = "/addcurrency <Player> <Amount>", permission = "tunnels.common.currency")
	public void addCurrencyCommand(CommandSender sender, String[] commandArgs) {
		if (commandArgs.length > 1) {
			String playerName = commandArgs[0];
			String currencyAmount = commandArgs[1];
			if (Commons.playerDatabase.hasData(playerName)) {
				if (StringUtils.isNumeric(currencyAmount)) {
					int currency = Integer.parseInt(currencyAmount);
					if (PlayerHandler.isOnline(playerName)) {
						PlayerWrapper playerWrapper = PlayerHandler.getData(playerName);
						playerWrapper.addCurrency((double)currency);
						PlayerHandler.updateData(playerWrapper);
						PlayerHandler.sendMessage(sender, Messages.ADDED_XP(playerName, currency));
					} else {
						PlayerWrapper playerWrapper = Commons.playerDatabase.getPlayerWrapper(playerName);
						if (playerWrapper != null) {
							playerWrapper.addCurrency((double)currency);
							PlayerHandler.updateData(playerWrapper);
							Commons.messageConsole("Added " + currency + " to " + playerName);
						} else {
							PlayerHandler.sendMessage(sender, Messages.PLAYER_OFFLINE(playerName));
						}
					}
				} else {
					PlayerHandler.sendMessage(sender,"&c" + currencyAmount + "&e isn't a number.. &o/addcurrency <Player> <Amount>");
				}
			} else {
				PlayerHandler.sendMessage(sender,"&eUnable to find data for " + playerName + "; Names are Case Sensitive, try again?");
			}
		} else {
			PlayerHandler.sendMessage(sender,Messages.INVALID_COMMAND_USAGE("Player", "Amount"));
		}
	}

	@CommandHandler(name = "buypremium", description = "Used by the console to give users premium for a set amount of time", permission = "tunnels.common.buypremium")
	public void buyPlayerPremium(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String playerName = args[0];
			if (!Commons.playerDatabase.updatePlayerPremium(playerName, true)) {
				sender.sendMessage(StringUtil.formatColorCodes("&eThe user &c" + playerName + " &edoesn't exist in the player database, please assure you've spelt the name properly."));
			} else {
				sender.sendMessage(StringUtil.formatColorCodes("&aSuccessfully promoted &e" + playerName + " &ato premium status!"));
			}
		} else {
			PlayerHandler.sendMessage(sender,Messages.INVALID_COMMAND_USAGE("PlayerName"));
		}
	}

	@CommandHandler(name = "removepremium", description = "Used by the console to remove premium from players", permission = "tunnels.common.removepremium")
	public void removePlayerPremium(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String playerName = args[0];
			if (!Commons.playerDatabase.updatePlayerPremium(playerName, false)) {
				sender.sendMessage(StringUtil.formatColorCodes("&eThe user &c" + playerName + " &edoesn't exist in the player database, please assure you've spelt the name properly."));
			} else {
				sender.sendMessage(StringUtil.formatColorCodes("&aSuccessfully removed premium from &e" + playerName));
			}
		} else {
			PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("PlayerName"));
		}
	}

	@CommandHandler(name = "silence", permission = "tunnels.common.silence")
	public void silenceLobbyCommand(CommandSender sender, String[] commandArgs) {
		Commons.getConfiguration().getWorldConfig().setChatSilenced(true);
		sender.sendMessage(ChatColor.YELLOW + "The chat has been silenced!");
		PlayerHandler.sendMessageToAllPlayers(ChatColor.RED + "Chat has been silenced, you may only speak if you are the required permissions");
	}

	@CommandHandler(name = "unsilence", permission = "tunnels.common.silence")
	public void unsilenceLobbyCommand(CommandSender sender, String[] commandArgs) {
		Commons.getConfiguration().getWorldConfig().setChatSilenced(false);
		sender.sendMessage(ChatColor.YELLOW + "The chat has been unsilenced.");
		PlayerHandler.sendMessageToAllPlayers(ChatColor.RED + "Chat has been unsilenced, you may now speak");
	}

	@CommandHandler(name = "maintenance", usage = "maintainance [on/off/toggle]", permission = "tunnels.common.maintenance")
	public void maintainanceToggleCommand(CommandSender commandSender, String[] commandArgs) {
		if (commandArgs.length > 0) {
			if (commandArgs[0] != null && !commandArgs[0].isEmpty()) {
				String maintainanceHandle = commandArgs[0];
				switch (maintainanceHandle.toLowerCase()) {
					case "on":
						Commons.getConfiguration().getMaintenanceConfig().setMaintenanceMode(true);
						PlayerHandler.kickAllPlayersWithoutPermission(TunnelsPermissions.MAINTAINANCE_WHITELIST, Commons.getConfiguration().getMaintenanceConfig().getKickMessage());
						commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenance mode is now &eenabled&a, to disable it do &e/maintenance off&a or &e/Maintenance toggle"));
						break;
					case "off":
						Commons.getConfiguration().getMaintenanceConfig().setMaintenanceMode(false);
						commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenancemode is now &edisabled&a, to enable it do &e/maintenance on&a or &e/Maintenance toggle"));
						break;
					case "toggle":
						Commons.getConfiguration().getMaintenanceConfig().toggleMaintenance();
						if (Commons.getConfiguration().getMaintenanceConfig().isMaintenanceMode()) {
							PlayerHandler.kickAllPlayersWithoutPermission(TunnelsPermissions.MAINTAINANCE_WHITELIST, Commons.getConfiguration().getMaintenanceConfig().getKickMessage());
							commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenance mode is now &eenabled&a, to disable it do &e/Maintenance off&a or &e/Maintenance toggle"));
						} else {
							commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenance mode is now &edisabled&a, to enable it do &e/Maintenance on&a or &e/Maintenance toggle"));
						}
						break;
					default:
						commandSender.sendMessage(StringUtil.formatColorCodes("&eProper Usage: &a/Maintenance [on/off/toggle]"));
						break;
				}
			}
		} else {
			commandSender.sendMessage(StringUtil.formatColorCodes("&aMaintenance mode is currently " + (Commons.getConfiguration().getMaintenanceConfig().isMaintenanceMode() ? "&eEnabled " : "&eDisabled ") + " to change this, do &e/Maintenance toggle"));
		}
	}
}
