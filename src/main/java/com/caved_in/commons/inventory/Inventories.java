package com.caved_in.commons.inventory;

import com.caved_in.commons.block.ChestType;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.nms.NMS;
import com.caved_in.commons.utilities.ArrayUtils;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class Inventories {

    /**
     * Get the type of chest that the inventory holder is.
     *
     * @param inventoryHolder holder to get the chest type for.
     * @return {@link com.caved_in.commons.block.ChestType} of the given inventory holder. Unknown if it's not a chest.
     */
    public static ChestType getChestType(InventoryHolder inventoryHolder) {
        if (isChest(inventoryHolder)) {
            return ChestType.SINGLE_CHEST;
        } else if (isDoubleChest(inventoryHolder)) {
            return ChestType.DOUBLE_CHEST;
        } else if (inventoryHolder.getInventory().getType() == InventoryType.ENDER_CHEST) {
            return ChestType.ENDER_CHEST;
        } else {
            return ChestType.UNKNOWN;
        }
    }

    /**
     * Check if the type of an inventory is a chest
     *
     * @param inventory inventory to check
     * @return true if the inventories type is a chest
     */
    public static boolean isChest(Inventory inventory) {
        return isChest(inventory.getHolder());
    }

    /**
     * Check if the inventoryholder is a chest
     *
     * @param inventoryHolder inventoryholder to check
     * @return true if the inventorys holder is a chest
     */
    public static boolean isChest(InventoryHolder inventoryHolder) {
        ChestType chestType = getChestType(inventoryHolder);
        switch (chestType) {
            case SINGLE_CHEST:
            case DOUBLE_CHEST:
            case ENDER_CHEST:
                return true;
            default:
                return false;
        }
    }

    /**
     * Check whether or not the inventory holder is a single chest.
     *
     * @param inventoryHolder holder to check
     * @return true if the inventory-holder is a single chest, false for anything else.
     */
    public static boolean isSingleChest(InventoryHolder inventoryHolder) {
        return getChestType(inventoryHolder) == ChestType.SINGLE_CHEST;
    }

    /**
     * Check if the inventoryholder is a double chest
     *
     * @param inventoryHolder inventoryholder to check
     * @return true if the inventorys holder is a double chest
     */
    public static boolean isDoubleChest(InventoryHolder inventoryHolder) {
        return getChestType(inventoryHolder) == ChestType.DOUBLE_CHEST;
    }

    /**
     * Gets the chest block related to the passed inventory holder
     *
     * @param inventoryHolder inventoryholder to get the chest of
     * @return
     */
    public static Chest getChest(InventoryHolder inventoryHolder) {
        return (Chest) inventoryHolder;
    }

    public static DoubleChest getDoubleChest(InventoryHolder inventoryHolder) {
        return (DoubleChest) inventoryHolder;
    }

    /**
     * Get the contents of the inventory.
     *
     * @param inventory inventory to get the contents for.
     * @return list of all the items in the inventory.
     */
    @Nullable
    public static List<ItemStack> getContents(Inventory inventory) {
        return Arrays.asList(inventory.getContents());
    }

    /**
     * Check whether or not the inventory is empty.
     *
     * @param inventory inventory to check
     * @return true if there's no items in the inventory, false otherwise.
     */
    public static boolean isEmpty(Inventory inventory) {
        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check whether or not the inventory is absolutely full.
     *
     * @param inv inventory to check.
     * @return true if every slot in the inventory has an item in it. False Otherwise.
     */
    public static boolean isFull(Inventory inv) {
        return inv.firstEmpty() == -1;
    }

    /**
     * Set the contents of the given inventory.
     *
     * @param inventory inventory to set the contents for
     * @param items     contents to set for the inventory.
     */
    public static void setContents(Inventory inventory, ItemStack[] items) {
        inventory.setContents(items);
    }

    /**
     * Set the item at a specific slot for the given inventory.
     *
     * @param inventory inventory to modify.
     * @param slot      slot to modify in the inventory.
     * @param item      item to assign in the given slot.
     */
    public static void setItem(Inventory inventory, int slot, ItemStack item) {
        inventory.setItem(slot, item);
    }

    /**
     * Open a workbench interface for the player.
     *
     * @param player player to open the workbench view for.
     * @return the workbench inventory view that was opened for the player.
     */
    public static InventoryView openWorkbench(Player player) {
        return player.openWorkbench(null, true);
    }

    /**
     * Set the item at a specific slot in the inventory view.
     * DOES NOT PERFORM AN UPDATE FOR EVERYONE CURRENTLY LOOKING.
     *
     * @param inventoryView inventory view to modify
     * @param itemSlot      slot to set the item at
     * @param itemStack     item to set in the chosen slot
     */
    public static void setViewItemAtSlot(InventoryView inventoryView, int itemSlot, ItemStack itemStack) {
        inventoryView.setItem(itemSlot, itemStack);
    }

    /**
     * Change the items in an inventory view, will overwrite if any item already exists in the slot indicated by the map key.
     * DOES NOT UPDATE THE VIEW FOR THOSE LOOKING.
     *
     * @param inventoryView  view to modify the items of
     * @param inventoryItems map of index -> item entries to set the view to.
     */
    public static void setViewItems(InventoryView inventoryView, Map<Integer, ItemStack> inventoryItems) {
        for (Map.Entry<Integer, ItemStack> itemEntry : inventoryItems.entrySet()) {
            inventoryView.setItem(itemEntry.getKey(), itemEntry.getValue());
        }
    }

    /**
     * Check whether or not the inventory contains a named material.
     *
     * @param inventory inventory to search
     * @param material  material to search for
     * @param itemName  name to match against
     * @return true if the item is in the inventory, false otherwise.
     */
    public static boolean contains(Inventory inventory, Material material, String itemName) {
        return getSlotOf(inventory, material, itemName) != -1;
    }

    /**
     * Get the slot which the named material sits in.
     *
     * @param inventory inventory to search
     * @param material  material to search for
     * @param itemName  name of the material to match
     * @return slot of the named-item if it exists in the inventory, -1 otherwise.
     */
    public static Integer getSlotOf(Inventory inventory, Material material, String itemName) {
        HashMap<Integer, ? extends ItemStack> items = inventory.all(material);
        for (Map.Entry<Integer, ? extends ItemStack> inventoryItem : items.entrySet()) {
            if (Items.nameContains(inventoryItem.getValue(), itemName)) {
                return inventoryItem.getKey();
            }
        }
        return -1;
    }

    /**
     * Get the slot of the given item.
     *
     * @param inventory  inventory to search
     * @param searchItem item to search for
     * @return slot of the item if the inventory contains it, -1 otherwise.
     */
    public static Integer getSlotOf(Inventory inventory, ItemStack searchItem) {
        Map<Integer, ? extends ItemStack> items = inventory.all(searchItem);
        for (Map.Entry<Integer, ? extends ItemStack> item : items.entrySet()) {
            if (item.getValue().isSimilar(searchItem)) {
                return item.getKey();
            }
        }
        return -1;
    }

    /**
     * Check whether or not an inventory contains more than one stack of a specific item in the inventory.
     *
     * @param inv        inventory to search through.
     * @param searchItem item to search for.
     * @return true if there's more than one slot containing the item, false otherwise.
     */
    public static boolean hasMultipleStacks(Inventory inv, ItemStack searchItem) {
        return getSlotsOf(inv, searchItem).size() > 1;
    }

    /**
     * Retrieve all the slots and associated amount of a specific item inside the inventory.
     *
     * @param inv        inventory of which to search.
     * @param searchItem item to search for in the inventory.
     * @return A HashMap with the Slot (Integer) as a key, and Value being the count of items in that slot.
     */
    public static Map<Integer, Integer> getSlotsCount(Inventory inv, ItemStack searchItem) {
        Map<Integer, Integer> slotsCount = new HashMap<>();

        Map<Integer, ? extends ItemStack> items = inv.all(searchItem);

        for (Map.Entry<Integer, ? extends ItemStack> item : items.entrySet()) {
            if (item.getValue().isSimilar(searchItem)) {
                slotsCount.put(item.getKey(), item.getValue().getAmount());
            }
        }

        return slotsCount;
    }

    /**
     * Retrieve all the slots in which a specific item resides. If any.
     *
     * @param inventory  Inventory to search for the item in.
     * @param searchItem item to search for inside the inventory
     * @return A HashMap of Integers, each of which being a slot that the searched item resides in.
     */
    public static Set<Integer> getSlotsOf(Inventory inventory, ItemStack searchItem) {
        Set<Integer> itemSlots = new HashSet<>();

        Map<Integer, ? extends ItemStack> items = inventory.all(searchItem);
        for (Map.Entry<Integer, ? extends ItemStack> item : items.entrySet()) {
            if (item.getValue().isSimilar(searchItem)) {
                itemSlots.add(item.getKey());
            }
        }

        return itemSlots;
    }

    /**
     * Check if the inventory contains any of the given item.
     *
     * @param inventory inventory to search
     * @param itemStack itemstack to check for
     * @return true if the inventory has any of the item, false otherwise.
     */
    public static boolean contains(Inventory inventory, ItemStack itemStack) {
        return getSlotOf(inventory, itemStack) != -1;
    }

    /**
     * Shuffle the contents of the inventory, moving all items to random slots.
     * 12
     *
     * @param inventory inventory to shuffle.
     */
    public static void shuffleContents(Inventory inventory) {
        ItemStack[] contents = inventory.getContents();
        ArrayUtils.shuffleArray(contents);
        for (int i = 0; i < contents.length; i++) {
            inventory.setItem(i, contents[i]);
        }
    }

    /**
     * Clear the slot in the given inventory.
     *
     * @param inventory inventory to modify.
     * @param slot      slot to clear
     */
    public static void clearSlot(Inventory inventory, int slot) {
        inventory.setItem(slot, null);
    }

    /**
     * Check whether or not the inventory contains the given material.
     *
     * @param inventory inventory to search
     * @param material  material to check for
     * @return true if the inventory contains the material, false otherwise.
     */
    public static boolean contains(Inventory inventory, Material material) {
        return inventory.contains(material);
    }

    /**
     * Get the amount of rows, for the count of items given.
     * There's 9 slots per row, and a total of 9 rows total available:
     * 8 would return 1, 11 returns 2, 21 returns 3, so on so forth.
     *
     * @param count amount of items to retrieve row count for.
     * @return count of rows required for the count of items passed.
     */
    public static int getRows(int count) {
        return ((int) Math.ceil(count / 9.0D));
    }

    /**
     * Create a custom inventory.
     *
     * @param title title to give the inventory (color codes supported with & prefix)
     * @param rows  rows to give the inventory
     * @return an inventory with the title, and rows.
     */
    public static Inventory makeInventory(String title, int rows) {
        return Bukkit.getServer().createInventory(null, rows * 9, StringUtil.formatColorCodes(title));
    }

    /**
     * Retrieve the first slot that the given item sits in.
     *
     * @param inv  inventory to search
     * @param item item to search for
     * @return the first slot the item resides in if available, -1 otherwise.
     */
    public static int getFirst(Inventory inv, ItemStack item) {
        Map<Integer, ? extends ItemStack> matchingItems = inv.all(item.getType());

        for (Map.Entry<Integer, ? extends ItemStack> itemEnty : matchingItems.entrySet()) {
            if (itemEnty.getValue().isSimilar(item)) {
                return itemEnty.getKey();
            }
        }

        return -1;
    }

    /**
     * Retrieve the first slot that the given material is found in.
     *
     * @param inv inventory to search
     * @param mat material to search for
     * @return the slot of the searched material if it's available, -1 if not.
     */
    public static int getFirst(Inventory inv, Material mat) {
        Map<Integer, ? extends ItemStack> matchingItems = inv.all(mat);

        for (Map.Entry<Integer, ? extends ItemStack> itemEnty : matchingItems.entrySet()) {
            if (itemEnty.getValue().getType() == mat) {
                return itemEnty.getKey();
            }
        }

        return -1;
    }

    /**
     * Retrieve how many of the given material are in the inventory.
     *
     * @param inv inventory to check
     * @param mat material to search for
     * @return amount of items with the given material in the inventory.
     */
    public static int getCount(Inventory inv, Material mat) {
        Map<Integer, ? extends ItemStack> matchingItems = inv.all(mat);

        int count = 0;

        for (Map.Entry<Integer, ? extends ItemStack> itemEntry : matchingItems.entrySet()) {
            ItemStack item = itemEntry.getValue();
            if (item.getType() != mat) {
                continue;
            }

            count += item.getAmount();
        }

        return count;
    }

    /**
     * Retrieve how many of the given item resides within the inventory.
     *
     * @param inv  inventory to check
     * @param item item to search for within the inventory
     * @return amount of the searched items in the given inventory.
     */
    public static int getCount(Inventory inv, ItemStack item) {
        Map<Integer, ? extends ItemStack> matchingItems = inv.all(item);

        int count = 0;

        for (Map.Entry<Integer, ? extends ItemStack> itemEntry : matchingItems.entrySet()) {
            ItemStack invItem = itemEntry.getValue();
            if (!item.isSimilar(invItem)) {
                continue;
            }

            count += invItem.getAmount();
        }

        return count;
    }

    /**
     * Force the change of an inventories name through the power
     * of NMS and a version of {@link com.caved_in.commons.nms.InventoryHandler}
     *
     * @param inv   inventory to change
     * @param title new title to assign the inventory
     */
    public static void rename(Inventory inv, String title) {
        NMS.getInventoryHandler().changeTitle(inv, title);
    }
}
