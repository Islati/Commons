package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnMobCommand {
	@Command(identifier = "spawnmob", permissions = Perms.COMMAND_MOB_SPAWN)
	public void onSpawnMobCommand(Player player, @Arg(name = "mob type") EntityType type, @Arg(name = "amount") int amount) {
		if (type == null) {
			return;
		}

		Entities.spawnLivingEntity(type, Players.getTargetLocation(player), amount);
	}
}
