package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.item.ItemSetManager;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.mysql.jdbc.StringUtils;
import org.bukkit.entity.Player;

public class SetCommand {
	private static ItemSetManager sets;

	public SetCommand() {
		sets = Commons.getInstance().getItemSetManager();
	}

	@Command(identifier = "set", permissions = Perms.SET_COMMAND)
	public void onSetCommand(Player player, @Wildcard @Arg(name = "name") String name) {
		if (StringUtils.isNullOrEmpty(name)) {
			Chat.message(player, Messages.invalidCommandUsage("name(can include spaces)"));
			return;
		}

		ItemSetManager.ItemSet set = sets.getSet(name);

		if (set == null) {
			Chat.message(player, String.format("&cThe set '&e%s'&c doesn't exist", name));
			return;
		}

		Players.setInventory(player, set.getInventoryContents(), true);
		Chat.message(player, String.format("&eYour inventory has been set to the '&a%s&e' item set", set.getName()));
	}

	@Command(identifier = "set list")
	public void onSetListCommand(Player player) {
		for (String name : sets.getSetNames()) {
			Chat.message(player, name);
		}
	}

	@Command(identifier = "set save")
	public void onSetSaveCommand(Player player, @Wildcard @Arg(name = "name") String name) {
		if (StringUtils.isNullOrEmpty(name)) {
			Chat.message(player, Messages.invalidCommandUsage("name(can include spaces)"));
			return;
		}

		ItemSetManager.ItemSet set = new ItemSetManager.ItemSet(name, player.getInventory());
		sets.addSet(set);

		Chat.message(player, String.format("&aSaved the set &e%s&a to file!", name));

	}

}
