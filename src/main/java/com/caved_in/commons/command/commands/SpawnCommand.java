package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.Worlds;
import org.bukkit.entity.Player;

public class SpawnCommand {
    @Command(identifier = "spawn", permissions = Perms.COMMAND_SPAWN)
    public void onSpawnCommand(Player player) {
        //Teleport the player to spawn
        Players.teleport(player, Worlds.getSpawn(player.getWorld()));
    }
}
