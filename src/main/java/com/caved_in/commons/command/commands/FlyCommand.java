package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FlyCommand {
	@Command(name = "fly", usage = "/fly to toggle your fly on and off accordingly", permission = "commons.command.fly")
	public void onFlyCommand(Player player, String[] args) {
		player.setAllowFlight(!player.getAllowFlight());
		player.sendMessage(ChatColor.GREEN + "You are " + (player.getAllowFlight() ? "now in fly mode" : "no longer in fly mode"));
	}
}
