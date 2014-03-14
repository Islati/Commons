package com.caved_in.commons.command;

import com.caved_in.commons.player.Players;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.warp.Warps;
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
			if (!Warps.isWarp(warpName)) {
				Warps.addWarp(new Warp(warpName, player.getLocation()), true);
				Players.sendMessage(player, WARP_CREATED(warpName));
			} else {
				Players.sendMessage(player, WARP_ALREADY_EXISTS(warpName));
			}
		} else {
			Players.sendMessage(player, INVALID_COMMAND_USAGE("warp name"));
		}
	}
}
