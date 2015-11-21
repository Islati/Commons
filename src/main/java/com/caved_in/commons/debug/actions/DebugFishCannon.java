package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.debug.gadget.FishCannon;
import com.caved_in.commons.player.Players;
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
