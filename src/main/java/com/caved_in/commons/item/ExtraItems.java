package com.caved_in.commons.item;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public enum ExtraItems {
    COARSE_DIRT(Material.DIRT, 1, "coarse", "corse", "course"),
    PODZOL(Material.DIRT, 2, "podzol", "podsol", "posol", "potsoul"),
    SPRUCE_WOOD_PLANK(Material.WOOD, 1, "spruce", "sprucewood", "pine"),
    BIRCH_WOOD_PLANK(Material.WOOD, 2, "birch", "birchwood"),
    JUNGLE_WOOD_PLANK(Material.WOOD, 3, "jungle", "junglewood"),
    ACAIA_WOOD_PLANK(Material.WOOD, 4, "acaia", "orange", "acaya"),
    DARK_OAK_WOOD_PLANK(Material.WOOD, 5, "darkoak", "blackoak"),
    SPRUCE_SAPLING(Material.SAPLING, 1, "spruce", "pine"),
    BIRCH_SAPLING(Material.SAPLING, 2, "birch"),
    JUNGLE_SAPLING(Material.SAPLING, 3, "jungle"),
    ACAIA_SAPLING(Material.SAPLING, 4, "acaia", "orange"),
    DARK_OAK_SAPLING(Material.SAPLING, 5, "darkoak", "blackoak"),
    RED_SAND(Material.SAND, 1, "red"),
    SPRUCE_LOG(Material.LOG, 1, "spruce", "pine"),
    BIRCH_LOG(Material.LOG, 2, "birch"),
    JUNGLE_LOG(Material.LOG, 3, "jungle"),
    SPRUCE_LEAVES(Material.LEAVES, 1, "spruce"),
    BIRCH_LEAVES(Material.LEAVES, 2, "birch"),
    JUNGLE_LEAVES(Material.LEAVES, 3, "jungle"),
    CHISELED_SANDSTONE(Material.SANDSTONE, 1, "chiseled"),
    SMOOTH_SANDSTONE(Material.SANDSTONE, 2, "smooth"),
    GRASS(Material.getMaterial(31), 1, "tallgrass", "grass"),
    FERN(Material.getMaterial(31), 2, "fern", "smalltree"),
    ORANGE_WOOL(Material.WOOL, 1, "orange"),
    MAGENTA_WOOL(Material.WOOL, 2, "magenta"),
    LIGHT_BLUE_WOOL(Material.WOOL, 3, "lightblue", "light blue"),
    YELLOW_WOOL(Material.WOOL, 4, "yellow"),
    LIME_WOOL(Material.WOOL, 5, "lime"),
    PINK_WOOL(Material.WOOL, 6, "pink"),
    GRAY_WOOL(Material.WOOL, 7, "gray"),
    LIGHT_GRAY_WOOL(Material.WOOL, 8, "lightgray", "light gray"),
    CYAN_WOOL(Material.WOOL, 9, "cyan"),
    PURPLE_WOOL(Material.WOOL, 10, "purple"),
    BLUE_WOOL(Material.WOOL, 11, "blue"),
    BROWN_WOOL(Material.WOOL, 12, "brown"),
    GREEN_WOOL(Material.WOOL, 13, "green"),
    RED_WOOL(Material.WOOL, 14, "red"),
    BLACK_WOOL(Material.WOOL, 15, "black"),
    BLUE_ORCHID(Material.getMaterial(38), 1, "orchid"),
    ALLIUM(Material.getMaterial(38), 2, "allium"),
    AZURE_BLUET(Material.getMaterial(38), 3, "azure", "bluet", "azurebluet", "azure bluet"),
    RED_TULIP(Material.getMaterial(38), 4, "redtulip", "red"),
    ORANGE_TULIP(Material.getMaterial(38), 5, "orangetulip", "orange"),
    WHITE_TULIP(Material.getMaterial(38), 6, "white", "whitetulip"),
    PINK_TULIP(Material.getMaterial(38), 7, "pinktulip", "pink"),
    OXEYE_DAISY(Material.getMaterial(38), 8, "daisy", "oxeyedaisy"),
    DOUBLE_SANDSTONE_SLAB(Material.getMaterial(43), 1, "sandstone"),
    DOUBLE_WOODEN_SLAB(Material.getMaterial(43), 2, "wood", "wooden"),
    DOUBLE_COBBLESTONE_SLAB(Material.getMaterial(43), 3, "cobble", "cobblestone"),
    DOUBLE_BRICK_SLAB(Material.getMaterial(43), 4, "brick"),
    DOUBLE_STONEBRICK_SLAB(Material.getMaterial(43), 5, "stonebrick", "stone brick", "smoothstone"),
    DOUBLE_NETHER_BRICK_SLAB(Material.getMaterial(43), 6, "nether", "netherbrick"),
    DOUBLE_QUARTZ_SLAB(Material.getMaterial(43), 7, "quartz"),
    SANDSTONE_SLAB(Material.getMaterial(44), 1, "sandstone"),
    WOODEN_SLAB(Material.getMaterial(44), 2, "wood", "wooden"),
    COBBLESTONE_SLAB(Material.getMaterial(44), 3, "cobble", "cobblestone"),
    BRICK_SLAB(Material.getMaterial(44), 4, "brick"),
    STONEBRICK_SLAB(Material.getMaterial(44), 5, "stonebrick", "stone brick", "smoothstone"),
    NETHER_BRICK_SLAB(Material.getMaterial(44), 6, "nether", "netherbrick"),
    QUARTZ_SLAB(Material.getMaterial(44), 7, "quartz");

    private Material material;
    private int dataValue;
    private String[] aliases;

    private static Map<Material, Map<Integer, Set<String>>> materialValueAliases = new HashMap<>();

    static {
        for (ExtraItems extraItems : EnumSet.allOf(ExtraItems.class)) {
            Material material = extraItems.getMaterial();
            int dataValue = extraItems.getDataValue();
            Set<String> valueAliases = Sets.newHashSet(extraItems.getAliases());
            materialValueAliases.put(material, new HashMap<>());
            materialValueAliases.get(material).put(dataValue, valueAliases);
        }
    }

    public static MaterialData getItem(Material material, String id) {
        if (!materialValueAliases.containsKey(material)) {
            return null;
        }


        Map<Integer, Set<String>> idAliases = materialValueAliases.get(material);
        for (Map.Entry<Integer, Set<String>> entry : idAliases.entrySet()) {
            if (!entry.getValue().contains(id)) {
                return null;
            }
            return Items.getMaterialData(material, entry.getKey());
        }
        return null;
    }

    ExtraItems(Material material, int dataValue, String... aliases) {
        this.material = material;
        this.dataValue = dataValue;
        this.aliases = aliases;
    }

    public ItemStack getItem() {
        return Items.makeItem(getMaterial(), getDataValue());
    }

    public Material getMaterial() {
        return material;
    }

    public int getDataValue() {
        return dataValue;
    }

    public String[] getAliases() {
        return aliases;
    }
}
