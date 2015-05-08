package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class RepairCommand {
    @Command(identifier = "repair", permissions = Perms.COMMAND_REPAIR)
    @Flags(identifier = "a", description = "Repair all your items, not just the one in your hand")
    public void onItemRepairCommand(Player player, @FlagArg("a") final boolean all) {
        if (all) {
            Players.repairItems(player, true);
        }

        if (!all) {
            if (Players.handIsEmpty(player)) {
                Chat.message(player, Messages.ITEM_IN_HAND_REQUIRED);
                return;
            }

            Items.repairItem(player.getItemInHand());
        }

        Chat.message(player, Messages.ITEMS_REPAIRED);
    }
}
