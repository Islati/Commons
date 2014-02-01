package com.caved_in.commons.commands;

import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.world.WorldHandler;
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
		PlayerHandler.teleport(player, WorldHandler.getSpawn(player.getWorld()));
	}
}
