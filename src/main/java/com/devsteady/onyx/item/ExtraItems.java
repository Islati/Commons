package com.devsteady.onyx.item;

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
    /* Wood Planks */
    SPRUCE_WOOD_PLANK(Material.WOOD, 1, "spruce", "sprucewood", "pine"),
    BIRCH_WOOD_PLANK(Material.WOOD, 2, "birch", "birchwood"),
    JUNGLE_WOOD_PLANK(Material.WOOD, 3, "jungle", "junglewood"),
    ACAIA_WOOD_PLANK(Material.WOOD, 4, "acaia", "orange", "acaya"),
    DARK_OAK_WOOD_PLANK(Material.WOOD, 5, "darkoak", "blackoak"),
    /* Sappling Types */
    SPRUCE_SAPLING(Material.SAPLING, 1, "spruce", "pine"),
    BIRCH_SAPLING(Material.SAPLING, 2, "birch"),
    JUNGLE_SAPLING(Material.SAPLING, 3, "jungle"),
    ACAIA_SAPLING(Material.SAPLING, 4, "acaia", "orange"),
    DARK_OAK_SAPLING(Material.SAPLING, 5, "darkoak", "blackoak"),
    RED_SAND(Material.SAND, 1, "red"),
    /* Log Wood Types */
    SPRUCE_LOG(Material.LOG, 1, "spruce", "pine"),
    BIRCH_LOG(Material.LOG, 2, "birch"),
    JUNGLE_LOG(Material.LOG, 3, "jungle"),
    SPRUCE_LEAVES(Material.LEAVES, 1, "spruce"),
    BIRCH_LEAVES(Material.LEAVES, 2, "birch"),
    JUNGLE_LEAVES(Material.LEAVES, 3, "jungle"),
    /* Sandstone */
    CHISELED_SANDSTONE(Material.SANDSTONE, 1, "chiseled"),
    SMOOTH_SANDSTONE(Material.SANDSTONE, 2, "smooth"),
    /* Grass */
    GRASS(Material.getMaterial(31), 1, "tallgrass", "grass"),
    FERN(Material.getMaterial(31), 2, "fern", "smalltree"),
    /* WOOL */
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
    /* Flowers */
    BLUE_ORCHID(Material.getMaterial(38), 1, "orchid"),
    ALLIUM(Material.getMaterial(38), 2, "allium"),
    AZURE_BLUET(Material.getMaterial(38), 3, "azure", "bluet", "azurebluet", "azure bluet"),
    RED_TULIP(Material.getMaterial(38), 4, "redtulip", "red"),
    ORANGE_TULIP(Material.getMaterial(38), 5, "orangetulip", "orange"),
    WHITE_TULIP(Material.getMaterial(38), 6, "white", "whitetulip"),
    PINK_TULIP(Material.getMaterial(38), 7, "pinktulip", "pink"),
    OXEYE_DAISY(Material.getMaterial(38), 8, "daisy", "oxeyedaisy"),

    /* Slabs */
    DOUBLE_SANDSTONE_SLAB(Material.getMaterial(43), 1, "sandstone"),
    DOUBLE_WOODEN_SLAB(Material.getMaterial(43), 2, "wood", "wooden"),
    DOUBLE_COBBLESTONE_SLAB(Material.getMaterial(43), 3, "cobble", "cobblestone"),
    DOUBLE_BRICK_SLAB(Material.getMaterial(43), 4, "doublebrick", "thickbrickslab"),
    DOUBLE_STONEBRICK_SLAB(Material.getMaterial(43), 5, "stonebrick", "stone brick", "smoothstone"),
    DOUBLE_NETHER_BRICK_SLAB(Material.getMaterial(43), 6, "nether", "netherbrick"),
    DOUBLE_QUARTZ_SLAB(Material.getMaterial(43), 7, "quartz"),
    SANDSTONE_SLAB(Material.getMaterial(44), 1, "sandstone"),
    WOODEN_SLAB(Material.getMaterial(44), 2, "wood", "wooden"),
    COBBLESTONE_SLAB(Material.getMaterial(44), 3, "cobble", "cobblestone"),
    BRICK_SLAB(Material.getMaterial(44), 4, "brick", "brickslab", "brick_slab"),
    STONEBRICK_SLAB(Material.getMaterial(44), 5, "stonebrick", "stone brick", "smoothstone"),
    NETHER_BRICK_SLAB(Material.getMaterial(44), 6, "nether", "netherbrick"),
    QUARTZ_SLAB(Material.getMaterial(44), 7, "quartz"),

    /* Stained Glass */
    ORANGE_GLASS(Material.STAINED_GLASS, 1, "orange_glass", "orangeglass"),
    MAGENTA_GLASS(Material.STAINED_GLASS, 2, "magenta_glass", "magentaglass"),
    LIGHT_BLUE_GLASS(Material.STAINED_GLASS, 3, "light_blue_glass", "lightblue_glass", "lightblueglass"),
    YELLOW_GLASS(Material.STAINED_GLASS, 4, "yellow_glass", "yellowglass"),
    LIME_GLASS(Material.STAINED_GLASS, 5, "lime_glass", "limeglass"),
    PINK_STAINED_GLASS(Material.STAINED_GLASS, 6, "pink_glass", "pinkglass"),
    GRAY_GLASS(Material.STAINED_GLASS, 7, "gray_glass", "grayglass"),
    LIGHT_GRAY_GLASS(Material.STAINED_GLASS, 8, "light_gray_glass", "lightgray_glass", "lightgrayglass"),
    CYAN_GLASS(Material.STAINED_GLASS, 9, "cyanglass", "cyan_glass"),
    PURPLE_GLASS(Material.STAINED_GLASS, 10, "purpleglass", "purple_glass"),
    BLUE_GLASS(Material.STAINED_GLASS, 11, "blueglass", "blue_glass"),
    BROWN_GLASS(Material.STAINED_GLASS, 12, "brown_glass", "brownglass"),
    GREEN_GLASS(Material.STAINED_GLASS, 13, "greenglass", "green_glass"),
    RED_GLASS(Material.STAINED_GLASS, 14, "redglass", "red_glass"),
    BLACK_GLASS(Material.STAINED_GLASS, 15, "blackglass", "black_glass"),

    /* Stained Glass Panes */
    ORANGE_GLASS_PANE(Material.STAINED_GLASS_PANE, 1, "orange_glass_pane", "orangeglasspane"),
    MAGENTA_GLASS_PANE(Material.STAINED_GLASS_PANE, 2, "magenta_glass_pane", "magentaglasspane"),
    LIGHT_BLUE_GLASS_PANE(Material.STAINED_GLASS_PANE, 3, "light_blue_glass_pane", "lightblue_glasspane", "lightblueglasspane"),
    YELLOW_GLASS_PANE(Material.STAINED_GLASS_PANE, 4, "yellow_glass_pane", "yellowglasspane"),
    LIME_GLASS_PANE(Material.STAINED_GLASS_PANE, 5, "lime_glass_pane", "limeglass_pane"),
    PINK_STAINED_GLASS_PANE(Material.STAINED_GLASS_PANE, 6, "pink_glass_pane", "pinkglasspane"),
    GRAY_GLASS_PANE(Material.STAINED_GLASS_PANE, 7, "gray_glass_pane", "grayglasspane"),
    LIGHT_GRAY_GLASS_PANE(Material.STAINED_GLASS_PANE, 8, "light_gray_glass_pane", "lightgray_glass_pane", "lightgrayglasspane"),
    CYAN_GLASS_PANE(Material.STAINED_GLASS_PANE, 9, "cyanglasspane", "cyan_glass_pane"),
    PURPLE_GLASS_PANE(Material.STAINED_GLASS_PANE, 10, "purpleglasspane", "purple_glass_pane"),
    BLUE_GLASS_PANE(Material.STAINED_GLASS_PANE, 11, "blueglasspane", "blue_glass_pane"),
    BROWN_GLASS_PANE(Material.STAINED_GLASS_PANE, 12, "brown_glass_pane", "brownglasspane"),
    GREEN_GLASS_PANE(Material.STAINED_GLASS_PANE, 13, "greenglasspane", "green_glass_pane"),
    RED_GLASS_PANE(Material.STAINED_GLASS_PANE, 14, "redglass_pane", "red_glass_pane"),
    BLACK_GLASS_PANE(Material.STAINED_GLASS_PANE, 15, "blackglass_pane", "black_glass_pane"),


    /* Monster Eggs */
    COBBLESTONE_MONSTER_EGG(Material.MONSTER_EGG, 1, "cobblestone_monsteregg", "cobbleegg", "cobblemonsteregg"),
    STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGG, 2, "stone_brick_monster_egg", "stonebrick_monsteregg", "stonebrickmonsteregg"),
    MOSSY_STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGG, 3, "mossy_stone_brick_monster_egg", "mossy_stone_brick_monsteregg", "mossy_monster_egg", "mossmonsteregg", "mossy_brick_monsteregg", "mossystonebrickmonsteregg", "mossbrickmonsteregg"),
    CRACKED_STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGG, 4, "cracked_monsteregg", "crackedstone_monsteregg", "crackedstonebrickmonsteregg", "crackedmonsteregg", "cbmonsteregg", "cracked_stone_brick_monster_egg"),
    CHISELED_STONE_BRICK_MONSTER_EGG(Material.MONSTER_EGG, 5, "chiseled_monsteregg", "chiseled_stone_monsteregg"),

    /* Decorative Stone Bricks */
    MOSSY_STONE_BRICK(Material.SMOOTH_BRICK,1,"mossy_stone_brick","mossystonebrick","mossbrick"),
    CRACKED_STONE_BRICK(Material.SMOOTH_BRICK,2,"cracked_stone_brick","crackedstonebrick","crackedbrick"),
    CHISELED_STONE_BRICK(Material.SMOOTH_BRICK,3,"chiseled_stone_brick","chiseledstonebrick","chiseledbrick"),

    /* Decorative Quartz Bricks */
    CHISELED_QUARTZ(Material.QUARTZ_BLOCK,1,"chiseled_quartz","chiseledquartz"),
    PILLAR_QUARTZ(Material.QUARTZ_BLOCK,2,"quartz_pillar","quartzpillar"),

    /* Coloured Carped */
    YELLOW_CARPET(Material.CARPET, 4, "yellow_carpet", "yellowcarpet"),
    LIGHT_GRAY_CARPET(Material.CARPET, 8, "light_gray_carpet", "lightgraycarpet"),
    LIGHT_BLUE_CARPET(Material.CARPET, 3, "light_blue_carpet", "lightbluecarpet"),
    RED_CARPET(Material.CARPET, 14, "red_carpet", "redcarpet"),
    BROWN_CARPET(Material.CARPET, 12, "brown_carpet", "browncarpet"),
    ORANGE_CARPET(Material.CARPET, 1, "orange_carpet", "orangecarpet"),
    GRAY_CARPET(Material.CARPET, 7, "gray_carpet", "graycarpet"),
    PINK_CARPET(Material.CARPET, 6, "pink_carpet", "pinkcarpet"),
    BLUE_CARPET(Material.CARPET, 11, "blue_carpet", "bluecarpet"),
    PURPLE_CARPET(Material.CARPET, 10, "purple_carpet", "purplecarpet"),
    CYAN_CARPET(Material.CARPET, 9, "cyan_carpet", "cyancarpet"),
    LIME_CARPET(Material.CARPET, 5, "lime_carpet", "limecarpet"),
    MAGENTA_CARPET(Material.CARPET, 2, "magenta_carpet", "magentacarpet"),
    BLACK_CARPET(Material.CARPET, 15, "black_carpet", "blackcarpet"),
    GREEN_CARPET(Material.CARPET, 13, "green_carpet", "greencarpet"),

    /* Alternative Stone Materials */
    GRANITE(Material.STONE, 1, "granite"),
    POLISHED_GRANITE(Material.STONE, 2, "polished_granite", "polishedgranite"),
    DIORITE(Material.STONE, 3, "diorite"),
    POLISHED_DIORITE(Material.STONE, 4, "polished_diorite", "polisheddiorite", "finediorite"),
    ANDESITE(Material.STONE, 5, "andesite"),
    POLISHED_ANDESITE(Material.STONE, 6, "polished_andesite", "polishedandesite", "fineandesite");

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
