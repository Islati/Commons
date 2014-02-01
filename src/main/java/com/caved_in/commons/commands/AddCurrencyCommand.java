package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:24 PM
 */
public class AddCurrencyCommand {
	@CommandController.CommandHandler(name = "addcurrency", usage = "/addcurrency <Player> <Amount>", permission = "tunnels.common.currency")
	public void addCurrencyCommand(CommandSender sender, String[] commandArgs) {
		if (commandArgs.length > 1) {
			String playerName = commandArgs[0];
			String currencyAmount = commandArgs[1];
			if (Commons.playerDatabase.hasData(playerName)) {
				if (StringUtils.isNumeric(currencyAmount)) {
					int currency = Integer.parseInt(currencyAmount);
					if (PlayerHandler.isOnline(playerName)) {
						PlayerWrapper playerWrapper = PlayerHandler.getData(playerName);
						playerWrapper.addCurrency((double) currency);
						PlayerHandler.updateData(playerWrapper);
						PlayerHandler.sendMessage(sender, Messages.ADDED_XP(playerName, currency));
					} else {
						PlayerWrapper playerWrapper = Commons.playerDatabase.getPlayerWrapper(playerName);
						if (playerWrapper != null) {
							playerWrapper.addCurrency((double) currency);
							PlayerHandler.updateData(playerWrapper);
							Commons.messageConsole("Added " + currency + " to " + playerName);
						} else {
							PlayerHandler.sendMessage(sender, Messages.PLAYER_OFFLINE(playerName));
						}
					}
				} else {
					PlayerHandler.sendMessage(sender, "&c" + currencyAmount + "&e isn't a number.. &o/addcurrency <Player> <Amount>");
				}
			} else {
				PlayerHandler.sendMessage(sender, Messages.PLAYER_DATA_NOT_FOUND(playerName));
			}
		} else {
			PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("Player", "Amount"));
		}
	}
}
