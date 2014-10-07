package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

public class AddCurrencyCommand {


	@Command(
			identifier = "addcurrency",
			description = "Give the player some currency!",
			onlyPlayers = false,
			permissions = {"commons.command.addcurrency"}
	)
	public void addCurrency(CommandSender sender, @Arg(name = "player", description = "player to give the money to") MinecraftPlayer player, @Arg(name = "amount", description = "amount of currency to give to the player!") double amt) {
		player.addCurrency(amt);
		Players.updateData(player);
		Players.sendMessage(sender, Messages.playerAddedXp(player.getName(), (int) amt));
	}
}
