package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.inventory.HandSlot;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MoreCommand {
    @Command(identifier = "more", permissions = {Perms.COMMAND_MORE})
    @Flags(identifier = {"a"})
    public void onMoreCommand(Player player, @FlagArg("a") final boolean allItems, @FlagArg("m")final boolean mainHand, @FlagArg("o")final boolean offHand) {
        if (allItems) {
            PlayerInventory inventory = player.getInventory();
            for (ItemStack item : inventory.getContents()) {
                if (item == null) {
                    continue;
                }

                item.setAmount(item.getMaxStackSize());
            }
            return;
        }

        if (!Players.hasItemInHand(player)) {
            Chat.message(player, Messages.ITEM_IN_HAND_REQUIRED);
            return;
        }

        // If no hand was specifically noted, then just max out both.
        if (!mainHand && !offHand) {
            if(!Players.handIsEmpty(player,HandSlot.MAIN_HAND)) {
                ItemStack mainHandItem = Players.getItemInHand(player, HandSlot.MAIN_HAND);
                mainHandItem.setAmount(mainHandItem.getMaxStackSize());
                Players.setItemInHand(player,mainHandItem,HandSlot.MAIN_HAND);
            }

            if(!Players.handIsEmpty(player,HandSlot.OFF_HAND)) {
                ItemStack offHandItem = Players.getItemInHand(player, HandSlot.OFF_HAND);
                offHandItem.setAmount(offHandItem.getMaxStackSize());
                Players.setItemInHand(player,offHandItem,HandSlot.OFF_HAND);
            }

            return;
        }

        if (mainHand) {
            if (Players.handIsEmpty(player,HandSlot.MAIN_HAND)) {
                Chat.message(player,Messages.ITEM_IN_HAND_REQUIRED);
                return;
            }

            ItemStack mainHandItem = Players.getItemInHand(player, HandSlot.MAIN_HAND);
            mainHandItem.setAmount(mainHandItem.getMaxStackSize());
            Players.setItemInHand(player,mainHandItem,HandSlot.MAIN_HAND);
        }

        if (offHand) {
            if (Players.handIsEmpty(player,HandSlot.OFF_HAND)) {
                Chat.message(player,Messages.ITEM_IN_HAND_REQUIRED);
                return;
            }

            ItemStack offHandItem = Players.getItemInHand(player, HandSlot.OFF_HAND);
            offHandItem.setAmount(offHandItem.getMaxStackSize());
            Players.setItemInHand(player,offHandItem,HandSlot.OFF_HAND);
        }

        ItemStack playerHandItem = player.getItemInHand();
        playerHandItem.setAmount(64);
        player.setItemInHand(playerHandItem);
    }
}
