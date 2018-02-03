package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.player.Players;
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
