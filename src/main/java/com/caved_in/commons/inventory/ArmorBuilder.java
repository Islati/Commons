package com.caved_in.commons.inventory;

import com.caved_in.commons.entity.CreatureBuilder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ArmorBuilder {
	private Map<ArmorSlot, ItemStack> armor = new HashMap<>();

	private CreatureBuilder creatureBuilder;

	public ArmorBuilder() {

	}

	public ArmorBuilder(CreatureBuilder creatureBuilder) {
		this.creatureBuilder = creatureBuilder;
	}

	private void setItem(ArmorSlot slot, ItemStack item) {
		armor.put(slot, item);
	}

	public ArmorBuilder withHelmet(ItemStack item) {
		setItem(ArmorSlot.HELMET, item);
		return this;
	}

	public ArmorBuilder withChest(ItemStack item) {
		setItem(ArmorSlot.CHEST, item);
		return this;
	}

	public ArmorBuilder withLeggings(ItemStack item) {
		setItem(ArmorSlot.LEGGINGS, item);
		return this;
	}

	public ArmorBuilder withBoots(ItemStack item) {
		setItem(ArmorSlot.BOOTS, item);
		return this;
	}

	public ArmorBuilder withWeapon(ItemStack item) {
		setItem(ArmorSlot.WEAPON, item);
		return this;
	}

	public ArmorBuilder parent(CreatureBuilder builder) {
		this.creatureBuilder = builder;
		return this;
	}

	public CreatureBuilder parent() {
		return creatureBuilder;
	}

	public ArmorInventory toInventory() {
		ArmorInventory inv = new ArmorInventory();
		armor.entrySet().forEach(e -> inv.setItem(e.getKey(), e.getValue()));
		return inv;
	}
}
