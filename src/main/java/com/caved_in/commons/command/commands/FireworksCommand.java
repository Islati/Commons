package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.fireworks.Fireworks;
import com.caved_in.commons.threading.RunnableManager;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class FireworksCommand {
	@Command(name = "fw", usage = "/fw", permission = "commons.command.fireworks")
	public void onFireworksCommand(Player player, String[] args) {
		Location loc = player.getLocation();

		int fireWorkAmount = 1;

		if (args.length > 0) {
			fireWorkAmount = StringUtil.getNumberAt(args, 0, 1);
		}

		int delayTicks = 10;

		if (args.length > 1) {
			delayTicks = StringUtil.getNumberAt(args, 1, 10);
		}

		RunnableManager manager = Commons.getInstance().getThreadManager();
		for (int i = 0; i < fireWorkAmount; i++) {
			manager.runTaskLater(() -> Fireworks.playRandomFirework(loc), i * delayTicks);
		}
	}
}
