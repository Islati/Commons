package com.devsteady.onyx.item;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemCycler {
    private List<ItemStack> items = new ArrayList<>();

    private int currentIndex = 0;

    public ItemCycler(ItemStack... items) {
        for (ItemStack i : items) {
            this.items.add(i);
        }
    }

    public void cycle() {
        /*
        Dont succeed the length of the array,
        that could cause a few problems.
         */
        if (currentIndex >= items.size()) {
            currentIndex = 0;
            return;
        }

        currentIndex += 1;
    }

    public ItemStack activeItem() {
        return items.get(currentIndex);
    }

    public ItemStack nextItem() {
        cycle();
        return activeItem();
    }
}
