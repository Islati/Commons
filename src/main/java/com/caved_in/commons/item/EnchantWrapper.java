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

	public Enchantment getEnchantment() {
		return enchantment;
	}

	public boolean isItemGlow() {
		return itemGlow;
	}

	public int getLevel() {
		return level;
	}
}
