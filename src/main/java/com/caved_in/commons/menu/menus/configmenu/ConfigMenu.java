package com.caved_in.commons.menu.menus.configmenu;

import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.menus.configmenu.items.OpenWorldConfigItem;
import com.caved_in.commons.menu.menus.configmenu.items.RegisterCommandsItem;

public class ConfigMenu extends ItemMenu {
    private static ConfigMenu instance;

    protected ConfigMenu() {
        super("Configuration Menu", 1);
        addMenuItem(new RegisterCommandsItem(), 0);
        addMenuItem(new OpenWorldConfigItem(), 1);
    }

    public static ConfigMenu getInstance() {
        if (instance == null) {
            instance = new ConfigMenu();
        }
        return instance;
    }
}
