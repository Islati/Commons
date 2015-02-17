package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddCurrencyCommand {


	@Command(
			identifier = "addcurrency",
			description = "Give the player some currency!",
			onlyPlayers = false,
			permissions = {Perms.COMMAND_ADD_CURRENCY}
	)
	public void addCurrency(CommandSender sender, @Arg(name = "player", description = "player to give the money to") Player player, @Arg(name = "amount", description = "amount of currency to give to the player!") double amt) {
		Players.giveMoney(player, (int) amt, true);
		Players.updateData(player);
		Chat.message(sender, Messages.playerAddedXp(player.getName(), (int) amt));
	}
}
