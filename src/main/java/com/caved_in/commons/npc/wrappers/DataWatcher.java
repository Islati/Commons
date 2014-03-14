package com.caved_in.commons.npc.wrappers;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.lang.reflect.Method;

public class DataWatcher extends BasicWrapper {

	private final Method RETURN_ALL_WATCHED = ReflectionUtilities.getMethod(ReflectionUtilities.getNMSClass("DataWatcher"), "c");
	private final Method UNWATCH_AND_RETURN_ALL_WATCHED = ReflectionUtilities.getMethod(ReflectionUtilities.getNMSClass("DataWatcher"), "b");

	public DataWatcher() {
		Entity fake = Bukkit.getWorlds().get(0).spawnEntity(new Location(Bukkit.getWorlds().get(0), 0, -5, 0), EntityType.CHICKEN);
		try {
			setHandle(ReflectionUtilities.getNMSClass("DataWatcher").getDeclaredConstructor(new Class[]{ReflectionUtilities.getNMSClass("Entity")}).newInstance(Entities.getHandle(fake)));
		} catch (Exception e) {
			Commons.messageConsole(Messages.FAILED_TO_CREATE_DATAWATCHER);
			e.printStackTrace();
		}

	}

	public Object getAllWatched() {
		return ReflectionUtilities.invokeMethod(RETURN_ALL_WATCHED, getHandle());
	}

	public Object unwatchAndReturnAllWatched() {
		return ReflectionUtilities.invokeMethod(UNWATCH_AND_RETURN_ALL_WATCHED, getHandle());
	}

	public void write(int i, Object object) {
		ReflectionUtilities.invokeMethod(ReflectionUtilities.getMethod(getHandle().getClass(), "a", int.class, Object.class), getHandle(), i, object);
	}
}
