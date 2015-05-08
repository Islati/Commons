package com.caved_in.commons.menu;

import org.bukkit.entity.Player;

public interface MenuOpenBehaviour extends MenuBehaviour {
    @Override
    public void doAction(ItemMenu menu, Player player);
}
