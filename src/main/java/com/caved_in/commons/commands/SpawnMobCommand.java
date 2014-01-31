package com.caved_in.commons.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.commands.CommandController;
import com.caved_in.commons.entity.EntityUtility;
import com.caved_in.commons.player.PlayerHandler;
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
			EntityType entityType = EntityUtility.getTypeByName(mobArg);
			if (entityType != EntityType.UNKNOWN) {
				int spawnAmount = 1;
				if (args.length > 1 && StringUtils.isNumeric(args[1])) {
					spawnAmount = Integer.parseInt(args[1]);
				}
				EntityUtility.spawnLivingEntity(entityType, PlayerHandler.getTargetLocation(player), spawnAmount);
			} else {
				PlayerHandler.sendMessage(player, Messages.INVALID_MOB_TYPE(mobArg));
			}
		} else {
			PlayerHandler.sendMessage(player, Messages.INVALID_COMMAND_USAGE("mob"));
		}
	}
}
