package com.caved_in.commons.inventory;

import com.caved_in.commons.config.SerializableItemStack;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.yml.ConfigMode;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.SerializeOptions;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@SerializeOptions(
        configMode = ConfigMode.DEFAULT
)
public class ArmorInventory {

    @Path("items")
    private Map<ArmorSlot, SerializableItemStack> armorItems = new HashMap<>();

    public ArmorInventory() {

    }

    public ArmorInventory(ItemStack[] armor) {
        for (int i = 0; i < armor.length; i++) {
            ArmorSlot slot = ArmorSlot.getSlot(i);
            ItemStack item = armor[i];
            armorItems.put(slot, SerializableItemStack.fromItem(item));
        }
    }

    public void setItem(ArmorSlot slot, ItemStack item) {
        armorItems.put(slot, SerializableItemStack.fromItem(item));
    }

    public void equip(LivingEntity entity) {
        if (entity instanceof Player) {
            Players.setArmor((Player) entity, this);
        } else {
            Entities.setEquipment(entity, this);
        }
    }

    public ItemStack getMainHand() {
        return armorItems.get(ArmorSlot.MAIN_HAND).getItemStack();
    }

    public ItemStack getOffHand() {
        return armorItems.get(ArmorSlot.OFF_HAND).getItemStack();
    }

    public ItemStack getHelmet() {
        return armorItems.get(ArmorSlot.HELMET).getItemStack();
    }

    public ItemStack getBoots() {
        return armorItems.get(ArmorSlot.BOOTS).getItemStack();
    }

    public ItemStack getLegs() {
        return armorItems.get(ArmorSlot.LEGGINGS).getItemStack();
    }

    public ItemStack getChest() {
        return armorItems.get(ArmorSlot.CHEST).getItemStack();
    }

    public Map<ArmorSlot, ItemStack> getArmor() {
        Map<ArmorSlot, ItemStack> armor = new HashMap<>();

        for (Map.Entry<ArmorSlot, SerializableItemStack> item : armorItems.entrySet()) {
            armor.put(item.getKey(), item.getValue().getItemStack());
        }

        return armor;
    }
}
