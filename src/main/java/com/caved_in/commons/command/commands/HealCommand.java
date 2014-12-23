package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class HealCommand {
	@Command(identifier = "heal", permissions = {Perms.COMMAND_HEAL})
	public void onHealCommand(Player player, @Arg(name = "target", def = "?sender") Player target) {
		Players.heal(target);
		Chat.message(target, Messages.PLAYER_HEALED);
		//If the player using the command healed themself.
		if (!target.getUniqueId().equals(player.getUniqueId())) {
			if (!player.hasPermission(Perms.HEAL_OTHER)) {
				Chat.message(player, Messages.permissionRequired(Perms.HEAL_OTHER));
				return;
			}
			Chat.message(player, Messages.playerHealed(target.getName()));
		}
	}
}
