package com.caved_in.commons.inventory;

import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ArmorInventory {
	private Map<ArmorSlot, ItemStack> armorSlots = new HashMap<>();


	public ArmorInventory() {

	}

	public ArmorInventory(Map<ArmorSlot, ItemStack> armor) {
		armorSlots.putAll(armor);
	}

	public ArmorInventory(ItemStack[] armor) {
		for (int i = 0; i < armor.length; i++) {
			ArmorSlot slot = ArmorSlot.getSlot(i);
			this.armorSlots.put(slot, armor[i]);
		}
	}

	public void setItem(ArmorSlot slot, ItemStack item) {
		armorSlots.put(slot, item);
	}

	public void equip(LivingEntity entity) {
		if (entity instanceof Player) {
			Players.setArmor((Player) entity, this);
		} else {
			Entities.setEquipment(entity, this);
		}
	}

	public Map<ArmorSlot, ItemStack> getArmor() {
		return armorSlots;
	}
}
