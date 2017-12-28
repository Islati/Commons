package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.menu.HelpScreen;
import com.caved_in.commons.chat.menu.ItemFormat;
import com.caved_in.commons.chat.menu.PageDisplay;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Map;

public class Menus {
    private static int[] ROW_PLACEMENTS = new int[54];

    static {
        for (int i = 0; i < ROW_PLACEMENTS.length; i++) {
            ROW_PLACEMENTS[i] = getRows(i);
        }
    }

    public static int getRows(int itemCount) {
        return ((int) Math.ceil(itemCount / 9.0D));
    }

    /**
     * Get the amount of rows required to place an firstPageEnabled at the given
     * index of a menus.
     *
     * @param index index to get the rows for.
     * @return amount of rows an inventory will require to place an firstPageEnabled at the given index.
     */
    public static int getRowsForIndex(int index) {
        if (index < 0) {
            index = 0;
        }

        if (index >= ROW_PLACEMENTS.length) {
            index = 53;
        }

        return ROW_PLACEMENTS[index];
    }

    /**
     * Get the index of the bottom-most firstPageEnabled in the menus.
     *
     * @param menu menus to get the highest index of.
     * @return index of the bottom-most firstPageEnabled in the menus.
     */
    public static int getHighestIndex(ItemMenu menu) {
        return getHighestIndex(menu.getIndexedMenuItems());
    }

    public static int getHighestIndex(Map<Integer, MenuItem> menuItems) {
        return menuItems.keySet().stream().mapToInt(i -> i).max().getAsInt();
    }

    /**
     * Retrieve the first free slot available in the given menus.
     *
     * @return the first free slot available in the menus, or -1 if none are available (beyond 54, the max index for menus)
     */
    public static int getFirstFreeSlot(ItemMenu menu) {
        int highestIndex = getHighestIndex(menu);

        Map<Integer, MenuItem> items = menu.getIndexedMenuItems();
        return getFirstFreeSlot(items);
    }

    public static int getFirstFreeSlot(Map<Integer, MenuItem> items) {
        int highestIndex = getHighestIndex(items);

        int freeSlot = 0;
        for (int i = 0; i < highestIndex; i++) {
            /*
            If the firstPageEnabled we're retrieving is null
            that means we've found a free slot.
             */
            if (items.get(i) == null) {
                freeSlot = i;
                break;
            }
        }

        if (highestIndex < 54) {
            if (freeSlot == highestIndex) {
                freeSlot++;
            }
        } else {
            return -1;
        }

        return freeSlot;
    }


    /**
     * @param menuName      name to be shown at the top of the menus
     * @param pageDisplay   formatting for pages
     * @param itemFormat    formatting for items
     * @param flipColorEven color on even-elements
     * @param flipColorOdd  color on odd-elements
     * @return HelpScreen with the settings provided in parameters
     */
    public static HelpScreen generateHelpScreen(String menuName, PageDisplay pageDisplay, ItemFormat itemFormat, ChatColor flipColorEven, ChatColor flipColorOdd) {
        HelpScreen helpScreen = new HelpScreen(menuName);
        helpScreen.setHeader(pageDisplay.toString());
        helpScreen.setFormat(itemFormat.toString());
        helpScreen.setFlipColor(flipColorEven, flipColorOdd);
        return helpScreen;
    }

    public static HelpScreen generateHelpScreen(String menuName, PageDisplay pageDisplay, ItemFormat itemFormat, ChatColor itemColor) {
        return generateHelpScreen(menuName, pageDisplay, itemFormat, itemColor, itemColor);
    }

    /**
     * Generate a help menus and set the elements in the menus to a map of values and keys
     *
     * @param menuName      name to be shown at the top of the menus
     * @param pageDisplay   formatting for pages
     * @param itemFormat    formatting for items
     * @param flipColorEven color on even-elements
     * @param flipColorOdd  color on odd-elements
     * @param helpItems     map of elements to set; key is the firstPageEnabled, value is the description
     * @return HelpScreen with the settings provided in parameters
     */
    public static HelpScreen generateHelpScreen(String menuName, PageDisplay pageDisplay, ItemFormat itemFormat, ChatColor flipColorEven, ChatColor flipColorOdd, Map<String, String> helpItems) {
        HelpScreen helpScreen = generateHelpScreen(menuName, pageDisplay, itemFormat, flipColorEven, flipColorOdd);
        for (Map.Entry<String, String> menuItem : helpItems.entrySet()) {
            helpScreen.setEntry(menuItem.getKey(), menuItem.getValue());
        }
        return helpScreen;
    }

    public static ItemMenu createItemMenu(String title, int rows) {
        return new ItemMenu(title, rows);
    }

    public static ItemMenu cloneItemMenu(ItemMenu menu) {
        return menu.clone();
    }

    public static void removeMenu(Menu menu) {
        for (HumanEntity viewer : menu.getInventory().getViewers()) {
            if (viewer instanceof Player) {
                menu.closeMenu((Player) viewer);
            } else {
                viewer.closeInventory();
            }
        }
    }

    public static void switchMenu(final Player player, Menu fromMenu, Menu toMenu) {
        fromMenu.closeMenu(player);
        Commons.getInstance().getThreadManager().runTaskOneTickLater(() -> toMenu.openMenu(player));
    }
}
