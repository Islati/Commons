package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearInventoryCommand {
    @Command(identifier = "ci", permissions = {Perms.CLEAR_INVENTORY})
    public void onClearInventoryCommand(CommandSender commandSender, @Arg(name = "target", def = "?sender") String playerName) {
        Player player = null;

        if (playerName != null) {
            //If the player doesn't have the permission to clear the inventory of someone else, then they can't clear it!
            if (!commandSender.hasPermission(Perms.CLEAR_INVENTORY_OTHER)) {
                Chat.message(commandSender, Messages.permissionRequired(Perms.CLEAR_INVENTORY_OTHER));
                return;
            }

            //Check if there's a player online with the name in our argument
            if (Players.isOnline(playerName)) {
                //Assign the player to clear the inventory of
                player = Players.getPlayer(playerName);
            } else {
                Chat.message(commandSender, Messages.playerOffline(playerName));
                return;
            }
        }

        //If the command-sender isn't a player, we're not clearing the inventory or someone else and the arguments are less than 1,
        //then they's not doing it right.
        if (player == null && !(commandSender instanceof Player)) {
            Chat.message(commandSender, Messages.invalidCommandUsage("name"));
            return;
        }

        player = (Player) commandSender;
        //Clear the players inventory through the Players Class
        Players.clearInventory(player, true);
        //Send them a message saying their inventory was cleared
        Chat.message(player, Messages.INVENTORY_CLEARED);
    }
}
