package com.caved_in.commons.inventory.menu;

import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * Default menu items to help your development adventure.
 */
public class MenuItems {

    public static final MenuItem DEFAULT_GRAY_WINDOW_PAIN = new MenuItem(ItemBuilder.of(Material.GRAY_STAINED_GLASS_PANE).item()) {
        @Override
        public void onClick(Player player, ClickType type) {
        }
    };
}
