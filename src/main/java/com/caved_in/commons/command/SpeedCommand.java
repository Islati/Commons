package com.caved_in.commons.command;

import com.caved_in.commons.Messages;
import com.caved_in.commons.player.PlayerWrapper;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:10 PM
 */
public class SpeedCommand {
	@CommandController.CommandHandler(name = "speed", permission = "tunnels.common.speed")
	public void onSpeedCommand(Player player, String[] args) {
		PlayerWrapper playerWrapper = Players.getData(player);
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
					double fSpeed = (speed + (PlayerWrapper.defaultWalkSpeed * 10)) / 10;
					playerWrapper.setFlySpeed(fSpeed);
					Players.sendMessage(player, "" + player.getFlySpeed());

				} else {
					double fSpeed = (speed + (PlayerWrapper.defaultWalkSpeed * 10)) / 10;
					playerWrapper.setWalkSpeed(fSpeed);
					Players.sendMessage(player, "" + player.getWalkSpeed());
				}

				//Send the player a message saying their speed was updated
				Players.sendMessage(player, Messages.SPEED_UPDATED(player.isFlying(), Integer.parseInt(speedArg)));

			} else {
				Players.sendMessage(player, Messages.INVALID_COMMAND_USAGE("speed"));
			}
		} else {
			//They didn't pass a speed argument, so reset their speeds to default
			if (player.isFlying()) {
				//Default fly speed
				playerWrapper.setFlySpeed(PlayerWrapper.defaultFlySpeed);
			} else {
				//Default walk speed
				playerWrapper.setWalkSpeed(PlayerWrapper.defaultWalkSpeed);
			}
			Players.sendMessage(player, Messages.SPEED_RESET(player.isFlying()));
		}
	}
}
