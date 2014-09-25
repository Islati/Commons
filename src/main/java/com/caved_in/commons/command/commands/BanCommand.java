package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentBuilder;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.UUID;

public class BanCommand {
	@Command(name = "ban", description = "Bans a player permanately, or temporarily across all servers", permission = "commons.command.ban", usage = "/ban [Name] [Reason] <Time>")
	public void onBanCommand(final CommandSender sender, String[] args) {
		if (args.length < 2) {
			Players.sendMessage(sender, Messages.invalidCommandUsage("player", "reason", "(optional) t:"));
			return;
		}

		final UUID senderId = (sender instanceof Player) ? ((Player) sender).getUniqueId() : UUID.randomUUID();
		final String playerName = args[0];
		final boolean banningPlayerIsOnline = Players.isOnline(playerName);
		final StringBuilder banReason = new StringBuilder();
		int timeArg = -1;

		for (int i = 1; i < args.length; i++) {
			if (StringUtil.startsWithIgnoreCase(args[i], "t:")) {
				timeArg = i;
				continue;
			}
			banReason.append(args[i]);
			if (i != args.length - 1) {
				banReason.append(" ");
			}
		}

		//If the time argument hasn't been determined, then that determines when the ban expires
		final boolean isPermanent = timeArg == -1;


		String unparsedTime = null;
		long parsedLongTime = 0L;

		if (!isPermanent) {
			unparsedTime = args[timeArg];
			parsedLongTime = TimeHandler.parseStringForDuration(unparsedTime);
		}

		Punishment punishment = new PunishmentBuilder().withType(PunishmentType.BAN).permanent(isPermanent).expiresOn(isPermanent ? (System.currentTimeMillis() + TimeHandler.getTimeInMilles(10, TimeType.YEAR)) : (System.currentTimeMillis() + parsedLongTime)).issuedOn(System.currentTimeMillis()).withIssuer(senderId).withReason(banReason.toString()).build();

		if (banningPlayerIsOnline) {
			Players.ban(Players.getPlayer(playerName), punishment);
		} else {
			Players.ban(playerName, punishment);
		}

	}
}
