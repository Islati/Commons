package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class LauncherListener implements Listener
{

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent Event)
	{
		Player player = Event.getPlayer();
		Location playerLocation = player.getLocation();
		Material playerBlock = playerLocation.getWorld().getBlockAt(playerLocation).getType();
		if (playerBlock == Material.STONE_PLATE || playerBlock == Material.WOOD_PLATE || playerBlock == Material.GOLD_PLATE || playerBlock == Material.IRON_PLATE)
		{
			player.setVelocity(playerLocation.getDirection().multiply(3));
			player.setVelocity(new Vector(player.getVelocity().getX(), 1.0D, player.getVelocity().getZ()));
		}
	}
}
