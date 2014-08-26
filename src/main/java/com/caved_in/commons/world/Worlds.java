package com.caved_in.commons.world;

import com.caved_in.commons.Commons;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.threading.tasks.ClearDroppedItemsThread;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

public class Worlds {
	private static final Method GET_HANDLE = ReflectionUtilities.getMethod(ReflectionUtilities.getCBClass("CraftWorld"), "getHandle");

	public static void handleWeather(World World) {
		if (World.hasStorm() && Commons.getConfiguration().getWorldConfig().isWeatherDisabled()) {
			World.setStorm(false);
			World.setThundering(false);
		}
	}

	public static Object getHandle(World world) {
		return ReflectionUtilities.invokeMethod(GET_HANDLE, world);
	}

	public static World getWorld(String worldName) {
		return Bukkit.getWorld(worldName);
	}

	public static World getWorld(UUID worldUUID) {
		return Bukkit.getWorld(worldUUID);
	}

	public static World getWorld(Entity entity) {
		return entity.getWorld();
	}

	public static String getWorldName(Entity entity) {
		return getWorld(entity).getName();
	}

	public static String getWorldName(Location location) {
		return location.getWorld().getName();
	}

	public static boolean exists(String worldName) {
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

	public static Location getSpawn(Entity entity) {
		return getSpawn(getWorld(entity));
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
		return setSpawn(world, Locations.getXYZ(location));
	}

	public static boolean unload(String worldName) {
		return Bukkit.getServer().unloadWorld(getWorld(worldName), false);
	}

	public static boolean unload(World world) {
		return Bukkit.getServer().unloadWorld(world, false);
	}

	public static boolean load(String worldName) {
		try {
			Bukkit.getServer().createWorld(new WorldCreator(worldName));
//			if (exists(worldName)) {
//				World world = getWorld(worldName);
//				Location spawnLocation = world.getSpawnLocation();
//				world.loadChunk(spawnLocation.getBlockX(),spawnLocation.getBlockZ(),true);
//			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return exists(worldName);
	}

	public static List<World> allWorlds() {
		return Bukkit.getWorlds();
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

	public static void dropItem(World world, Location location, ItemStack item, boolean natural) {
		if (natural) {
			world.dropItemNaturally(location, item);
		} else {
			world.dropItem(location, item);
		}
	}

	public static void dropItem(Entity entity, ItemStack item, boolean natural) {
		dropItem(entity.getWorld(), entity.getLocation(), item, natural);
	}

	public static void dropItem(Location location, ItemStack itemStack) {
		dropItem(location.getWorld(), location, itemStack, true);
	}

	public static void dropItemNaturally(Entity entity, ItemStack item) {
		dropItem(entity, item, true);
	}

	public static World getDefaultWorld() {
		return Bukkit.getWorlds().get(0);
	}

	public static void rollback(World world) {
		boolean save = world.isAutoSave();
		String worldName = world.getName();
		world.setAutoSave(false);
		unload(world);
		load(worldName);
		world.setAutoSave(save);
	}

	public static void clearDroppedItems(Location loc, int radius) {
		Commons.getInstance().getThreadManager().runTaskNow(new ClearDroppedItemsThread(loc, radius));
	}

	public static void clearDroppedItems(Location loc, int radius, int delay, TimeType duration) {
		Commons.getInstance().getThreadManager().runTaskLater(new ClearDroppedItemsThread(loc, radius), TimeHandler.getTimeInTicks(delay, duration));
	}
}
