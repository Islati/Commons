package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class SpeedCommand {
	@Command(identifier = "speed", permissions = {Perms.SPEED_COMMAND})
	public void onSpeedCommand(Player player, @Arg(name = "speed", def = "1", verifiers = "min[1.0]|max[7.8]") double speed) {
		MinecraftPlayer minecraftPlayer = Players.getData(player);
		if (player.isFlying()) {
			double fSpeed = 0;

			if (speed == 1) {
				fSpeed = MinecraftPlayer.DEFAULT_FLY_SPEED;
			} else {
				fSpeed = (speed + (MinecraftPlayer.DEFAULT_FLY_SPEED * 10)) / 10;
			}

			minecraftPlayer.setFlySpeed(fSpeed);
		} else {
			double wSpeed = 0;
			if (speed == 1) {
				wSpeed = MinecraftPlayer.DEFAULT_WALK_SPEED;
			} else {
				wSpeed = (speed + (MinecraftPlayer.DEFAULT_WALK_SPEED * 10)) / 10;
			}
			minecraftPlayer.setWalkSpeed(wSpeed);
		}

		Players.sendMessage(player, Messages.playerSpeedUpdated(player.isFlying(), (int) speed));

	}
}
