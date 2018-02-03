package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import org.bukkit.entity.Player;

public class DebugPlayerDirection implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        Chat.message(player, Messages.playerFacingDirection(player));
    }

    @Override
    public String getActionName() {
        return "player_direction";
    }
}
