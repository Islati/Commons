package com.caved_in.commons.location;

import com.caved_in.commons.world.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Locations {

	public static Set<Player> getPlayersInRadius(Location location, double radius) {
		Set<Player> playerInRadius = new HashSet<>();
		Player[] onlinePlayers = Bukkit.getOnlinePlayers();
		double radiusSquared = radius * radius;
		for (Player onlinePlayer : onlinePlayers) {
			if (onlinePlayer.getLocation().distanceSquared(location) <= radiusSquared) {
				playerInRadius.add(onlinePlayer);
			}
		}
		return playerInRadius;
	}

	public static boolean isPlayerInRadius(Location location, double radius, Player player) {
		return isEntityInRadius(location, radius, player);
	}

	public static boolean isEntityInRadius(Location center, double radius, LivingEntity entity) {
		return center.distanceSquared(entity.getLocation()) <= (radius * radius);
	}

	public static Location getRandomLocation(Location locationCenter, int radius) {
		Random rand = new Random();
		double angle = rand.nextDouble() * 360; //Generate a random angle
		double x = locationCenter.getX() + (rand.nextDouble() * radius * Math.cos(Math.toRadians(angle)));
		double z = locationCenter.getZ() + (rand.nextDouble() * radius * Math.sin(Math.toRadians(angle)));
		double y = locationCenter.getWorld().getHighestBlockYAt((int) x, (int) z);
		return new Location(locationCenter.getWorld(), x, y, z);
	}

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

	public static String getWorldName(Location location) {
		return location.getWorld().getName();
	}

	public static boolean isBehind(LivingEntity entityToCheck, LivingEntity entityBehind) {
		return Math.abs(Math.toDegrees(entityToCheck.getEyeLocation().getDirection().angle(entityBehind.getLocation().getDirection()))) < 45;
	}
}