package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import org.bukkit.entity.Player;

public class DebugActionMessage implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        Chat.actionMessage(player, "Looks like the NMS Wrapper works!");
    }

    @Override
    public String getActionName() {
        return "action_message";
    }
}
