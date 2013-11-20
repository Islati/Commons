package com.caved_in.commons.commands;

import com.caved_in.commons.commands.chat.ChatCommands;
import com.caved_in.commons.commands.moderation.PunishmentCommands;
import com.caved_in.commons.Commons;
import com.caved_in.commons.commands.admin.AdminCommands;
import com.caved_in.commons.commands.donator.DonatorCommands;
import com.caved_in.commons.commands.friends.FriendCommands;
import com.caved_in.commons.commands.utility.Utility;

public class CommandRegister
{

	public CommandRegister(Commons Plugin)
	{
		CommandController.registerCommands(Plugin, new DonatorCommands());
		CommandController.registerCommands(Plugin, new Utility());
		CommandController.registerCommands(Plugin, new PunishmentCommands());
		CommandController.registerCommands(Plugin, new ChatCommands());
		CommandController.registerCommands(Plugin, new AdminCommands());
		CommandController.registerCommands(Plugin, new FriendCommands());
	}

}
