package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:14 PM
 */
public class SpawnMobCommand {
	@CommandController.CommandHandler(name = "spawnmob", permission = "tunnels.common.spawnmob")
	public void onSpawnMobCommand(Player player, String[] args) {
		if (args.length > 0) {
			String mobArg = args[0];
			EntityType entityType = Entities.getTypeByName(mobArg);
			if (entityType != EntityType.UNKNOWN) {
				int spawnAmount = 1;
				if (args.length > 1 && StringUtils.isNumeric(args[1])) {
					spawnAmount = Integer.parseInt(args[1]);
				}
				Entities.spawnLivingEntity(entityType, Players.getTargetLocation(player), spawnAmount);
			} else {
				Players.sendMessage(player, Messages.invalidMobType(mobArg));
			}
		} else {
			Players.sendMessage(player, Messages.invalidCommandUsage("mob"));
		}
	}
}
