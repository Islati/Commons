package com.caved_in.commons.location;

import com.caved_in.commons.world.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class Locations {
	/**
	 * Get all the players within a radius
	 *
	 * @param location
	 * @param radius
	 * @return
	 */
	public static ArrayList<Player> getPlayersInRadius(Location location, double radius) {
		ArrayList<Player> playerInRadius = new ArrayList<Player>();
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();
		double radiusSquared = radius * radius;
		for (Player onlinePlayer : onlinePlayers) {
			if (onlinePlayer.getLocation().distanceSquared(location) <= radiusSquared) {
				playerInRadius.add(onlinePlayer);
			}
		}
		return playerInRadius;
	}

	/**
	 * Check if a player is within a radius of another location
	 *
	 * @param location
	 * @param radius
	 * @param player
	 * @return
	 */
	public static boolean isPlayerInRadius(Location location, double radius, Player player) {
		double radiusSquared = radius * radius;
		if (player.getLocation().distanceSquared(location) <= radiusSquared) {
			return true;
		}
		return false;
	}

	/**
	 * Get a random point with a radius of another location
	 *
	 * @param locationCenter
	 * @param radius
	 * @return
	 */
	public static Location getRandomLocation(Location locationCenter, int radius) {
		Random rand = new Random();
		double angle = rand.nextDouble() * 360; //Generate a random angle
		double x = locationCenter.getX() + (rand.nextDouble() * radius * Math.cos(Math.toRadians(angle)));
		double z = locationCenter.getZ() + (rand.nextDouble() * radius * Math.sin(Math.toRadians(angle)));
		double y = locationCenter.getWorld().getHighestBlockYAt((int) x, (int) z);
		return new Location(locationCenter.getWorld(), x, y, z);
	}

	/**
	 * Get the X, Y, and Z Coords of a location
	 * in a literal array, where index:
	 * <b>0 = X <br/>1 = Y<br/>2 = Z</b>
	 *
	 * @param location
	 * @return
	 */
	public static int[] getXYZ(Location location) {
		int x = (int) location.getX();
		int y = (int) location.getY();
		int z = (int) location.getZ();
		return new int[]{x, y, z};
	}

	public static Location getLocation(World world, double x, double y, double z) {
		return new Location(world, x, y, z);
	}

	public static Location getLocation(String worldName, double x, double y, double z) {
		return getLocation(Worlds.getWorld(worldName), x, y, z);
	}

	public static Location getLocation(String worldName, double[] xyz) {
		return getLocation(worldName, xyz[0], xyz[1], xyz[2]);
	}

	public static Location getLocation(World world, double[] xyz) {
		return new Location(world, xyz[0], xyz[1], xyz[2]);
	}

	public static Location getNormalizedLocation(Location location) {
		return getLocation(location.getWorld(), location.getX(), location.getY() + 1, location.getZ());
	}

	public static boolean isBehind(LivingEntity entityToCheck, LivingEntity entityBehind) {
		return Math.abs(Math.toDegrees(entityToCheck.getEyeLocation().getDirection().angle(entityBehind.getLocation().getDirection()))) < 45;
	}
}