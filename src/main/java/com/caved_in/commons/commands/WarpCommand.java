package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.warps.Warp;
import com.caved_in.commons.warps.WarpManager;
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
			if (WarpManager.isWarp(warpArg)) {
				Warp warp = WarpManager.getWarp(warpArg);
				PlayerHandler.teleport(player, warp);
				PlayerHandler.sendMessage(player, Messages.WARPED_TO(warpArg));
			}
		} else {
			PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("warp name"));
		}
	}
}
