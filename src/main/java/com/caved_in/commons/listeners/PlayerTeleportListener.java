package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.location.PreTeleportType;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

	private Players players;

	public PlayerTeleportListener() {
		players = Commons.getInstance().getPlayerHandler();
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		if (event.isCancelled()) {
			return;
		}

		Location fromLocation = event.getFrom();
		Player player = event.getPlayer();
		if (fromLocation != null && players.hasData(player.getUniqueId())) {
			MinecraftPlayer mcPlayer = players.getData(player);
			PreTeleportType preTeleportType = PreTeleportType.getByCause(event.getCause());
			mcPlayer.setPreTeleportLocation(event.getFrom(), preTeleportType);
		}
	}
}
