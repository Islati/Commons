package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.world.Worlds;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class NightCommand {
	@Command(identifier = "night", permissions = Perms.COMMAND_TIME)
	public void onNightCommand(Player player, @Arg(name = "world", def = "?sender") World world) {
		Worlds.setTimeNight(world);
		Chat.message(player, Messages.timeUpdated(world.getName(), "night"));
	}
}
