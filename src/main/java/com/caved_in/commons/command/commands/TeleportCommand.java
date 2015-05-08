package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TeleportCommand {
    @Command(identifier = "tp", permissions = Perms.COMMAND_TELEPORT)
    public void onTeleportCommand(Player sender, @Arg(name = "target") Player target) {
        Players.teleport(sender, target);
        Chat.message(sender, Messages.playerTeleportedToPlayer(target.getName()));
    }
}
