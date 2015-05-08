package com.caved_in.commons.item;

import com.caved_in.commons.inventory.ArmorInventory;
import com.caved_in.commons.permission.Perms;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ArmorSet {
    LEATHER(Perms.LEATHER_ARMOR_SET, Items.LEATHER_ARMOR, "leather"),
    IRON(Perms.IRON_ARMOR_SET, Items.IRON_ARMOR, "iron"),
    GOLD(Perms.GOLD_ARMOR_SET, Items.GOLD_ARMOR, "gold"),
    CHAIN(Perms.CHAIN_ARMOR_SET, Items.CHAIN_ARMOR, "chain"),
    DIAMOND(Perms.DIAMOND_ARMOR_SET, Items.DIAMOND_ARMOR, "diamond");

    private static Map<String, ArmorSet> sets = new HashMap<>();

    static {
        for (ArmorSet set : EnumSet.allOf(ArmorSet.class)) {
            for (String identified : set.getIdentifiers()) {
                sets.put(identified, set);
            }
        }
    }

    private ItemStack[] armor;
    private String permission;
    private String[] identifiers;

    ArmorSet(String permission, ItemStack[] armor, String... identifiers) {
        this.armor = armor;
        this.permission = permission;
        this.identifiers = identifiers;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getIdentifiers() {
        return identifiers;
    }

    public ArmorInventory getArmorInventory() {
        return new ArmorInventory(getArmor());
    }

    public static ArmorSet getSetByName(String name) {
        return sets.get(name);
    }
}
