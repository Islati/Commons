package com.caved_in.commons.commands;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.warps.Warp;
import com.caved_in.commons.warps.WarpManager;
import org.bukkit.entity.Player;

import static com.caved_in.commons.Messages.*;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 10:45 PM
 */
public class SetWarpCommand {
	@CommandController.CommandHandler(name = "setwarp", permission = "tunnels.common.setwarp")
	public void onSetWarpCommand(Player player, String[] args) {
		if (args.length > 0) {
			String warpName = args[0];
			if (!WarpManager.isWarp(warpName)) {
				WarpManager.addWarp(new Warp(warpName, player.getLocation()), true);
				PlayerHandler.sendMessage(player, WARP_CREATED(warpName));
			} else {
				PlayerHandler.sendMessage(player, WARP_ALREADY_EXISTS(warpName));
			}
		} else {
			PlayerHandler.sendMessage(player, INVALID_COMMAND_USAGE("warp name"));
		}
	}
}
