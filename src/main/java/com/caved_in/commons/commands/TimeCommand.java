package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.world.WorldHandler;
import com.caved_in.commons.world.WorldTime;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:10 PM
 */
public class TimeCommand {
	@CommandController.CommandHandler(name = "time", permission = "tunnels.common.time")
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
					if (WorldHandler.worldExists(worldName)) {
						world = WorldHandler.getWorld(worldName);
					} else {
						//Send a message saying the world requested doesn't exist
						PlayerHandler.sendMessage(sender, Messages.WORLD_DOESNT_EXIST(worldName));
						return;
					}
				} else {
					//They didnt include a world argument
					PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("time", "world"));
					return;
				}
			}
			timeAction = timeAction.toLowerCase();
			//Switch on what the player entered
			switch (timeAction) {
				case "day":
				case "night":
				case "dawn":
					WorldHandler.setTime(world, WorldTime.getWorldTime(timeAction));
					PlayerHandler.sendMessage(sender, Messages.TIME_CHANGED(world.getName(), timeAction));
					break;
				default:
					PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("Time [day/night/dawn]"));
					break;
			}

		} else {
			PlayerHandler.sendMessage(sender, Messages.INVALID_COMMAND_USAGE("Time [day/night/dawn]"));
		}
	}
}
