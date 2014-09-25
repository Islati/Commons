package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.config.MaintenanceConfiguration;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

public class MaintenanceCommand {
	@Command(name = "maintenance", usage = "maintenance [on/off/toggle]", permission = "commons.maintenance.toggle")
	public void maintainanceToggleCommand(CommandSender commandSender, String[] commandArgs) {
		MaintenanceConfiguration maintenanceConfiguration = Commons.getConfiguration().getMaintenanceConfig();
		if (commandArgs.length > 0) {
			String maintainanceHandle = commandArgs[0];
			//Get the maintenance config
			switch (maintainanceHandle.toLowerCase()) {
				case "on":
					maintenanceConfiguration.setMaintenanceMode(true);
					Players.kickAllWithoutPermission(Perms.MAINTENANCE_WHITELIST, maintenanceConfiguration.getKickMessage());
					Players.sendMessage(commandSender, Messages.MAINTENANCE_MODE_ENABLED);
					break;
				case "off":
					maintenanceConfiguration.setMaintenanceMode(false);
					Players.sendMessage(commandSender, Messages.MAINTENANCE_MODE_DISABLED);
					break;
				case "toggle":
					maintenanceConfiguration.toggleMaintenance();
					if (maintenanceConfiguration.isMaintenanceMode()) {
						Players.kickAllWithoutPermission(Perms.MAINTENANCE_WHITELIST, maintenanceConfiguration.getKickMessage());
						Players.sendMessage(commandSender, Messages.MAINTENANCE_MODE_ENABLED);
					} else {
						Players.sendMessage(commandSender, Messages.MAINTENANCE_MODE_DISABLED);
					}
					break;
				default:
					Players.sendMessage(commandSender, Messages.invalidCommandUsage("status [on/off/toggle]"));
					break;
			}
		} else {
			Players.sendMessage(commandSender, "&aMaintenance mode is currently " + (maintenanceConfiguration.isMaintenanceMode() ? "&eEnabled " : "&eDisabled ") + " to change this, do &e/Maintenance toggle");
		}
	}
}
