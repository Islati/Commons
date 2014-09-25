package com.caved_in.commons.item;

import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ArmorSet {
	LEATHER("commons.armor.leather", Items.LEATHER_ARMOR, "leather"),
	IRON("commons.armor.iron", Items.IRON_ARMOR, "iron"),
	GOLD("commons.armor.gold", Items.GOLD_ARMOR, "gold"),
	DIAMOND("commons.armor.diamond", Items.DIAMOND_ARMOR, "diamond");

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

	public static ArmorSet getSetByName(String name) {
		return sets.get(name);
	}
}
