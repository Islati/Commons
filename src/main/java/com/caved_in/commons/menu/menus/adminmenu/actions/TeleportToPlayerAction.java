package com.caved_in.commons.menu.menus.adminmenu.actions;

import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TeleportToPlayerAction implements AdminAction {
    @Override
    public void perform(Player admin, Player target) {
        Players.teleport(admin, target);
    }
}
