package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.world.WorldHandler;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:11 PM
 */
public class DayCommand {
	@CommandController.CommandHandler(name = "day", permission = "tunnels.common.time")
	public void onDayCommand(Player player, String[] args) {
		World world = player.getWorld();
		WorldHandler.setTimeDay(world);
		PlayerHandler.sendMessage(player, Messages.TIME_CHANGED(world.getName(), "day"));
	}
}
