package com.caved_in.commons.npc;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Material.*;

public enum SlotType {

	ITEM_IN_HAND,
	HELMET(LEATHER_HELMET, CHAINMAIL_HELMET, GOLD_HELMET, DIAMOND_HELMET, IRON_HELMET),
	BODY_ARMOR(LEATHER_CHESTPLATE, CHAINMAIL_CHESTPLATE, GOLD_CHESTPLATE, DIAMOND_CHESTPLATE, IRON_CHESTPLATE),
	PANTS(LEATHER_LEGGINGS, CHAINMAIL_LEGGINGS, GOLD_LEGGINGS, DIAMOND_LEGGINGS, IRON_LEGGINGS),
	SHOES(LEATHER_BOOTS, CHAINMAIL_BOOTS, GOLD_BOOTS, DIAMOND_BOOTS, IRON_BOOTS);

	private static Map<Material, SlotType> materialSlots = new HashMap<>();

	static {
		for (SlotType slotType : EnumSet.allOf(SlotType.class)) {
			for (Material material : slotType.materials) {
				materialSlots.put(material, slotType);
			}
		}
	}

	private Material[] materials;

	SlotType(Material... materials) {
		this.materials = materials;
	}

	public int toNumeric() {
		switch (this) {
			case ITEM_IN_HAND:
				return 0;
			case HELMET:
				return 1;
			case BODY_ARMOR:
				return 2;
			case PANTS:
				return 3;
			case SHOES:
				return 4;
			default:
				return -1;
		}
	}

	public static SlotType getTypeFor(ItemStack itemStack) {
		return getTypeFor(itemStack.getType());
	}

	public static SlotType getTypeFor(Material material) {
		if (materialSlots.containsKey(material)) {
			return materialSlots.get(material);
		} else {
			return SlotType.ITEM_IN_HAND;
		}
	}
}
