package com.caved_in.commons.commands;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.player.PlayerWrapper;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 11:30 PM
 */
public class BackCommand {
	@CommandController.CommandHandler(name = "back", permission = "tunnels.common.back")
	public void onBackCommand(Player player, String[] args) {
		PlayerWrapper playerWrapper = PlayerHandler.getData(player);
		PlayerHandler.teleport(player, playerWrapper.getPreTeleportLocation());
	}
}
