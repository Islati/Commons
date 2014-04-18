package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeHandler;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class DebugTimeHandler implements DebugAction {

	@Override
	public void doAction(Player player) {
		long tenYears = TimeHandler.parseStringForDuration("10y");
		long tenWeeks = TimeHandler.parseStringForDuration("10w");
		long tenDays = TimeHandler.parseStringForDuration("10d");
		Punishment punishment = Commons.database.getMostRecentPunishment(player.getUniqueId(), PunishmentType.BAN);
		sendDurationMessage(player, tenYears, tenWeeks, tenDays, punishment.getExpiryTime() - punishment.getIssuedTime(), punishment.getExpiryTime() - System.currentTimeMillis());
	}

	private void sendDurationMessage(Player player, long... durations) {
		for (long duration : durations) {
			Players.sendMessage(player, DurationFormatUtils.formatDurationWords(duration, true, true));
		}
	}

	@Override
	public String getActionName() {
		return "debug_time_handler";
	}
}
