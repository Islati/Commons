package com.caved_in.commons.shop;

import java.util.HashMap;
import java.util.Map;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class Shop {
    private static Map<Integer, StoreItem> storeItems = new HashMap<>();

    public static boolean isItem(int id) {
        return storeItems.containsKey(id);
    }

    public static StoreItem getItemById(int id) {
        return storeItems.get(id);
    }

    public static void addItem(StoreItem item) {
        storeItems.put(item.getItemId(), item);
    }
}
