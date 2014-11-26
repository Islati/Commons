package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.permission.Perms;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkullCommand {
	@Command(identifier = "skull", permissions = Perms.COMMAND_SKULL)
	public void getPlayerSkullCommand(Player player, @Arg(name = "player") String name) {
		ItemStack playerSkull = Items.getSkull(name);
		Items.setName(playerSkull, name + "'s Head");
		player.getInventory().addItem(playerSkull);
	}
}
