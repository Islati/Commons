package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.time.TimeHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PrePlayerLoginListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		//Person who's logging in
		String playerName = event.getName();
		//Pardon all expired punishments for this player
		//TODO Make a "cron thread" to do this.
		Commons.bansDatabase.pardonExpiredPunishments(playerName);
		if (Commons.bansDatabase.isBanned(playerName)) {
			//Get the players ban
			Punishment playerBan = Commons.bansDatabase.getLatestRecord(PunishmentType.BAN, playerName);
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, String.format("You've been banned by %s for '%s' and will be unbanned in %s",playerBan.getIssuer(),playerBan.getReason(),TimeHandler.getDurationBreakdown(playerBan.getExpiryTime() - System.currentTimeMillis())));
		}
	}
}
