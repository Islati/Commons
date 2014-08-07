package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TunnelsXPCommand {

	@Command(name = "xp", usage = "/xp")
	public void playerXPCommand(Player player, String[] args) {
		Players.sendMessage(player, Messages.playerXpBalance(player));
	}
}
