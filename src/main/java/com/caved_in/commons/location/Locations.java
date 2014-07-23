package com.caved_in.commons.location;

import com.caved_in.commons.world.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.*;

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

	public static boolean isEntityInRadius(Location center, double radius, Entity entity) {
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

	public static Location getRoundedCompassLocation(Location location, int round) {
		int x = location.getBlockX();
		int z = location.getBlockZ();
		z = Math.round(z / round) * round;
		x = Math.round(x / round) * round;
		return new Location(location.getWorld(), x, 0.0, z);
	}

	public static List<Location> getParticlesCircle(Location center, float radius, float distanceBetweenParticles) {
		List<Location> locs = new ArrayList<Location>();
		for (float i = 0F; i < 360F; ) {
			locs.add(new Location(center.getWorld(), center.getX() + Math.cos((double) i) / radius, center.getY(), center.getZ() + Math.sin((double) i) / radius));
			i = i + distanceBetweenParticles;
		}
		return locs;
	}

	public static List<Location> getSpiral(Location center, Float degrees, double centerRadius, float radius, float distanceBetweenParticles) {
		List<Location> locs = new ArrayList<Location>();
		for (float i = 0F; i < degrees; ) {
			locs.add(new Location(center.getWorld(), center.getX() + Math.sin((double) i) / radius, center.getY() + i / centerRadius, center.getZ() + Math.cos((double) i) / radius));
			i = i + distanceBetweenParticles;
		}
		return locs;
	}

	public static List<Location> getCircle(Location centerLoc, int radius) {
		List<Location> circle = new ArrayList<>();
		World world = centerLoc.getWorld();
		int x = 0;
		int z = radius;
		int error = 0;
		int d = 2 - 2 * radius;
		while (z >= 0) {
			circle.add(new Location(world, centerLoc.getBlockX() + x, centerLoc.getY(), centerLoc.getBlockZ() + z));
			circle.add(new Location(world, centerLoc.getBlockX() - x, centerLoc.getY(), centerLoc.getBlockZ() + z));
			circle.add(new Location(world, centerLoc.getBlockX() - x, centerLoc.getY(), centerLoc.getBlockZ() - z));
			circle.add(new Location(world, centerLoc.getBlockX() + x, centerLoc.getY(), centerLoc.getBlockZ() - z));
			error = 2 * (d + z) - 1;
			if ((d < 0) && (error <= 0)) {
				x++;
				d += 2 * x + 1;
			} else {
				error = 2 * (d - x) - 1;
				if ((d > 0) && (error > 0)) {
					z--;
					d += 1 - 2 * z;
				} else {
					x++;
					d += 2 * (x - z);
					z--;
				}
			}
		}
		return circle;
	}

	public static List<Location> getPlain(Location position1, Location position2) {
		List<Location> plain = new ArrayList<Location>();
		if (position1 == null) {
			return plain;
		}
		if (position2 == null) {
			return plain;
		}
		for (int x = Math.min(position1.getBlockX(), position2.getBlockX()); x <= Math.max(position1.getBlockX(), position2.getBlockX()); x++) {
			for (int z = Math.min(position1.getBlockZ(), position2.getBlockZ()); z <= Math.max(position1.getBlockZ(), position2.getBlockZ()); z++) {
				plain.add(new Location(position1.getWorld(), x, position1.getBlockY(), z));
			}
		}
		return plain;
	}

	public static List<Location> getLine(Location position1, Location position2) {
		List<Location> line = new ArrayList<>();
		int dx = Math.max(position1.getBlockX(), position2.getBlockX()) - Math.min(position1.getBlockX(), position2.getBlockX());
		int dy = Math.max(position1.getBlockY(), position2.getBlockY()) - Math.min(position1.getBlockY(), position2.getBlockY());
		int dz = Math.max(position1.getBlockZ(), position2.getBlockZ()) - Math.min(position1.getBlockZ(), position2.getBlockZ());
		int x1 = position1.getBlockX();
		int x2 = position2.getBlockX();
		int y1 = position1.getBlockY();
		int y2 = position2.getBlockY();
		int z1 = position1.getBlockZ();
		int z2 = position2.getBlockZ();
		int x = 0;
		int y = 0;
		int z = 0;
		int i = 0;
		int d = 1;
		switch (getHighest(dx, dy, dz)) {
			case 1:
				i = 0;
				d = 1;
				if (x1 > x2) {
					d = -1;
				}
				x = position1.getBlockX();
				do {
					i++;
					y = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
					z = z1 + (x - x1) * (z2 - z1) / (x2 - x1);
					line.add(new Location(position1.getWorld(), x, y, z));
					x += d;
				} while (i <= Math.max(x1, x2) - Math.min(x1, x2));
				break;
			case 2:
				i = 0;
				d = 1;
				if (y1 > y2) {
					d = -1;
				}
				y = position1.getBlockY();
				do {
					i++;
					x = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
					z = z1 + (y - y1) * (z2 - z1) / (y2 - y1);
					line.add(new Location(position1.getWorld(), x, y, z));
					y += d;
				} while (i <= Math.max(y1, y2) - Math.min(y1, y2));
				break;
			case 3:
				i = 0;
				d = 1;
				if (z1 > z2) {
					d = -1;
				}
				z = position1.getBlockZ();
				do {
					i++;
					y = y1 + (z - z1) * (y2 - y1) / (z2 - z1);
					x = x1 + (z - z1) * (x2 - x1) / (z2 - z1);
					line.add(new Location(position1.getWorld(), x, y, z));
					z += d;
				} while (i <= Math.max(z1, z2) - Math.min(z1, z2));
		}
		return line;
	}

	private static int getHighest(int x, int y, int z) {
		if ((x >= y) && (x >= z)) {
			return 1;
		}
		if ((y >= x) && (y >= z)) {
			return 2;
		}
		return 3;
	}

	public static String getWorldName(Location location) {
		return location.getWorld().getName();
	}

	public static boolean isBehind(LivingEntity entityToCheck, LivingEntity entityBehind) {
		return Math.abs(Math.toDegrees(entityToCheck.getEyeLocation().getDirection().angle(entityBehind.getLocation().getDirection()))) < 45;
	}
}