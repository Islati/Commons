package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.Worlds;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 6:52 PM
 */
public class SpawnCommand {
	@Command(name = "spawn", permission = "common.command.spawn")
	public void onSpawnCommand(Player player, String[] args) {
		//Teleport the player to spawn
		Players.teleport(player, Worlds.getSpawn(player.getWorld()));
	}
}
