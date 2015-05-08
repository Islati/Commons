package com.caved_in.commons.item;

/*
todo finish this class with methods to retrieve the given armor type based on the material passed to it
 */

import com.google.common.collect.Sets;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum ArmorType {
    LEATHER(Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET),
    IRON(Material.IRON_BOOTS, Material.IRON_LEGGINGS, Material.IRON_CHESTPLATE, Material.IRON_HELMET),
    GOLD(Material.GOLD_BOOTS, Material.GOLD_LEGGINGS, Material.GOLD_CHESTPLATE, Material.GOLD_HELMET),
    CHAIN(Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET),
    DIAMOND(Material.DIAMOND_BOOTS, Material.DIAMOND_LEGGINGS, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET);

    private static Map<ArmorType, Set<Material>> armorTypes = new HashMap<>();

    static {
        /* Initialize all the types to cache on load! */
        for (ArmorType type : ArmorType.values()) {
            armorTypes.put(type, Sets.newHashSet(type.armor));
        }
    }

    private Material[] armor;

    ArmorType(Material... type) {
        this.armor = type;
    }

    /**
     * Get the type of armor for the material that's passed.
     *
     * @param mat material to get the associated armor type of.
     * @return the type of armor that the material is, if not armor, then null.
     */
    public static ArmorType getArmorType(Material mat) {
        for (Map.Entry<ArmorType, Set<Material>> armorTypeEntry : armorTypes.entrySet()) {
            if (armorTypeEntry.getValue().contains(mat)) {
                return armorTypeEntry.getKey();
            }
        }
        return null;
    }
}
