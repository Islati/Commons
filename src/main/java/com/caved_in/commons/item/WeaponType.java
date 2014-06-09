package com.caved_in.commons.item;

import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.bukkit.Material.*;


public enum WeaponType {
	SWORD(DIAMOND_SWORD, GOLD_SWORD, IRON_SWORD, STONE_SWORD, WOOD_SWORD),
	AXE(DIAMOND_AXE, GOLD_AXE, IRON_AXE, STONE_AXE, WOOD_AXE),
	BOW(Material.BOW),
	POTION(Material.POTION);

	private static final Map<Material, WeaponType> weaponTypes = new HashMap<>();

	static {
		for (WeaponType weaponType : EnumSet.allOf(WeaponType.class)) {
			for (Material material : weaponType.getMaterials()) {
				weaponTypes.put(material, weaponType);
			}
		}
	}

	private Set<Material> materials;

	WeaponType(Material... materials) {
		this.materials = Sets.newHashSet(materials);
	}

	public Set<Material> getMaterials() {
		return materials;
	}

	public boolean isSameType(ItemStack itemStack) {
		return materials.contains(itemStack.getType());
	}

	public static boolean isItemWeapon(ItemStack itemStack, WeaponType weaponType) {
		Material material = itemStack.getType();
		if (!weaponTypes.containsKey(material)) {
			return false;
		}

		WeaponType type = weaponTypes.get(material);
		return type == weaponType;
	}

	public static boolean isMaterialWeapon(Material material) {
		return weaponTypes.containsKey(material);
	}

	public static boolean isItemWeapon(ItemStack itemStack) {
		return isMaterialWeapon(itemStack.getType());
	}
}
