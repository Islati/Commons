package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.item.ItemSetManager;
import com.caved_in.commons.permission.Perms;
import org.bukkit.entity.Player;

public class SetCommand {
	private static ItemSetManager sets;

	public SetCommand() {
		sets = Commons.getInstance().getItemSetManager();
	}

	@Command(identifier = "set", permissions = Perms.SET_COMMAND)
	public void onSetCommand(Player player, @Wildcard @Arg(name = "name") String name) {

	}

}
