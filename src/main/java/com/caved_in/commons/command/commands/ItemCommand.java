package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand {
	@Command(identifier = "i", permissions = {Perms.COMMAND_ITEM})
	public void onItemCommand(Player player, @Arg(name = "item") ItemStack item, @Arg(name = "amount", def = "1") int amount) {
		item.setAmount(amount);
		Players.giveItem(player, item);
		Players.sendMessage(player, Messages.playerItemsGiven(Items.getFormattedMaterialName(item.getType()), amount));
	}
}
