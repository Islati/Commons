package com.caved_in.commons.item;

import com.google.common.collect.Sets;
import org.bukkit.Material;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum ToolType {

    HOE(Material.WOOD_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.GOLD_HOE, Material.DIAMOND_HOE),
    AXE(Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.DIAMOND_AXE),
    SHOVEL(Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.DIAMOND_SPADE),
    PICK_AXE(Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.DIAMOND_PICKAXE),
    FIRE_STARTER(Material.FLINT_AND_STEEL),
    UTILITY(Material.BUCKET, Material.FISHING_ROD, Material.CARROT_STICK, Material.SHEARS),
    REDSTONE(Material.REDSTONE, Material.REDSTONE_COMPARATOR, Material.REDSTONE_TORCH_ON, Material.REDSTONE_LAMP_OFF, Material.DAYLIGHT_DETECTOR, Material.STONE_BUTTON, Material.WOOD_BUTTON, Material.GOLD_PLATE, Material.IRON_PLATE, Material.WOOD_PLATE, Material.STONE_PLATE, Material.DISPENSER, Material.HOPPER, Material.DROPPER, Material.PISTON_BASE, Material.PISTON_STICKY_BASE, Material.NOTE_BLOCK, Material.TNT, Material.FENCE_GATE, Material.TRIPWIRE_HOOK, Material.REDSTONE_BLOCK, Material.getMaterial(356)/* Redstone repeater */,Material.TRAP_DOOR,Material.WOOD_DOOR);

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
