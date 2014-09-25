package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.location.PreTeleportLocation;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BackCommand {
	@Command(name = "back", permission = "commons.command.back")
	public void onBackCommand(Player player, String[] args) {
		MinecraftPlayer minecraftPlayer = Players.getData(player);
		Location location = minecraftPlayer.getPreTeleportLocation();

		if (location == null) {
			Players.sendMessage(player, Messages.NO_TELEPORT_BACK_LOCATION);
			return;
		}

		PreTeleportLocation preTeleLoc = minecraftPlayer.getPreTeleportLocation();

		if (!preTeleLoc.hasPermission(player)) {
			Players.sendMessage(player, Messages.insufficientPreTeleportPermissions(preTeleLoc));
			return;
		}

		Players.teleport(player, preTeleLoc);
	}
}
