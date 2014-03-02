package com.caved_in.commons.npc.utils;

import org.bukkit.World;

import com.caved_in.commons.npc.reflection.ReflectionUtil;

import java.lang.reflect.Method;

public class WorldUtil {

    private static final Method GET_HANDLE = ReflectionUtil.getMethod(ReflectionUtil.getCBClass("CraftWorld"), "getHandle");

    public static Object getHandle(World world) {
        return ReflectionUtil.invokeMethod(GET_HANDLE, world);
    }
}
