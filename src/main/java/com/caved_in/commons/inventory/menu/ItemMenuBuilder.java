package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.inventory.menu.Menus;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemMenuBuilder {

    /*
    The title of the menus we're wanting to create
    */
    private String title = null;

    /*
     The Items which are on the menus.
     */
    private Map<Integer, MenuItem> items = Maps.newHashMap();

    /*
    Whether or not to exit when you click outside the menus.
     */
    private boolean exitOnClickOutside = false;

    /*
    If there's no items, it'll default to building a single row.
     */
    private int rows = 1;

    /*
    Map of all the menus behaviour types and the actions going to be executed.
     */
    private Map<MenuAction, ArrayList<MenuBehaviour>> menuActions = new HashMap<>();

    public ItemMenuBuilder() {
        menuActions.put(MenuAction.OPEN, Lists.newArrayList());
        menuActions.put(MenuAction.CLOSE, Lists.newArrayList());
    }

    public ItemMenuBuilder title(String title) {
        this.title = title;
        return this;
    }

    public ItemMenuBuilder exitOnClickOutside(boolean val) {
        exitOnClickOutside = val;
        return this;
    }

    public ItemMenuBuilder item(MenuItem item) {
        int freeSlot = Menus.getFirstFreeSlot(items);
        items.put(freeSlot, item);
        return this;
    }

    public ItemMenuBuilder item(int index, MenuItem item) {
        items.put(index, item);
        return this;
    }

    public ItemMenuBuilder rows(int count) {
        this.rows = count;
        return this;
    }

    public ItemMenuBuilder addBehaviour(MenuAction type, MenuBehaviour action) {
        menuActions.get(type).add(action);
        return this;
    }

    public ItemMenuBuilder onMenuOpen(MenuBehaviour action) {
        addBehaviour(MenuAction.OPEN, action);
        return this;
    }

    public ItemMenuBuilder onMenuClose(MenuBehaviour action) {
        addBehaviour(MenuAction.CLOSE, action);
        return this;
    }

    public ItemMenu getMenu() {
        Validate.notNull(title);

        /*
        Retrieve the rows required to fit the furthest firstPageEnabled
        on the menus; Thus everything falls before that firstPageEnabled and
        fits.
         */
        int rowCount = 0;

        if (items.isEmpty()) {
            rowCount = this.rows;
        } else {
            rowCount = Menus.getRowsForIndex(Menus.getHighestIndex(items));
        }

        ItemMenu menu = new ItemMenu(title, rowCount);
        menu.setBehaviours(menuActions);
        if (!items.isEmpty()) {
            menu.setMenuItems(items);
        }
        return menu;
    }

}
