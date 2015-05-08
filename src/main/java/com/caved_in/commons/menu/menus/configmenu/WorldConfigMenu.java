package com.caved_in.commons.menu.menus.configmenu;

import com.caved_in.commons.menu.ItemMenu;

public class WorldConfigMenu extends ItemMenu {
    private static WorldConfigMenu instance;

    protected WorldConfigMenu() {
        super("&bWorld Configuration Menu", 3);

    }

    public static WorldConfigMenu getInstance() {
        if (instance == null) {
            instance = new WorldConfigMenu();
        }
        return instance;
    }
}
