package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.SavedItemManager;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand {
	@Command(identifier = "i", permissions = {Perms.COMMAND_ITEM})
	public void onItemCommand(Player player, @Arg(name = "item") ItemStack item, @Arg(name = "amount", def = "1") int amount) {
		item.setAmount(amount);
		Players.giveItem(player, item);
		Chat.actionMessage(player, Messages.playerItemsGiven(Items.getFormattedMaterialName(item.getType()), amount));
	}

	@Command(identifier = "i save", permissions = {Perms.COMMAND_ITEM})
	public void onItemSaveCommand(Player player, @Wildcard @Arg(name = "file name") String name) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name.trim())) {
			Chat.actionMessage(player, "The items name must not be empty!");
			return;
		}

		if (Players.handIsEmpty(player)) {
			Chat.actionMessage(player, Messages.ITEM_IN_HAND_REQUIRED);
			return;
		}

		ItemStack hand = player.getItemInHand();
		boolean saved = SavedItemManager.saveItem(name, hand);

		if (saved) {
			Chat.actionMessage(player, String.format("&e%s&a has been saved!", name));
		} else {
			Chat.actionMessage(player, String.format("&cFailed to save &e%s", name));
		}
	}

	@Command(identifier = "i load", permissions = {Perms.COMMAND_ITEM})
	public void onItemLoadCommand(Player player, @Wildcard @Arg(name = "file name") String name) {
		if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name.trim())) {
			Chat.actionMessage(player, "The items name must not be empty!");
			return;
		}

		ItemStack item = SavedItemManager.getItem(name);

		if (item == null) {
			Chat.actionMessage(player, String.format("&eThe item &c%s&e doesn't exist.", name));
			return;
		}

		Players.giveItem(player, item);
		Chat.actionMessage(player, "&aEnjoy!");
	}

	@Command(identifier = "i list", permissions = {Perms.COMMAND_ITEM})
	public void onItemListCommand(Player player) {
		for (String item : SavedItemManager.getItemNames()) {
			Chat.message(player, item);
		}
	}
}
