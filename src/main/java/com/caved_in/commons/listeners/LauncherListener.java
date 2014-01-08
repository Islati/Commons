package com.caved_in.commons.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class LauncherListener implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location playerLocation = player.getLocation();
		Material playerBlock = playerLocation.getWorld().getBlockAt(playerLocation).getType();
		switch (playerBlock) {
			case STONE_PLATE:
			case WOOD_PLATE:
			case GOLD_PLATE:
			case IRON_PLATE:
				player.setVelocity(playerLocation.getDirection().multiply(3));
				player.setVelocity(new Vector(player.getVelocity().getX(), 1.0D, player.getVelocity().getZ()));
				break;
			default:
				break;
		}

	}
}
