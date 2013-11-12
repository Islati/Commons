package com.caved_in.commons.handlers.Location;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationHandler
{
	/**
	 * Get all the players within a radius
	 * @param location
	 * @param radius
	 * @return
	 */
	public static ArrayList<Player> getPlayersInRadius(Location location, double radius)
	{
		ArrayList<Player> Players = new ArrayList<Player>();
		Player[] OnlinePlayers = Bukkit.getOnlinePlayers();
		double radiusSquared = radius * radius;
		for (Player onlinePlayer : OnlinePlayers)
		{
			if (onlinePlayer.getLocation().distanceSquared(location) <= radiusSquared)
			{
				Players.add(onlinePlayer);
			}
		}
		return Players;
	}

	/**
	 * Check if a player is within a radius of another location
	 * @param location
	 * @param radius
	 * @param player
	 * @return
	 */
	public static boolean isPlayerInRadius(Location location, double radius, Player player)
	{
		double radiusSquared = radius * radius;
		if (player.getLocation().distanceSquared(location) <= radiusSquared)
		{
			return true;
		}
		return false;
	}

	/**
	 * Get a random point with a radius of another location
	 * @param locationCenter
	 * @param radius
	 * @return
	 */
	public static Location getRandomLocation(Location locationCenter, int radius)
	{
		Random rand = new Random();
		double angle = rand.nextDouble()*360; //Generate a random angle
		double x = locationCenter.getX() + (rand.nextDouble()*radius*Math.cos(Math.toRadians(angle)));
		double z = locationCenter.getZ() + (rand.nextDouble()*radius*Math.sin(Math.toRadians(angle)));
		double y = locationCenter.getWorld().getHighestBlockYAt((int)x,(int)z);
		return new Location(locationCenter.getWorld(), x, y, z);
	}
}