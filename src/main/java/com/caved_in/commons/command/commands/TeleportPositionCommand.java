package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class TeleportPositionCommand {
	@Command(name = "tppos", permission = "commons.command.tppos")
	public void onTeleportPositionCommand(Player player, String[] args) {
		if (args.length >= 3) {
			String xArg = args[0];
			String yArg = args[1];
			String zArg = args[2];
			//Verify that all the args are numbers
			if (StringUtils.isNumeric(xArg) && StringUtils.isNumeric(yArg) && StringUtils.isNumeric(zArg)) {
				Players.teleport(player, new double[]{Double.parseDouble(xArg), Double.parseDouble(yArg), Double.parseDouble(zArg)});
			} else {
				Players.sendMessage(player, Messages.invalidCommandUsage("x", "y", "z"));
			}
		} else {
			Players.sendMessage(player, Messages.invalidCommandUsage("x", "y", "z"));
		}
	}
}
