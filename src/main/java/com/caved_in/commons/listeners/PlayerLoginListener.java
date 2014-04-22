package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.MaintenanceConfiguration;
import com.caved_in.commons.config.PremiumConfiguration;
import com.caved_in.commons.permission.Permission;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		Configuration configuration = Commons.getConfiguration();
		MaintenanceConfiguration maintenanceConfiguration = configuration.getMaintenanceConfig();
		//If maintenance mode is enabled, kick the player if they don't have permissions
		if (maintenanceConfiguration.isMaintenanceMode()) {
			if (!Players.hasPermission(player, Permission.MAINTENANCE_WHITELIST)) {
				event.setKickMessage(maintenanceConfiguration.getKickMessage());
				event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			}
		}

		if (!configuration.hasSqlBackend()) {
			return;
		}
		/*
		If the server is in premium-only mode check if
		the player is premium and if not kick them
		*/
		PremiumConfiguration premiumConfiguration = configuration.getPremiumConfig();
		if (!premiumConfiguration.isPremiumMode()) {
			return;
		}
		if (!Commons.database.getPlayerWrapper(player.getUniqueId()).isPremium()) {
			event.setKickMessage(premiumConfiguration.getKickMessage());
			event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
		}
	}
}
