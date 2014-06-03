package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MoreCommand {
	@Command(name = "more", usage = "/more", permission = "commons.commands.more")
	public void onMoreCommand(Player player, String[] args) {
		if (!Players.hasItemInHand(player)) {
			Players.sendMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
			return;
		}
		ItemStack playerHandItem = player.getItemInHand();
		playerHandItem.setAmount(64);
		player.setItemInHand(playerHandItem);
	}
}
