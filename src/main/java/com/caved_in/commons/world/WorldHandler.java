package com.caved_in.commons.world;

import com.caved_in.commons.Commons;
import com.caved_in.commons.location.LocationHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.UUID;

public class WorldHandler {
	public static void handleWorldWeather(World World) {
		if (World.hasStorm() && Commons.getConfiguration().getWorldConfig().isWeatherDisabled()) {
			World.setStorm(false);
			World.setThundering(false);
		}
	}

	public static World getWorld(String worldName) {
		return Bukkit.getWorld(worldName);
	}

	public static World getWorld(UUID worldUUID) {
		return Bukkit.getWorld(worldUUID);
	}

	public static boolean worldExists(String worldName) {
		return Bukkit.getWorld(worldName) != null;
	}

	public static Location getSpawn(UUID worldUUID) {
		return getSpawn(getWorld(worldUUID));
	}

	public static Location getSpawn(String worldName) {
		return getSpawn(getWorld(worldName));
	}

	public static Location getSpawn(World world) {
		return world.getSpawnLocation();
	}

	public static boolean setSpawn(World world, int x, int y, int z) {
		return world.setSpawnLocation(x, y, z);
	}

	public static boolean setSpawn(String world, int x, int y, int z) {
		return setSpawn(getWorld(world), x, y, z);
	}

	public static boolean setSpawn(World world, int[] XYZ) {
		return setSpawn(world, XYZ[0], XYZ[1], XYZ[2]);
	}

	public static boolean setSpawn(World world, Location location) {
		return setSpawn(world, LocationHandler.getXYZ(location));
	}

	public static boolean unloadWorld(String worldName) {
		return Bukkit.getServer().unloadWorld(getWorld(worldName), false);
	}

	public static boolean loadWorld(String worldName) {
		try {
			Bukkit.getServer().createWorld(new WorldCreator(worldName));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static void setTime(World world, long time) {
		world.setTime(time);
	}

	public static void setTime(World world, WorldTime time) {
		world.setTime(time.getTime());
	}

	public static void setTimeDawn(World world) {
		setTime(world, WorldTime.DAWN);
	}

	public static void setTimeNight(World world) {
		setTime(world, WorldTime.NIGHT);
	}

	public static void setTimeDay(World world) {
		setTime(world, WorldTime.DAY);
	}
}
