package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.Worlds;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class NightCommand {
	@Command(name = "night", permission = "commons.command.time")
	public void onNightCommand(Player player, String[] args) {
		World world = player.getWorld();
		Worlds.setTimeNight(world);
		Players.sendMessage(player, Messages.timeUpdated(world.getName(), "night"));
	}
}
