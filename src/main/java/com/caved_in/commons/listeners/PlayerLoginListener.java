package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLoginListener implements Listener {

	private Configuration config;

	public PlayerLoginListener() {
		config = Commons.getInstance().getConfiguration();
	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		//If maintenance mode is enabled, kick the player if they don't have permissions
		if (config.isMaintenanceModeEnabled()) {
			if (!Players.hasPermission(player, Perms.MAINTENANCE_WHITELIST)) {
				event.setKickMessage(config.maintenanceModeKickMessage());
				event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
			}
		}

		if (!config.hasSqlBackend()) {
			return;
		}
		/*
		If the server is in premium-only mode check if
		the player is premium and if not kick them
		*/
		if (!config.isPremiumOnlyMode()) {
			return;
		}

		//TODO Hook Vault / Permissions to see if player has permission to join while in premium.
		if (!Commons.getInstance().getServerDatabase().getPlayerWrapper(player.getUniqueId()).isPremium()) {
			event.setKickMessage(config.getPremiumOnlyModeKickMessage());
			event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
		}
	}
}
