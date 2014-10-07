package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import com.caved_in.commons.inventory.Inventories;
import org.bukkit.entity.Player;

public class WorkbenchCommand {
	@Command(identifier = "workbench", permissions = "commons.command.workbench")
	public void onWorkbenchCommand(Player player) {
		Inventories.openWorkbench(player);
	}
}
