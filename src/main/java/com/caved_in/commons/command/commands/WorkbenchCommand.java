package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Command;
import com.caved_in.commons.inventory.Inventories;
import org.bukkit.entity.Player;

public class WorkbenchCommand {
	@Command(name = "workbench", permission = "commons.command.workbench", aliases = {"wb", "wbench"})
	public void onWorkbenchCommand(Player player, String[] args) {
		Inventories.openWorkbench(player);
	}
}
