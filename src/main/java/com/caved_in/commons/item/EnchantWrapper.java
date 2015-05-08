package com.caved_in.commons.item;

import org.bukkit.enchantments.Enchantment;

public class EnchantWrapper {
    private Enchantment enchantment;
    private int level;
    private boolean itemGlow = false;

    public EnchantWrapper(Enchantment enchantment, int level, boolean itemGlow) {
        this.enchantment = enchantment;
        this.level = level;
        this.itemGlow = itemGlow;
    }

    public EnchantWrapper() {


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

    public EnchantWrapper enchantment(Enchantment enchant, int level) {
        this.enchantment = enchant;
        this.level = level;
        return this;
    }

    public EnchantWrapper glow(boolean glow) {
        this.itemGlow = glow;
        return this;
    }
}
