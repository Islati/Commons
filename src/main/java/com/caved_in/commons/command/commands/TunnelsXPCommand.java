package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TunnelsXPCommand {

    @Command(identifier = "xp")
    public void playerXPCommand(Player player) {
        Chat.message(player, Messages.playerXpBalance(player));
    }
}
