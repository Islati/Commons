package com.caved_in.commons.nms.non_breaking_implementation;

import com.caved_in.commons.nms.InventoryHandler;
import com.caved_in.commons.reflection.ReflectionUtilities;
import org.bukkit.inventory.Inventory;

public class NonBreakingInventoryHandler implements InventoryHandler {
    static Class<?> obcCraftInventory;
    static Class<?> obcCraftInventoryCustom;
    static Class<?> obcMinecraftInventory;

    static {
        try {
            obcCraftInventory = ReflectionUtilities.getOBCClass("inventory.CraftInventory");
            obcCraftInventoryCustom = ReflectionUtilities.getOBCClass("inventory.CraftInventoryCustom");
            for (Class<?> c : obcCraftInventoryCustom.getDeclaredClasses()) {
                if (c.getSimpleName().equals("MinecraftInventory")) {
                    obcMinecraftInventory = c;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeTitle(Inventory inv, String title) {
        try {
            Object minecrafInventory = ReflectionUtilities.setAccessible(obcCraftInventory.getDeclaredField("inventory")).get(inv);
            ReflectionUtilities.setAccessible(obcMinecraftInventory.getDeclaredField("displayName")).set(minecrafInventory, title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
