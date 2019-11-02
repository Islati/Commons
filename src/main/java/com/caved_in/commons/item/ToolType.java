package com.caved_in.commons.item;

import com.google.common.collect.Sets;
import org.bukkit.Material;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum ToolType {

    HOE(Material.WOODEN_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLDEN_HOE, Material.DIAMOND_HOE),
    AXE(Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLDEN_AXE, Material.DIAMOND_AXE),
    SHOVEL(Material.WOODEN_SHOVEL, Material.STONE_SHOVEL, Material.IRON_SHOVEL, Material.GOLDEN_SHOVEL, Material.DIAMOND_SHOVEL),
    PICK_AXE(Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE),
    FIRE_STARTER(Material.FLINT_AND_STEEL),
    UTILITY(Material.BUCKET, Material.FISHING_ROD, Material.CARROT_ON_A_STICK, Material.SHEARS),
    REDSTONE(Material.REDSTONE, Material.COMPARATOR, Material.REDSTONE_TORCH, Material.REDSTONE_LAMP, Material.DAYLIGHT_DETECTOR, Material.STONE_BUTTON, Material.OAK_BUTTON, Material.ACACIA_BUTTON, Material.BIRCH_BUTTON, Material.JUNGLE_BUTTON, Material.SPRUCE_BUTTON, Material.DARK_OAK_BUTTON, Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.JUNGLE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE, Material.STONE_PRESSURE_PLATE, Material.DISPENSER, Material.HOPPER, Material.DROPPER, Material.PISTON, Material.NOTE_BLOCK, Material.TNT, Material.ACACIA_FENCE, Material.BIRCH_FENCE, Material.DARK_OAK_FENCE, Material.JUNGLE_FENCE, Material.NETHER_BRICK_FENCE, Material.OAK_FENCE, Material.SPRUCE_FENCE, Material.TRIPWIRE_HOOK, Material.REDSTONE_BLOCK, Material.REPEATER, Material.ACACIA_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.ACACIA_TRAPDOOR, Material.BIRCH_TRAPDOOR, Material.DARK_OAK_TRAPDOOR, Material.IRON_TRAPDOOR, Material.JUNGLE_TRAPDOOR, Material.OAK_TRAPDOOR, Material.SPRUCE_TRAPDOOR, Material.IRON_TRAPDOOR, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.IRON_DOOR, Material.JUNGLE_DOOR, Material.SPRUCE_DOOR, Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.DARK_OAK_DOOR, Material.IRON_DOOR, Material.JUNGLE_DOOR, Material.OAK_DOOR, Material.SPRUCE_DOOR);

    private static Set<Material> validTools = Sets.newHashSet();

    private static Map<ToolType, Set<Material>> toolIndexedMaterials = new HashMap<>();

    static {
        for (ToolType toolType : EnumSet.allOf(ToolType.class)) {
            Set<Material> materials = toolType.getMaterialTypes();
            /* All all the materials of the tool type to the set of valid tools */
            validTools.addAll(materials);

            /*
			Populate the tool-indexed map of materials; Used for static
            retrieval and checking of types for materials!
             */
            toolIndexedMaterials.put(toolType, materials);
        }
    }

    private Set<Material> toolTypes;

    ToolType(Material... types) {
        toolTypes = Sets.newHashSet(types);
    }

    public Set<Material> getMaterialTypes() {
        return toolTypes;
    }

    public boolean isType(Material material) {
        return toolTypes.contains(material);
    }

    public static boolean isTool(Material material) {
        return validTools.contains(material);
    }

    /**
     * Get the tool type of the given material.
     *
     * @param mat material to get the tool type for
     * @return tooltype for the given material if it's a tool, if it's not a tool null is returned.
     */
    public static ToolType getType(Material mat) {
        if (!isTool(mat)) {
            return null;
        }

        for (ToolType type : toolIndexedMaterials.keySet()) {
            if (!type.isType(mat)) {
                continue;
            }

            return type;
        }

        return null;
    }

    public static boolean isType(ToolType toolType, Material material) {
        return toolType.isType(material);
    }
}
