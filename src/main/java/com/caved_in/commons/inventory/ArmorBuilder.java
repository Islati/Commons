package com.caved_in.commons.inventory;

import com.caved_in.commons.entity.CreatureBuilder;
import com.caved_in.commons.item.Items;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ArmorBuilder {
    private Map<ArmorSlot, ItemStack> armor = new HashMap<>();

    private CreatureBuilder creatureBuilder;

    public ArmorBuilder() {

    }

    public ArmorBuilder(ItemStack[] items) {
        for (int i = 0; i < items.length; i++) {
            ArmorSlot slot = ArmorSlot.getSlot(i);
            ItemStack armorItem = items[i];
            armor.put(slot, armorItem);
        }
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

    public ArmorBuilder withMainHand(ItemStack item) {
        setItem(ArmorSlot.MAIN_HAND, item);
        return this;
    }

    public ArmorBuilder withOffHand(ItemStack item) {
        setItem(ArmorSlot.OFF_HAND,item);
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
        armor.entrySet().forEach(e -> {
            ItemStack item = e.getValue();
            if (item != null) {
                inv.setItem(e.getKey(), item);
            }
        });
        return inv;
    }

    public ArmorBuilder enchantAll(Enchantment enchant, int level) {
        armor.entrySet().forEach(e -> {
            Items.addEnchantment(e.getValue(), enchant, level);
        });

        return this;
    }
}
