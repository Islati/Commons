package com.caved_in.commons.command;

import com.caved_in.commons.fireworks.FireworkEffectPlayer;
import com.caved_in.commons.fireworks.FireworkSettings;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 8:27 PM
 */
public class FireworksCommand {
	@CommandController.CommandHandler(name = "fw", usage = "/fw to create random fireworks around you", permission = "tunnels.common.fireworks")
	public void onFireworksCommand(Player player, String[] args) {
		try {
			new FireworkEffectPlayer().playFirework(player.getWorld(), player.getEyeLocation(), new FireworkSettings().randomFireworkEffect());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
