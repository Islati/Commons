package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.debug.gadget.KickStick;
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
