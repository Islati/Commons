package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.command.CommandSender;

public class MaintenanceCommand {
    private static Configuration config = Commons.getInstance().getConfiguration();

    public MaintenanceCommand() {

    }

    @Command(identifier = "maintenance", permissions = {Perms.MAINTENANCE_TOGGLE}, onlyPlayers = false)
    public void onMaintenanceCommand(CommandSender sender, @Arg(name = "action", def = "toggle") String mode) {
        switch (mode.toLowerCase()) {
            case "on":
                config.setMaintenanceMode(true);
                Players.kickAllWithoutPermission(Perms.MAINTENANCE_WHITELIST, config.maintenanceModeKickMessage());
                Chat.message(sender, Messages.MAINTENANCE_MODE_ENABLED);
                break;
            case "off":
                config.setMaintenanceMode(false);
                Chat.message(sender, Messages.MAINTENANCE_MODE_DISABLED);
                break;
            case "toggle":
                config.setMaintenanceMode(!config.isMaintenanceModeEnabled());
                if (config.isMaintenanceModeEnabled()) {
                    Players.kickAllWithoutPermission(Perms.MAINTENANCE_WHITELIST, config.maintenanceModeKickMessage());
                    Chat.message(sender, Messages.MAINTENANCE_MODE_ENABLED);
                } else {
                    Chat.message(sender, Messages.MAINTENANCE_MODE_DISABLED);
                }
                break;
            default:
                Chat.message(sender, Messages.invalidCommandUsage("status [on/off/toggle]"));
                break;
        }
    }
}
