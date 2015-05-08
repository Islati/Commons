package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.exceptions.InvalidMaterialNameException;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class IdCommand {
    @Command(identifier = "id", permissions = Perms.COMMAND_ID)
    public void onIdCommand(Player player, @Arg(name = "item", def = "0") String item) {
        if (item == null || "0".equals(item)) {
            if (!Players.hasItemInHand(player)) {
                Chat.message(player, "&eEither &ogive an item name as an argument, &e&lor&r&e &ohold an item in your hand&r&e.");
                return;
            }

            Chat.message(player, Messages.itemId(player.getItemInHand()));
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
