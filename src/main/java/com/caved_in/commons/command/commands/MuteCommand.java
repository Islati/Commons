package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import org.bukkit.command.CommandSender;

public class MuteCommand {

    @Command(identifier = "mute", permissions = Perms.COMMAND_MUTE, onlyPlayers = false)
    public void onMuteCommand(CommandSender player, @Arg(name = "player-name") String name) {

    }

}
