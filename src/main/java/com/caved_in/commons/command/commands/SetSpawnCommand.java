package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.Worlds;
import org.bukkit.entity.Player;


public class SetSpawnCommand {
	@Command(name = "setspawn", usage = "/setspawn", permission = "commons.command.setspawn")
	public void setSpawnCommand(Player player, String[] commandArgs) {
		if (Worlds.setSpawn(player.getWorld(), player.getLocation())) {
			Players.sendMessage(player, "&aSpawn location for the world &7" + player.getWorld().getName() + "&a has been set!");
		} else {
			Players.sendMessage(player, "&eThere was an error changing the spawn location for world &7" + player.getWorld().getName() + "&e; please check the console.");
		}
	}
}
