package com.caved_in.commons.item;

import org.bukkit.inventory.ItemStack;

public class ItemCycler {
    private ItemStack[] items;

    private int currentIndex = 0;

    public ItemCycler(ItemStack... items) {
        this.items = items;
    }

    public void cycle() {
        /*
        Dont succeed the length of the array,
        that could cause a few problems.
         */
        if (currentIndex >= items.length) {
            currentIndex = 0;
            return;
        }

        currentIndex += 1;
    }

    public ItemStack activeItem() {
        return items[currentIndex];
    }

    public ItemStack nextItem() {
        cycle();
        return activeItem();
    }
}
