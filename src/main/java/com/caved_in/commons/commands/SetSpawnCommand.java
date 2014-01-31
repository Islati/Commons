package com.caved_in.commons.commands;

import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.world.WorldHandler;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 6:52 PM
 */
public class SetSpawnCommand {
	@CommandController.CommandHandler(name = "setspawn", usage = "/setspawn", permission = "tunnels.common.setspawn")
	public void setSpawnCommand(Player player, String[] commandArgs) {
		if (WorldHandler.setSpawn(player.getWorld(), player.getLocation())) {
			PlayerHandler.sendMessage(player, "&aSpawn location for the world &7" + player.getWorld().getName() + "&a has been set!");
		} else {
			PlayerHandler.sendMessage(player, "&eThere was an error changing the spawn location for world &7" + player.getWorld().getName() + "&e; please check the console.");
		}
	}
}
