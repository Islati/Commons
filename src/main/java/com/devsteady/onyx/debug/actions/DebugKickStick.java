package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.debug.gadget.KickStick;
import org.bukkit.entity.Player;

public class DebugKickStick implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        KickStick.getInstance().giveTo(player);
        Chat.message(player, "&aKick Stick is registered with ID: &e" + KickStick.getInstance().id());
    }

    @Override
    public String getActionName() {
        return "kick_stick";
    }
}
