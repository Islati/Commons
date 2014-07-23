package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class SpeedCommand {
	@Command(name = "speed", permission = "commons.command.speed")
	public void onSpeedCommand(Player player, String[] args) {
		MinecraftPlayer minecraftPlayer = Players.getData(player);
		if (args.length > 0) {
			String speedArg = args[0];
			if (StringUtils.isNumeric(speedArg)) {
				//Get the speed from whatever the player passed as an argument
				double speed = Integer.parseInt(speedArg);
				//Assure the number isn't >8 or <0.1
				if (speed >= 7.8) {
					speed = 7.8;
				} else if (speed <= 1) {
					speed = 1;
				}
				//If they're flying, set their fly speed; if not, their walk speed
				if (player.isFlying()) {
					double fSpeed = (speed + (MinecraftPlayer.defaultWalkSpeed * 10)) / 10;
					minecraftPlayer.setFlySpeed(fSpeed);
					Players.sendMessage(player, "" + player.getFlySpeed());

				} else {
					double fSpeed = (speed + (MinecraftPlayer.defaultWalkSpeed * 10)) / 10;
					minecraftPlayer.setWalkSpeed(fSpeed);
					Players.sendMessage(player, "" + player.getWalkSpeed());
				}

				//Send the player a message saying their speed was updated
				Players.sendMessage(player, Messages.playerSpeedUpdated(player.isFlying(), Integer.parseInt(speedArg)));

			} else {
				Players.sendMessage(player, Messages.invalidCommandUsage("speed"));
			}
		} else {
			//They didn't pass a speed argument, so reset their speeds to default
			if (player.isFlying()) {
				//Default fly speed
				minecraftPlayer.setFlySpeed(MinecraftPlayer.defaultFlySpeed);
			} else {
				//Default walk speed
				minecraftPlayer.setWalkSpeed(MinecraftPlayer.defaultWalkSpeed);
			}
			Players.sendMessage(player, Messages.playerSpeedReset(player.isFlying()));
		}
	}
}
