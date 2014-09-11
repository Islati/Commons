package com.caved_in.commons.inventory;

import com.caved_in.commons.config.XmlItemStack;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.HashMap;
import java.util.Map;

@Root(name = "armor")
public class ArmorInventory {
	@ElementMap(name = "items", entry = "slot", value = "item", keyType = Integer.class, valueType = XmlItemStack.class)
	private Map<Integer, XmlItemStack> serializableArmorSlots = new HashMap<>();

	private Map<ArmorSlot, ItemStack> armorSlots = new HashMap<>();

	public ArmorInventory() {

	}

	public ArmorInventory(@ElementMap(name = "items", entry = "slot", value = "item", keyType = Integer.class, valueType = XmlItemStack.class) Map<Integer, XmlItemStack> armor) {
		serializableArmorSlots.putAll(armor);
		for (Map.Entry<Integer, XmlItemStack> armorItem : serializableArmorSlots.entrySet()) {
			armorSlots.put(ArmorSlot.getSlot(armorItem.getKey()), armorItem.getValue().getItemStack());
		}
	}

	public ArmorInventory(ItemStack[] armor) {
		for (int i = 0; i < armor.length; i++) {
			ArmorSlot slot = ArmorSlot.getSlot(i);
			ItemStack item = armor[i];
			armorSlots.put(slot, item);
			serializableArmorSlots.put(slot.getSlot(), XmlItemStack.fromItem(item));
		}
	}

	public void setItem(ArmorSlot slot, ItemStack item) {
		armorSlots.put(slot, item);
		serializableArmorSlots.put(slot.getSlot(), XmlItemStack.fromItem(item));
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
