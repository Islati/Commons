package com.caved_in.commons.inventory.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class InlineMenuItem extends MenuItem {
    private MenuItemClickHandler handler = null;

    public InlineMenuItem() {

    }

    public InlineMenuItem(String text) {
        super(text);
    }

    public InlineMenuItem(String text, MaterialData icon) {
        super(text, icon);
    }

    public InlineMenuItem(String text, MaterialData icon, int number) {
        super(text, icon, number);
    }

    public InlineMenuItem(String text, ItemStack item) {
        super(text, item);
    }

    public MenuItemClickHandler getHandler() {
        return handler;
    }

    public void setClickHandler(MenuItemClickHandler handler) {
        this.handler = handler;
    }

    @Override
    public void onClick(Player player, ClickType type) {
        if (handler != null) {
            handler.onClick(this, player, type);
        }
    }
}
