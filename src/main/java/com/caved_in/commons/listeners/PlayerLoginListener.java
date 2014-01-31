package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.TunnelsPermissions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (Commons.getConfiguration().getMaintenanceConfig().isMaintenanceMode()) {
			if (!event.getPlayer().hasPermission(TunnelsPermissions.MAINTENANCE_WHITELIST)) {
				event.setKickMessage(Commons.getConfiguration().getMaintenanceConfig().getKickMessage());
				event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			}
		}
	}
}
