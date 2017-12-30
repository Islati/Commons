package com.caved_in.commons.menus.adminmenu.actions;

import org.bukkit.entity.Player;

public interface AdminAction {
    public void perform(Player admin, Player target);
}
