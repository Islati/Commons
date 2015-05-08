package com.caved_in.commons.menu.menus.adminmenu.actions;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class BringPlayerAction implements AdminAction {
    @Override
    public void perform(Player admin, Player target) {
        Players.teleport(target, admin);
    }
}
