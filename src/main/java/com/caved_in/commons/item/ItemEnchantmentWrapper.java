package com.caved_in.commons.item;

import com.caved_in.commons.yml.YamlConfig;
import org.bukkit.enchantments.Enchantment;

public class ItemEnchantmentWrapper extends YamlConfig {
    private Enchantment enchantment;
    private int level;
    private boolean itemGlow = false;
    private boolean treasure = false;

    public ItemEnchantmentWrapper(Enchantment enchantment, int level, boolean itemGlow, boolean treasure) {
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
