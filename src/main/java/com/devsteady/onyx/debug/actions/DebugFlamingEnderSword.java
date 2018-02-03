package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.debug.gadget.FlamingEnderSword;
import com.devsteady.onyx.player.Players;
import org.bukkit.entity.Player;

public class DebugFlamingEnderSword implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        Players.giveItem(player, FlamingEnderSword.getInstance().getItem());
        Chat.message(player,"&aID Of the Flaming Ender Sword: " + FlamingEnderSword.getInstance().id());
    }

    @Override
    public String getActionName() {
        return "ender_sword";
    }
}
