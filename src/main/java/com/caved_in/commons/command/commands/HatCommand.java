package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;


public class HatCommand {
	@Command(identifier = "hat", permissions = {Perms.COMMAND_HAT})
	public void hatCommand(Player player, @Arg(name = "hat-item", def = "0") ItemStack hat) {
		PlayerInventory inventory = player.getInventory();
		if (hat == null || hat.getType() == Material.AIR) {
			if (!Items.isAir(inventory.getHelmet())) {
				Players.giveItem(player, inventory.getHelmet(), true);
				inventory.setHelmet(null);
				Chat.message(player, Messages.HAT_UNEQUIPPED);
				return;
			}

			if (Players.hasItemInHand(player)) {
				inventory.setHelmet(player.getItemInHand());
				Players.clearHand(player);
				Chat.message(player, Messages.HAT_EQUIPPED);
				return;
			}
		}

		inventory.setHelmet(hat);
		Chat.message(player, Messages.HAT_EQUIPPED);
	}
}
