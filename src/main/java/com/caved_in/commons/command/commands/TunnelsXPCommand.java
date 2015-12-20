package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import org.bukkit.entity.Player;

public class TunnelsXPCommand {
    //TODO Implement optional toggle for the xp command; Move to premium currency handler.
    @Command(identifier = "xp")
    public void playerXPCommand(Player player) {
        Chat.message(player, Messages.playerXpBalance(player));
    }
}
