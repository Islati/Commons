package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MoreCommand {
    @Command(identifier = "more", permissions = {Perms.COMMAND_MORE})
    public void onMoreCommand(Player player) {
        if (!Players.hasItemInHand(player)) {
            Chat.message(player, Messages.ITEM_IN_HAND_REQUIRED);
            return;
        }

        ItemStack mainHandItem = player.getItemInHand();
        mainHandItem.setAmount(mainHandItem.getMaxStackSize());
        player.setItemInHand(mainHandItem);

    }
}
