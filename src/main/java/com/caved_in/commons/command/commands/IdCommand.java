package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.exceptions.InvalidMaterialNameException;
import com.caved_in.commons.inventory.HandSlot;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IdCommand {
    @Command(identifier = "id", permissions = Perms.COMMAND_ID)
    @Flags(identifier = "o")
    public void onIdCommand(Player player, @Arg(name = "item", def = "0") String item, @FlagArg("o")final boolean offHand) {
        if (item == null || "0".equals(item)) {
            if (!Players.hasItemInHand(player)) {
                Chat.message(player, "&eEither &ogive an item name as an argument, &e&lor&r&e &ohold an item in either of your hands&r&e.");
                return;
            }

            ItemStack itemStack = Players.getItemInHand(player, Players.handIsEmpty(player, HandSlot.MAIN_HAND) ? HandSlot.OFF_HAND : HandSlot.MAIN_HAND);

            Chat.message(player, Messages.itemId(itemStack));
            return;
        }

        Material material = null;

        try {
            material = Items.getMaterialByName(item);
            Chat.message(player, Messages.itemId(item, material));
        } catch (InvalidMaterialNameException e) {
            Chat.message(player, Messages.invalidItem(item));
        }
    }
}
