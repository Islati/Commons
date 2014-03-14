package com.caved_in.commons.command;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.warp.Warps;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 10:24 PM
 */
public class WarpCommand {
	@CommandController.CommandHandler(name = "warp", permission = "tunnels.common.warp")
	public void onWarpCommand(Player player, String[] args) {
		if (args.length > 0) {
			String warpArg = args[0];
			//Check if the argument they entered is a warp
			if (Warps.isWarp(warpArg)) {
				Warp warp = Warps.getWarp(warpArg);
				Players.teleport(player, warp);
				Players.sendMessage(player, Messages.WARPED_TO(warpArg));
			}
		} else {
			Players.sendMessage(player, Messages.INVALID_COMMAND_USAGE("warp name"));
		}
	}
}
