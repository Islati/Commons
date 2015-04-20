package com.caved_in.commons.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryItem {
    private int slot;
    private ItemStack item;
    private Inventory inventory;

    public InventoryItem(Inventory inv, ItemStack item, int slot) {
        this.inventory = inv;
        this.item = item;
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
