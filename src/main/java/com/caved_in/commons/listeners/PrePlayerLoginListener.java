package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PrePlayerLoginListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		if (Players.hasActivePunishment(event.getUniqueId(), PunishmentType.BAN)) {
			//Get the players ban and denies the event
			Punishment playerBan = Commons.playerDatabase.getMostRecentPunishment(event.getUniqueId(), PunishmentType.BAN);
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, String.format("You've been banned for '%s' and will be unbanned in %s", playerBan.getReason(), TimeHandler.getDurationBreakdown(playerBan.getExpiryTime() - System.currentTimeMillis())));
		}
	}
}
