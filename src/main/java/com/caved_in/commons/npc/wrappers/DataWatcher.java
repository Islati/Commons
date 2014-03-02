package com.caved_in.commons.npc.wrappers;

import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import com.caved_in.commons.Commons;
import com.caved_in.commons.npc.reflection.ReflectionUtil;
import com.caved_in.commons.npc.utils.EntityUtil;

public class DataWatcher extends BasicWrapper {

    private final Method RETURN_ALL_WATCHED = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("DataWatcher"), "c");
    private final Method UNWATCH_AND_RETURN_ALL_WATCHED = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("DataWatcher"), "b");
    public DataWatcher() {
        Entity fake = Bukkit.getWorlds().get(0).spawnEntity(new Location(Bukkit.getWorlds().get(0), 0, -5, 0), EntityType.CHICKEN);

        try {
            setHandle(ReflectionUtil.getNMSClass("DataWatcher").getDeclaredConstructor(new Class[]{ReflectionUtil.getNMSClass("Entity")}).newInstance(EntityUtil.getHandle(fake)));
        } catch (Exception e) {
            Commons.messageConsole("Failed to create new DataWatcher!");
            e.printStackTrace();
        }

    }

    public Object getAllWatched() {
        return ReflectionUtil.invokeMethod(RETURN_ALL_WATCHED, getHandle());
    }

    public Object unwatchAndReturnAllWatched() {
        return ReflectionUtil.invokeMethod(UNWATCH_AND_RETURN_ALL_WATCHED, getHandle());
    }

    public void write(int i, Object object){
        ReflectionUtil.invokeMethod(ReflectionUtil.getMethod(getHandle().getClass(), "a", int.class, Object.class), getHandle(), i, object);
    }
}
