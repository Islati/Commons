package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.debug.gadget.FishCannon;
import com.devsteady.onyx.player.Players;
import org.bukkit.entity.Player;

public class DebugFishCannon implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        Players.giveItem(player, FishCannon.getInstance().getItem());
        Chat.message(player,"&aFish Cannon has been registered under &e" + FishCannon.getInstance().id());
    }

    @Override
    public String getActionName() {
        return "fish_cannon";
    }
}
