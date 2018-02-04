package com.devsteady.onyx.item;

import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.YamlConfig;
import org.bukkit.enchantments.Enchantment;

public class ItemEnchantmentWrapper extends YamlConfig {
    @Path("enchant")
    private Enchantment enchantment;
    @Path("level")
    private int level;
    @Path("glow")
    private boolean itemGlow = false;

    /* Not in 1.7, hence second constructor */
    @Path("treasure")
    private boolean treasure = false;

    public ItemEnchantmentWrapper(Enchantment enchantment, int level, boolean itemGlow, boolean treasure) {
        this.enchantment = enchantment;
        this.level = level;
        this.itemGlow = itemGlow;
        this.treasure = treasure;
    }

    public ItemEnchantmentWrapper(Enchantment enchantment, int level, boolean itemGlow) {
        this.enchantment = enchantment;
        this.level = level;
        this.itemGlow = itemGlow;
    }

    public ItemEnchantmentWrapper() {


    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public boolean isItemGlow() {
        return itemGlow;
    }

    public int getLevel() {
        return level;
    }

    public ItemEnchantmentWrapper enchantment(Enchantment enchant, int level) {
        this.enchantment = enchant;
        this.level = level;
        return this;
    }

    public ItemEnchantmentWrapper glow(boolean glow) {
        this.itemGlow = glow;
        return this;
    }
}
