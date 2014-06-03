package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import com.caved_in.commons.fireworks.Fireworks;
import org.bukkit.entity.Player;

public class FireworksCommand {
	@Command(name = "fw", usage = "/fw", permission = "commons.command.fireworks")
	public void onFireworksCommand(Player player, String[] args) {
		Fireworks.playRandomFirework(player.getEyeLocation());
	}
}
