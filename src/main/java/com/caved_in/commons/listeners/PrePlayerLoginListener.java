package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PrePlayerLoginListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		if (Players.hasActivePunishment(event.getUniqueId(), PunishmentType.BAN)) {
			//Get the players ban and denies the event
			Punishment playerBan = Commons.database.getMostRecentPunishment(event.getUniqueId(), PunishmentType.BAN);
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, StringUtil.formatColorCodes(String.format("&cYou've been banned for '&e%s&c' and will be unbanned in &6%s", playerBan.getReason(), DurationFormatUtils.formatDurationWords(playerBan.getExpiryTime() - System.currentTimeMillis(), true, true))));
		}
	}
}
