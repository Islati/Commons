package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.debug.gadget.ThrowableBrick;
import com.devsteady.onyx.player.Players;
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
