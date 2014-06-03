package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.ItemType;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;


public class HatCommand {
	@Command(name = "hat", usage = "/hat <ItemID/ItemName> to place an item on your head (overwriting your current item); /hat to remove it", permission = "tunnels.common.hat")
	public void onHatCommand(Player player, String[] args) {
		PlayerInventory inventory = player.getInventory();

		if (args.length == 0) {
			//If they're wearing a helmet, remove it and give it back to them.
			if (!Items.isAir(inventory.getHelmet())) {
				Players.giveItem(player, inventory.getHelmet(), true);
				inventory.setHelmet(null);
				Players.sendMessage(player, Messages.HAT_UNEQUIPPED);
				return;
			}

			if (Players.hasItemInHand(player)) {
				inventory.setHelmet(player.getItemInHand());
				Players.clearHand(player);
				Players.sendMessage(player, Messages.HAT_EQUIPPED);
				return;
			}
		}

		if (args.length == 1) {
			Material hatMaterial;
			int hatId;
			String hatArg = args[0];
			if (StringUtils.isNumeric(hatArg)) {
				hatId = Integer.parseInt(hatArg);
			} else {
				ItemType itemType = ItemType.lookup(hatArg);
				if (itemType == null) {
					Players.sendMessage(player, Messages.invalidItem(hatArg));
					return;
				}

				hatId = itemType.getID();

			}

			hatMaterial = Material.getMaterial(hatId);
			inventory.setHelmet(Items.makeItem(hatMaterial));
			Players.sendMessage(player, Messages.HAT_EQUIPPED);
		}
	}
}
