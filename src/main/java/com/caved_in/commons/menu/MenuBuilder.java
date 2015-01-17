package com.caved_in.commons.menu;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuBuilder {

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
    private Map<MenuBehaviourType, ArrayList<MenuBehaviour>> menuActions = new HashMap<>();

    public MenuBuilder() {
        menuActions.put(MenuBehaviourType.OPEN, Lists.newArrayList());
        menuActions.put(MenuBehaviourType.CLOSE, Lists.newArrayList());
    }

    public MenuBuilder title(String title) {
        this.title = title;
        return this;
    }

    public MenuBuilder exitOnClickOutside(boolean val) {
        exitOnClickOutside = val;
        return this;
    }

    public MenuBuilder item(MenuItem item) {
        int freeSlot = Menus.getFirstFreeSlot(items);
        items.put(freeSlot, item);
        return this;
    }

    public MenuBuilder item(int index, MenuItem item) {
        items.put(index, item);
        return this;
    }

    public MenuBuilder addBehaviour(MenuBehaviourType type, MenuBehaviour action) {
        menuActions.get(type).add(action);
        return this;
    }

    public MenuBuilder onMenuOpen(MenuOpenBehaviour action) {
        addBehaviour(MenuBehaviourType.OPEN, action);
        return this;
    }

    public MenuBuilder onMenuClose(MenuCloseBehaviour action) {
        addBehaviour(MenuBehaviourType.CLOSE, action);
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
