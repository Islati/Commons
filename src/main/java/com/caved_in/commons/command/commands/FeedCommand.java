package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:12 PM
 */
public class FeedCommand {
	@Command(identifier = "feed", permissions = Perms.COMMAND_FEED)
	public void feedCommand(Player player, @Arg(name = "player", def = "?sender") Player target) {
		Players.feed(target);

		//If the player fed themselves we want to give them a unique message
		if (target.equals(player)) {
			Players.sendMessage(player, "&aYou've fully fed yourself! &eMmmmm!");
			return;
		}

		Players.sendMessage(target, Messages.PLAYER_FED);
		Players.sendMessage(player, Messages.playerFed(target.getName()));
	}
}
