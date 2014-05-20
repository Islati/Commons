package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.warp.Warps;
import org.bukkit.entity.Player;

public class WarpCommand {
	@Command(name = "warp", permission = "commons.command.warp")
	public void onWarpCommand(Player player, String[] args) {
		if (args.length > 0) {
			String warpArg = args[0];
			//Check if the argument they entered is a warp
			if (Warps.isWarp(warpArg)) {
				Warp warp = Warps.getWarp(warpArg);
				Players.teleport(player, warp);
				Players.sendMessage(player, Messages.playerWarpedTo(warpArg));
			}
		} else {
			Players.sendMessage(player, Messages.invalidCommandUsage("warp name"));
		}
	}
}
