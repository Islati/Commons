package com.caved_in.commons.menu.inventory;

import com.caved_in.commons.menu.Menus;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemMenuBuilder {

    /*
    The title of the menu we're wanting to create
    */
    private String title = null;

    /*
     The Items which are on the menu.
     */
    private Map<Integer, MenuItem> items = Maps.newHashMap();

    /*
    Whether or not to exit when you click outside the menu.
     */
    private boolean exitOnClickOutside = false;

    /*
    Map of all the menu behaviour types and the actions going to be executed.
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

    public ItemMenuBuilder addBehaviour(MenuAction type, MenuBehaviour action) {
        menuActions.get(type).add(action);
        return this;
    }

    public ItemMenuBuilder onMenuOpen(MenuOpenBehaviour action) {
        addBehaviour(MenuAction.OPEN, action);
        return this;
    }

    public ItemMenuBuilder onMenuClose(MenuCloseBehaviour action) {
        addBehaviour(MenuAction.CLOSE, action);
        return this;
    }

    public ItemMenu getMenu() {
        Validate.notNull(title);
        Validate.notEmpty(items);

        /*
        Retrieve the rows required to fit the furthest item
        on the menu; Thus everything falls before that item and
        fits.
         */
        int rows = Menus.getRowsForIndex(Menus.getHighestIndex(items));

        ItemMenu menu = new ItemMenu(title, rows);
        menu.setBehaviours(menuActions);
        menu.setMenuItems(items);
        return menu;
    }

}
