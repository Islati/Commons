package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.config.MaintenanceConfiguration;
import com.caved_in.commons.config.TunnelsPermissions;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 8:12 PM
 */
public class MaintenanceCommand {
	@CommandController.CommandHandler(name = "maintenance", usage = "maintainance [on/off/toggle]", permission = "tunnels.common.maintenance")
	public void maintainanceToggleCommand(CommandSender commandSender, String[] commandArgs) {
		MaintenanceConfiguration maintenanceConfiguration = Commons.getConfiguration().getMaintenanceConfig();
		if (commandArgs.length > 0) {
			String maintainanceHandle = commandArgs[0];
			//Get the maintenance config
			switch (maintainanceHandle.toLowerCase()) {
				case "on":
					maintenanceConfiguration.setMaintenanceMode(true);
					Players.kickAllPlayersWithoutPermission(TunnelsPermissions.MAINTENANCE_WHITELIST, maintenanceConfiguration.getKickMessage());
					Players.sendMessage(commandSender, Messages.MAINTENANCE_MODE_ENABLED);
					break;
				case "off":
					maintenanceConfiguration.setMaintenanceMode(false);
					Players.sendMessage(commandSender, Messages.MAINTENANCE_MODE_DISABLED);
					break;
				case "toggle":
					maintenanceConfiguration.toggleMaintenance();
					if (maintenanceConfiguration.isMaintenanceMode()) {
						Players.kickAllPlayersWithoutPermission(TunnelsPermissions.MAINTENANCE_WHITELIST, maintenanceConfiguration.getKickMessage());
						Players.sendMessage(commandSender, Messages.MAINTENANCE_MODE_ENABLED);
					} else {
						Players.sendMessage(commandSender, Messages.MAINTENANCE_MODE_DISABLED);
					}
					break;
				default:
					Players.sendMessage(commandSender, Messages.INVALID_COMMAND_USAGE("status [on/off/toggle]"));
					break;
			}
		} else {
			Players.sendMessage(commandSender, "&aMaintenance mode is currently " + (maintenanceConfiguration.isMaintenanceMode() ? "&eEnabled " : "&eDisabled ") + " to change this, do &e/Maintenance toggle");
		}
	}
}
