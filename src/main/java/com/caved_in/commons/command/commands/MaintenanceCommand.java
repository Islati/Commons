package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.config.MaintenanceConfiguration;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

public class MaintenanceCommand {
	@Command(identifier = "maintenance", permissions = {Perms.MAINTENANCE_TOGGLE}, onlyPlayers = false)
	public void onMaintenanceCommand(CommandSender sender, @Arg(name = "action", def = "toggle") String mode) {
		MaintenanceConfiguration config = Commons.getConfiguration().getMaintenanceConfig();

		switch (mode.toLowerCase()) {
			case "on":
				config.setMaintenanceMode(true);
				Players.kickAllWithoutPermission(Perms.MAINTENANCE_WHITELIST, config.getKickMessage());
				Players.sendMessage(sender, Messages.MAINTENANCE_MODE_ENABLED);
				break;
			case "off":
				config.setMaintenanceMode(false);
				Players.sendMessage(sender, Messages.MAINTENANCE_MODE_DISABLED);
				break;
			case "toggle":
				config.toggleMaintenance();
				if (config.isMaintenanceMode()) {
					Players.kickAllWithoutPermission(Perms.MAINTENANCE_WHITELIST, config.getKickMessage());
					Players.sendMessage(sender, Messages.MAINTENANCE_MODE_ENABLED);
				} else {
					Players.sendMessage(sender, Messages.MAINTENANCE_MODE_DISABLED);
				}
				break;
			default:
				Players.sendMessage(sender, Messages.invalidCommandUsage("status [on/off/toggle]"));
				break;
		}
	}
}
