package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.MaintenanceConfiguration;
import com.caved_in.commons.config.PremiumConfiguration;
import com.caved_in.commons.config.TunnelsPermissions;
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
		PremiumConfiguration premiumConfiguration = configuration.getPremiumConfig();
		//If maintenance mode is enabled, kick the player if they don't have permissions
		if (maintenanceConfiguration.isMaintenanceMode()) {
			if (!event.getPlayer().hasPermission(TunnelsPermissions.MAINTENANCE_WHITELIST)) {
				event.setKickMessage(maintenanceConfiguration.getKickMessage());
				event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			}
		}

		if (premiumConfiguration.isPremiumMode()) {
			if (!Commons.playerDatabase.getPlayerWrapper(player.getName()).isPremium()) {
				event.setKickMessage(premiumConfiguration.getKickMessage());
				event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			}
		}
	}
}
