package com.devsteady.onyx.inventory.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.material.MaterialData;

public class InlineMenuItem extends MenuItem {
    private MenuItemClickHandler handler;

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

    public MenuItemClickHandler getHandler() {
        return handler;
    }

    public InlineMenuItem setClickHandler(MenuItemClickHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public void onClick(Player player, ClickType type) {
        handler.onClick(this,player,type);
    }
}
