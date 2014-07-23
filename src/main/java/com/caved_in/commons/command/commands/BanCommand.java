package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentBuilder;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.BanPlayerCallable;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.UUID;

public class BanCommand {
	@Command(name = "ban", description = "Bans a player permanately, or temporarily across all servers", permission = "tunnels.common.ban", usage = "/ban [Name] [Reason] <Time>")
	public void onBanCommand(final CommandSender sender, String[] args) {
		if (args.length < 2) {
			Players.sendMessage(sender, Messages.invalidCommandUsage("player", "reason", "(optional) t:"));
			return;
		}

		final UUID senderId = (sender instanceof Player) ? ((Player) sender).getUniqueId() : UUID.randomUUID();
		final String playerName = args[0];
		final boolean banningPlayerIsOnline = Players.isOnline(playerName);
		final Player banningPlayer = Players.getPlayer(playerName);
		final String bannerName = sender.getName();
		final StringBuilder banReason = new StringBuilder();
		int timeArg = -1;

		for (int i = 1; i < args.length; i++) {
			if (StringUtil.startsWithIgnoreCase(args[i], "t:")) {
				timeArg = i;
				break;
			}
			banReason.append(args[i]);
			if (i != args.length - 1) {
				banReason.append(" ");
			}
		}

		//If the time argument hasn't been determined, then that determines when the ban expires
		final boolean isPermanent = timeArg == -1;

		final String unparsedTime = isPermanent ? null : args[timeArg];
		final long parsedLongTime = isPermanent ? 0L : TimeHandler.parseStringForDuration(unparsedTime);
		Punishment punishment = new PunishmentBuilder().withType(PunishmentType.BAN).expiresOn(isPermanent ? (System.currentTimeMillis() + TimeHandler.getTimeInMilles(10, TimeType.YEAR)) : (System.currentTimeMillis() + parsedLongTime)).issuedOn(System.currentTimeMillis()).withIssuer(senderId).withReason(banReason.toString()).build();

		ListenableFuture<Boolean> banPlayerFuture;


		if (banningPlayerIsOnline) {
			banPlayerFuture = Commons.asyncExecutor.submit(new BanPlayerCallable(banningPlayer.getUniqueId(), punishment));
		} else {
			banPlayerFuture = Commons.asyncExecutor.submit(new BanPlayerCallable(playerName, punishment));
		}

		Futures.addCallback(banPlayerFuture, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean banned) {
				if (banned) {
					if (banningPlayerIsOnline) {
						Players.kick(banningPlayer, banReason.toString(), true);
					}
					Players.messageAll(Messages.playerBannedGlobalMessage(playerName, bannerName, banReason.toString(), isPermanent ? "Never" : DurationFormatUtils.formatDurationWords(parsedLongTime, true, true)));
				} else {
					Players.sendMessage(sender, Messages.playerNotBanned(playerName));
				}
			}

			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
			}
		});
	}
}
