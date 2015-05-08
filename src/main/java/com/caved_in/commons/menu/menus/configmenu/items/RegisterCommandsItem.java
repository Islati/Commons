package com.caved_in.commons.menu.menus.configmenu.items;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.item.Wool;
import com.caved_in.commons.menu.MenuItem;
import org.bukkit.entity.Player;

public class RegisterCommandsItem extends MenuItem {

    public RegisterCommandsItem() {
        super();
        init();
    }

    private void init() {
        boolean hasSqlBackend = Commons.getInstance().getConfiguration().hasSqlBackend();
        if (hasSqlBackend) {
            setText("&aRegister Commands");
            setIcon(Wool.GREEN_WOOL);
            setDescriptions("&eClick to disable the Commands for Commons");
        } else {
            setText("&cRegister Commands");
            setIcon(Wool.RED_WOOL);
            setDescriptions("&eClick to enable toe Commands for Commons");
        }
        addDescriptions("&7Requires server restart (or reload) to take effect");
    }

    @Override
    public void onClick(Player player) {
        Configuration config = Commons.getInstance().getConfiguration();
        boolean registerCommands = !config.registerCommands();
        config.setRegisterCommands(registerCommands);
        init();
        getMenu().updateMenu();
    }
}
