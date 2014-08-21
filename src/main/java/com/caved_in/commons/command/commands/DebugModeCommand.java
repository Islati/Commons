package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DebugModeCommand {

	@Command(name = "debug", permission = "commons.debugmode")
	public void onDebugModeCommand(Player player, String[] args) {
		MinecraftPlayer minecraftPlayer = Players.getData(player);
		if (args.length == 0) {
			minecraftPlayer.setInDebugMode(!minecraftPlayer.isInDebugMode());
			Players.sendMessage(player, Messages.playerDebugModeChange(minecraftPlayer));
			Players.updateData(minecraftPlayer);
			return;
		}

		String action = args[0];
		if (Debugger.isDebugAction(action)) {
			String[] debugArgs = new String[0];
			if (args.length > 1) {
				debugArgs = Arrays.copyOfRange(args, 1, args.length);
			}
			Debugger.getDebugAction(action).doAction(player, debugArgs);
			return;
		}

		switch (action) {
			case "on":
			case "true":
				minecraftPlayer.setInDebugMode(true);
				Players.sendMessage(player, Messages.playerDebugModeChange(minecraftPlayer));
				break;
			case "off":
			case "false":
				minecraftPlayer.setInDebugMode(false);
				Players.sendMessage(player, Messages.playerDebugModeChange(minecraftPlayer));
				break;
			case "list":
			case "?":
				int page = 1;
				if (args.length > 1) {
					if (!StringUtils.isNumeric(args[1])) {
						Players.sendMessage(player, Messages.invalidCommandUsage("list", "[Page Number]"));
						return;
					}
					page = Integer.parseInt(args[1]);
				}
				Debugger.getDebugMenu().sendTo(player, page, 6);
				break;
			default:
				break;
		}

	}
}
