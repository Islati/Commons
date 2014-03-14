package com.caved_in.commons.command;

import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.Worlds;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 6:52 PM
 */
public class SpawnCommand {
	@CommandController.CommandHandler(name = "spawn", permission = "tunnels.common.spawn")
	public void onSpawnCommand(Player player, String[] args) {
		//Teleport the player to spawn
		Players.teleport(player, Worlds.getSpawn(player.getWorld()));
	}
}
