package com.caved_in.commons.config;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.HashMap;
import java.util.Map;

/**
 * Serializable wrapper for inventories.
 */
@Root(name = "inventory")
public class SerialiizableInventory {
    @ElementMap(name = "items", key = "slot", value = "item", valueType = SerializableItemStack.class)
    private Map<Integer, SerializableItemStack> inventoryItems = new HashMap<>();

    public SerialiizableInventory(@ElementMap(name = "items", key = "slot", value = "item", valueType = SerializableItemStack.class) Map<Integer, SerializableItemStack> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public SerialiizableInventory(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) {
                continue;
            }

            inventoryItems.put(i, SerializableItemStack.fromItem(item));
        }
    }

    public Map<Integer, ItemStack> getInventoryContents() {
        Map<Integer, ItemStack> inventoryContents = new HashMap<>();
        inventoryItems.entrySet().forEach(e -> inventoryContents.put(e.getKey(), e.getValue().getItemStack()));
        return inventoryContents;
    }
}
