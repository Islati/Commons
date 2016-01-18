package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
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
