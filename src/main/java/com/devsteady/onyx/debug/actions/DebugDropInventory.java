package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.player.Players;
import org.bukkit.entity.Player;

public class DebugDropInventory implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        Onyx.getInstance().debug("Dropping inventory of " + player.getName());
        Players.dropInventory(player);
    }

    @Override
    public String getActionName() {
        return "drop_inventory";
    }
}
