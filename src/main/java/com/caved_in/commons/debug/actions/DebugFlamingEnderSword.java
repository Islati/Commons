package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.debug.gadget.FlamingEnderSword;
import com.caved_in.commons.player.Players;
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
