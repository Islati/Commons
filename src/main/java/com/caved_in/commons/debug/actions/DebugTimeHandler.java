package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.time.TimeHandler;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;

public class DebugTimeHandler implements DebugAction {

	@Override
	public void doAction(Player player, String... args) {
		long tenYears = TimeHandler.parseStringForDuration("10y");
		long tenWeeks = TimeHandler.parseStringForDuration("10w");
		long tenDays = TimeHandler.parseStringForDuration("10d");
		Punishment punishment = Commons.getInstance().getServerDatabase().getMostRecentPunishment(player.getUniqueId(), PunishmentType.BAN);
		sendDurationMessage(player, tenYears, tenWeeks, tenDays, punishment.getExpiryTime() - punishment.getIssuedTime(), punishment.getExpiryTime() - System.currentTimeMillis());
	}

	private void sendDurationMessage(Player player, long... durations) {
		for (long duration : durations) {
			Chat.message(player, DurationFormatUtils.formatDurationWords(duration, true, true));
		}
	}

	@Override
	public String getActionName() {
		return "debug_time_handler";
	}
}
