package com.caved_in.commons.command;

import com.caved_in.commons.inventory.Inventories;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:15 PM
 */
public class WorkbenchCommand {
	@CommandController.CommandHandler(name = "workbench", permission = "tunnels.common.workbench", aliases = {"wb", "wbench"})
	public void onWorkbenchCommand(Player player, String[] args) {
		Inventories.openWorkbench(player);
	}
}
