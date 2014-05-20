package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.WorldTime;
import com.caved_in.commons.world.Worlds;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand {
	@Command(name = "time", permission = "commons.command.time")
	public void onTimeCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {
			String timeAction = args[0];
			World world;
			if (sender instanceof Player) {
				world = ((Player) sender).getWorld();
			} else {
				//Not a player sending the message, so check if they passed an argument for a world
				if (args.length > 1) {
					//Get what they passed, and see if the world exists
					String worldName = args[1];
					if (Worlds.exists(worldName)) {
						world = Worlds.getWorld(worldName);
					} else {
						//Send a message saying the world requested doesn't exist
						Players.sendMessage(sender, Messages.invalidWorld(worldName));
						return;
					}
				} else {
					//They didnt include a world argument
					Players.sendMessage(sender, Messages.invalidCommandUsage("time", "world"));
					return;
				}
			}
			timeAction = timeAction.toLowerCase();
			//Switch on what the player entered
			switch (timeAction) {
				case "day":
				case "night":
				case "dawn":
					Worlds.setTime(world, WorldTime.getWorldTime(timeAction));
					Players.sendMessage(sender, Messages.timeUpdated(world.getName(), timeAction));
					break;
				default:
					Players.sendMessage(sender, Messages.invalidCommandUsage("Time [day/night/dawn]"));
					break;
			}

		} else {
			Players.sendMessage(sender, Messages.invalidCommandUsage("Time [day/night/dawn]"));
		}
	}
}
