package com.caved_in.commons.npc.utils;

import org.bukkit.inventory.ItemStack;

import com.caved_in.commons.npc.reflection.ReflectionUtil;

import java.lang.reflect.Method;

public class ItemUtil {

    private static final Method TO_NMS = ReflectionUtil.getMethod(ReflectionUtil.getCBClass("inventory.CraftItemStack"), "asNMSCopy", ItemStack.class);

    public static Object toNMS(ItemStack stack) {
        return ReflectionUtil.invokeMethod(TO_NMS, null, stack);
    }
}
