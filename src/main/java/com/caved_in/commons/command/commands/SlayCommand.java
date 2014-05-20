package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Set;

public class SlayCommand {
	@Command(name = "slay", usage = "/slay <radius> <include players [yes/no]", permission = "commons.command.slay")
	public void onSlayCommand(Player player, String[] args) {
		//If there's no arguments, or theres only one argument and its not a number
		if (args.length == 0 || (args.length >= 1 && !StringUtils.isNumeric(args[0]))) {
			Players.sendMessage(player, Messages.invalidCommandUsage("radius", "include players (yes/no)"));
			return;
		}

		int radius = Integer.parseInt(args[0]);
		boolean killPlayers = false;
		if (args.length > 1) {
			String killPlayersArg = args[1];
			switch (killPlayersArg.toLowerCase()) {
				case "yes":
				case "true":
					killPlayers = true;
					break;
				default:
					break;
			}
		}

		int amountRemoved = 0;
		Set<LivingEntity> entities = Entities.getEntitiesNearLocation(player.getLocation(), radius);

		for (LivingEntity entity : entities) {
			//If we don't want to kill players, and the entity being looped is a player, then skip this entity
			if (!killPlayers && entity instanceof Player) {
				continue;
			}
			Entities.kill(entity);
			amountRemoved++;
		}
		Players.sendMessage(player, Messages.entityRemovedEntities(amountRemoved));
	}
}
