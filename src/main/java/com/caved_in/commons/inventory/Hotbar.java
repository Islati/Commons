package com.caved_in.commons.inventory;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.HashMap;
import java.util.Map;

@Root(name = "Hotbar")
public class Hotbar {
    @ElementMap(name = "items", entry = "item", value = "data", key = "slot", keyType = Integer.class, valueType = SerializableItemStack.class, attribute = true)
    private Map<Integer, SerializableItemStack> hotbarItems = new HashMap<>();

    public Hotbar(@ElementMap(name = "items", entry = "item", value = "data", key = "slot", keyType = Integer.class, valueType = SerializableItemStack.class, attribute = true) Map<Integer, SerializableItemStack> items) {
        this.hotbarItems = items;
    }

    public Hotbar(ItemStack... items) {
        for (int i = 0; i < items.length; i++) {
            if (i >= 9) {
                break;
            }

            hotbarItems.put(i, SerializableItemStack.fromItem(items[i]));
        }
    }

    /**
     * Assign a slot (1 to 9) an item.
     *
     * @param slot slot to set the item in (1 to 9)
     * @param item item to set in the slot.
     * @return the hotbar instance.
     */
    public Hotbar set(int slot, ItemStack item) {
        if (slot >= 9) {
            slot = 8;
        }

        hotbarItems.put(slot, SerializableItemStack.fromItem(item));
        return this;
    }

    /**
     * Assign the players hotbar the contents of this hotbar.
     *
     * @param player player to change the hotbar contents of
     */
    public void assign(Player player) {
        for (Map.Entry<Integer, SerializableItemStack> hotbarEntry : hotbarItems.entrySet()) {
            Players.setItem(player, hotbarEntry.getKey(), hotbarEntry.getValue().getItemStack());
        }
    }

    /**
     * @return an array of all the items in the hotbar.
     */
    public ItemStack[] getItems() {
        ItemStack[] items = new ItemStack[8];

        for (Map.Entry<Integer, SerializableItemStack> hotbarEntry : hotbarItems.entrySet()) {
            int index = hotbarEntry.getKey();
            if (index > items.length) {
                continue;
            }

            items[index] = hotbarEntry.getValue().getItemStack();
        }
        return items;
    }
}
