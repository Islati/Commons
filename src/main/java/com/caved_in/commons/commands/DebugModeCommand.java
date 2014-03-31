package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class DebugModeCommand {

	@CommandController.CommandHandler(name = "debug", permission = "common.debugmode")
	public void onDebugModeCommand(Player player, String[] args) {
		PlayerWrapper playerWrapper = Players.getData(player);
		if (args.length > 0) {
			String mode = args[0];
			switch (mode) {
				case "on":
				case "true":
					playerWrapper.setInDebugMode(true);
					Players.sendMessage(player, Messages.playerDebugModeChange(playerWrapper));
					break;
				case "off":
				case "false":
					playerWrapper.setInDebugMode(false);
					break;
				default:
					Players.sendMessage(player, Messages.invalidCommandUsage("on/off"));
					break;
			}
		} else {
			playerWrapper.setInDebugMode(!playerWrapper.isInDebugMode());
		}
		Players.sendMessage(player, Messages.playerDebugModeChange(playerWrapper));
		Players.updateData(playerWrapper);
	}
}
