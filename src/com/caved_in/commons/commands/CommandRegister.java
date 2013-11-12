package com.caved_in.commons.commands;

import com.caved_in.commons.commands.Chat.ChatCommands;
import com.caved_in.commons.commands.Moderation.PunishmentCommands;
import com.caved_in.commons.Commons;
import com.caved_in.commons.commands.Admin.AdminCommands;
import com.caved_in.commons.commands.Donator.DonatorCommands;
import com.caved_in.commons.commands.Friends.FriendCommands;
import com.caved_in.commons.commands.Utility.Utility;

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
