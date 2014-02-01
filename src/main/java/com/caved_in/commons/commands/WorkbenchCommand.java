package com.caved_in.commons.commands;

import com.caved_in.commons.inventory.InventoryHandler;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:15 PM
 */
public class WorkbenchCommand {
	@CommandController.CommandHandler(name = "workbench", permission = "tunnels.common.workbench", aliases = {"wb", "wbench"})
	public void onWorkbenchCommand(Player player, String[] args) {
		InventoryHandler.openWorkbench(player);
	}
}
