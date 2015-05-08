package com.caved_in.commons.command.commands;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.Worlds;
import org.bukkit.entity.Player;


public class SetSpawnCommand {
    @Command(identifier = "setspawn", permissions = {Perms.COMMAND_SETSPAWN})
    public void setSpawnCommand(Player player) {
        if (Worlds.setSpawn(player.getWorld(), player.getLocation())) {
            Chat.message(player, "&aSpawn location for the world &7" + player.getWorld().getName() + "&a has been set!");
        } else {
            Chat.message(player, "&eThere was an error changing the spawn location for world &7" + player.getWorld().getName() + "&e; please check the console.");
        }
    }
}
