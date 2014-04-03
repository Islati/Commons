package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:24 PM
 */
public class AddCurrencyCommand {
	@CommandController.CommandHandler(name = "addcurrency", usage = "/addcurrency <Player> <Amount>", permission = "common.currency.add")
	public void addCurrencyCommand(CommandSender sender, String[] commandArgs) {
		if (commandArgs.length > 1) {
			String playerName = commandArgs[0];
			String currencyAmount = commandArgs[1];
			if (Commons.playerDatabase.hasData(playerName)) {
				if (StringUtils.isNumeric(currencyAmount)) {
					int currency = Integer.parseInt(currencyAmount);
					if (Players.isOnline(playerName)) {
						PlayerWrapper playerWrapper = Players.getData(playerName);
						playerWrapper.addCurrency((double) currency);
						Players.updateData(playerWrapper);
						Players.sendMessage(sender, Messages.playerAddedXp(playerName, currency));
					} else {
						PlayerWrapper playerWrapper = Commons.playerDatabase.getPlayerWrapper(playerName);
						if (playerWrapper != null) {
							playerWrapper.addCurrency((double) currency);
							Players.updateData(playerWrapper);
							Commons.messageConsole("Added " + currency + " to " + playerName);
						} else {
							Players.sendMessage(sender, Messages.playerOffline(playerName));
						}
					}
				} else {
					Players.sendMessage(sender, "&c" + currencyAmount + "&e isn't a number.. &o/addcurrency <Player> <Amount>");
				}
			} else {
				Players.sendMessage(sender, Messages.invalidPlayerData(playerName));
			}
		} else {
			Players.sendMessage(sender, Messages.invalidCommandUsage("Player", "Amount"));
		}
	}
}
