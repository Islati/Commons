package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugHandItem implements DebugAction {

    @Override
    public void doAction(Player player, String... args) {
        if (!Players.hasItemInHand(player)) {
            Chat.message(player, Messages.DEBUG_ACTION_REQUIRES_HAND_ITEM);
            return;
        }

        ItemStack itemStack = player.getItemInHand();
        Chat.message(player, Messages.itemInfo(itemStack));
    }

    @Override
    public String getActionName() {
        return "hand_item_info";
    }
}
