package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class FeedCommand {
	@Command(identifier = "feed", permissions = Perms.COMMAND_FEED)
	public void feedCommand(Player player, @Arg(name = "player", def = "?sender") Player target) {
		Players.feed(target);

		//If the player fed themselves we want to give them a unique message
		if (target.equals(player)) {
			Chat.message(player, "&aYou've fully fed yourself! &eMmmmm!");
			return;
		}

		Chat.message(target, Messages.PLAYER_FED);
		Chat.message(player, Messages.playerFed(target.getName()));
	}
}
