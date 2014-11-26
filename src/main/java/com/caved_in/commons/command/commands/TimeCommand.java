package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.WorldTime;
import com.caved_in.commons.world.Worlds;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

public class TimeCommand {
	@Command(identifier = "time", permissions = Perms.COMMAND_TIME)
	public void onTimeCommand(CommandSender sender, @Arg(name = "world", def = "?sender") World world, @Arg(name = "time") String time) {
		time = time.toLowerCase();
		//Switch on what the player entered
		switch (time) {
			case "day":
			case "night":
			case "dawn":
				Worlds.setTime(world, WorldTime.getWorldTime(time));
				Players.sendMessage(sender, Messages.timeUpdated(world.getName(), time));
				break;
			default:
				Players.sendMessage(sender, Messages.invalidCommandUsage("Time [day/night/dawn]"));
				break;
		}
	}
}
