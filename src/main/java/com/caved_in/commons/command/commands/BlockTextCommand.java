package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.block.Letter;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.exceptions.InvalidMaterialNameException;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class BlockTextCommand {
	@Command(name = "blocktext", permission = "commons.command.blocktext")
	public void onBlockTextCommand(Player player, String[] args) {
		//Use letter class to draw text
		if (args.length < 2) {
			Players.sendMessage(player, Messages.invalidCommandUsage("item id / id:data", "Text"));
			return;
		}

		String itemArg = args[0];
		MaterialData materialData = null;
		try {
			materialData = Items.getMaterialDataFromString(itemArg);
		} catch (InvalidMaterialNameException invalidMaterialNameException) {
			Players.sendMessage(player, ChatColor.RED + invalidMaterialNameException.getMessage());
			return;
		}
		String text = StringUtil.joinString(args, " ", 1);
		Letter.drawString(text, materialData.getItemType(), materialData.getData(), Players.getTargetLocation(player), Players.getDirection(player));
	}
}
