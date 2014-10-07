package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.warp.Warps;
import org.bukkit.entity.Player;

public class WarpCommand {
	@Command(identifier = "warp", permissions = "commons.command.warp")
	public void onWarpCommand(Player player, @Wildcard @Arg(name = "warp") String warpName) {
		if (!Warps.isWarp(warpName)) {
			Players.sendMessage(player, Messages.invalidWarp(warpName));
			return;
		}

		Warp warp = Warps.getWarp(warpName);
		Players.teleport(player, warp);
		Players.sendMessage(player, Messages.playerWarpedTo(warpName));
	}
}
