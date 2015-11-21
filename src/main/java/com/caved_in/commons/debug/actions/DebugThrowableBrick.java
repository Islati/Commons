package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.debug.gadget.ThrowableBrick;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class DebugThrowableBrick implements DebugAction {

    @Override
    public void doAction(Player player, String... args) {
        Players.giveItem(player, ThrowableBrick.getInstance().getItem());
        Chat.message(player, "&aThrowable Brick is registered under id: &e" + ThrowableBrick.getInstance().id());
    }

    @Override
    public String getActionName() {
        return "brick_throw";
    }
}
