package com.caved_in.commons.npc.utils;

import org.bukkit.entity.Entity;

import com.caved_in.commons.npc.reflection.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class EntityUtil {

    public static Object getHandle(Entity entity) {
        return ReflectionUtil.invokeMethod(ReflectionUtil.getMethod(entity.getClass(), "getHandle"), entity);
    }

    /**
     * Utils for the projectiles etc...
     */
    public static final Class<?> ENTITY_SNOWBALL = ReflectionUtil.getNMSClass("EntitySnowBall");
    public static final Class<?> ENTITY_EGG = ReflectionUtil.getNMSClass("EntityEgg");
    public static final Class<?> ENTITY_ENDERPEARL = ReflectionUtil.getNMSClass("EntityEnderPearl");
    public static final Class<?> ENTITY_ARROW = ReflectionUtil.getNMSClass("EntityArrow");
    public static final Class<?> ENTITY_POTION = ReflectionUtil.getNMSClass("EntityPotion");
    public static final Class<?> FIREBALL = ReflectionUtil.getNMSClass("Fireball");
    public static final Class<?> ENTITY_SMALL_FIREBALL = ReflectionUtil.getNMSClass("EntitySmallFireball");
    public static final Class<?> ENTITY_LARGE_FIREBALL = ReflectionUtil.getNMSClass("EntityLargeFireball");
    public static final Class<?> ENTITY_WITHERSKULL = ReflectionUtil.getNMSClass("EntityWitherSkull");

    private static final Method SET__POSITION_ROTATION = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("Entity"), "setPositionRotation", double.class, double.class, double.class, float.class, float.class);

    private static final Class<?> WORLD = ReflectionUtil.getNMSClass("World");
    private static final Method ADD_ENTITY = ReflectionUtil.getMethod(WORLD, "addEntity", ReflectionUtil.getNMSClass("Entity"));

    private static final Method GET_BUKKIT_ENTITY = ReflectionUtil.getMethod(ReflectionUtil.getNMSClass("Entity"), "getBukkitEntity");

    public static void setPositionRotation(Object entityHandle, double x, double y, double z, float yaw, float pitch) {
        ReflectionUtil.invokeMethod(SET__POSITION_ROTATION, x, y, z, yaw, pitch);
    }

    public static void addEntity(Object worldhandle, Object entityHandle) {
        ReflectionUtil.invokeMethod(ADD_ENTITY, worldhandle, entityHandle);
    }

    public static Object invokeProjectile(Class<?> clazz, Object world) {
        if(!clazz.isAssignableFrom(ReflectionUtil.getNMSClass("EntityProjectile")) || world.getClass().isAssignableFrom(ReflectionUtil.getNMSClass("World"))) {
            return null;
        }
        Constructor constructor = ReflectionUtil.getConstructor(clazz, ReflectionUtil.getNMSClass("World"));
        return ReflectionUtil.invokeConstructor(constructor, world);
    }

    public static <T> T getBukkitEntity(Object entityHandle) {
        if(!entityHandle.getClass().isAssignableFrom(ReflectionUtil.getNMSClass("Entity"))) {
            return null;
        }

        return ReflectionUtil.invokeMethod(GET_BUKKIT_ENTITY, entityHandle);
    }
}
