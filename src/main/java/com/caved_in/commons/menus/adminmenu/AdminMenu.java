package com.caved_in.commons.menus.adminmenu;

import com.caved_in.commons.inventory.menu.ItemMenu;

@SuppressWarnings("WeakerAccess")
public class AdminMenu extends ItemMenu {
    private static AdminMenu instance;

    public static AdminMenu getInstance() {
        if (instance == null) {
            instance = new AdminMenu();
        }
        return instance;
    }

    protected AdminMenu() {
        super("&4Admin Menu", 1);
    }
}
