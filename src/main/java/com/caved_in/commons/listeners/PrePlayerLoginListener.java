package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.sql.ServerDatabaseConnector;
import com.caved_in.commons.time.DateUtils;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PrePlayerLoginListener implements Listener {

	private static ServerDatabaseConnector database;

	public PrePlayerLoginListener() {
		database = Commons.getInstance().getServerDatabase();
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
		if (Players.hasActivePunishment(event.getUniqueId(), PunishmentType.BAN)) {
			//Get the players ban and denies the event
			Punishment playerBan = database.getMostRecentPunishment(event.getUniqueId(), PunishmentType.BAN);
			event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, StringUtil.formatColorCodes(String.format("&cYou've been banned for '&e%s&c' and will be unbanned in &6%s", playerBan.getReason(), DateUtils.getDifferenceFormat(playerBan.getExpiryTime()))));
		}
	}
}
