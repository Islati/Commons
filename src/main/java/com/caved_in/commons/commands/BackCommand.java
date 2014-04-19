package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 11:30 PM
 */
public class BackCommand {
	@CommandController.CommandHandler(name = "back", permission = "commons.commands.back")
	public void onBackCommand(Player player, String[] args) {
		PlayerWrapper playerWrapper = Players.getData(player);
		Location location = playerWrapper.getPreTeleportLocation();
		if (location == null) {
			Players.sendMessage(player, Messages.NO_TELEPORT_BACK_LOCATION);
			return;
		}
		Players.teleport(player, playerWrapper.getPreTeleportLocation());
	}
}
