package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class HealCommand {
	@Command(identifier = "heal", permissions = {"commons.command.heal"})
	public void onHealCommand(Player player, @Arg(name = "target", def = "?sender") Player target) {
		Players.heal(target);
		Players.sendMessage(target, Messages.PLAYER_HEALED);
		//If the player using the command healed themself.
		if (!target.getUniqueId().equals(player.getUniqueId())) {
			Players.sendMessage(player, Messages.playerHealed(target.getName()));
		}
	}
}
