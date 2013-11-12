package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.Data.Bans.Punishment;
import com.caved_in.commons.handlers.Data.Bans.PunishmentType;
import com.caved_in.commons.handlers.Misc.TimeHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PrePlayerLoginListener implements Listener
{

	@EventHandler(priority = EventPriority.HIGHEST)
	public void PlayerPreLogin(AsyncPlayerPreLoginEvent Event)
	{
		Commons.bansDatabase.PardonAllExpiredPunishments(Event.getName());
		if (Commons.bansDatabase.isBanned(Event.getName()))
		{
			Punishment Ban = Commons.bansDatabase.getLatestRecord(PunishmentType.Ban, Event.getName());
			String KickMessage = "";
			KickMessage = "You've been banned by " + Ban.getIssuer();
			KickMessage += " for '" + Ban.getReason() + "'";
			KickMessage += " and will be unbanned in " + TimeHandler.getDurationBreakdown(Ban.getExpiryTime() - System.currentTimeMillis());
			Event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, KickMessage);
		}
	}
}
