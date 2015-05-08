package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class DebugDropInventory implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        Commons.getInstance().debug("Dropping inventory of " + player.getName());
        Players.dropInventory(player);
    }

    @Override
    public String getActionName() {
        return "drop_inventory";
    }
}
