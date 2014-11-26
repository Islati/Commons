package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FlyCommand {

	@Command(identifier = "fly", permissions = {Perms.COMMAND_FLY})
	public void flyCommand(Player player, @Arg(name = "player", def = "?sender") Player target) {
		target.setAllowFlight(!target.getAllowFlight());
		target.sendMessage(ChatColor.GREEN + "You are " + (player.getAllowFlight() ? "now in fly mode" : "no longer in fly mode"));
	}
}
