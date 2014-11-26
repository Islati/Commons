package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.fireworks.Fireworks;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.threading.RunnableManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FireworksCommand {

	@Command(identifier = "fw", permissions = {Perms.COMMAND_FIREWORKS})
	@Flags(identifier = {"a", "d"}, description = {"Amount of fireworks to launch", "Delay between each fireworks explosion"})
	public void fireworksCommand(Player player, @FlagArg("a") @Arg(name = "amount", def = "1") int amt, @FlagArg("d") @Arg(name = "delay", def = "10") int delay) {
		Location loc = player.getLocation();
		RunnableManager manager = Commons.getInstance().getThreadManager();
		for (int i = 0; i < amt; i++) {
			manager.runTaskLater(() -> Fireworks.playRandomFirework(loc), i * delay);
		}
	}
}
