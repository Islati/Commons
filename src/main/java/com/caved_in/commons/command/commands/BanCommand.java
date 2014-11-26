package com.caved_in.commons.command.commands;

import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentBuilder;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.command.*;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeHandler;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class BanCommand {
	@Command(identifier = "ban", permissions = {Perms.COMMAND_BAN})
	@Flags(identifier = {"t", "p"}, description = {"Time to ban the player for", "Permanently bans the player"})
	public void banCommand(CommandSender sender, @Arg(name = "player") String target, @FlagArg("t") @Arg(name = "time") String timeDuration, @FlagArg("p") final boolean permanent, @Wildcard @Arg(name = "reason") String reason) {
		final UUID senderId = (sender instanceof Player) ? ((Player) sender).getUniqueId() : UUID.randomUUID();

		long time = 0;
		if (!permanent) {
			if (timeDuration == null) {
				Players.sendMessage(sender, "If the ban isn't permanent, then you should include a time duration");
				return;
			}

			time = TimeHandler.parseStringForDuration(timeDuration);
		}

		if (time == 0 && !permanent) {
			Players.sendMessage(sender, "You need to include the time duration.\n&a--> &e/ban " + target + " -t 10y1m2w Being a griefer.");
			return;
		}

		Punishment punishment = new PunishmentBuilder()
				.withType(PunishmentType.BAN)
				.withIssuer(senderId)
				.withReason(reason)
				.permanent(permanent)
				.issuedOn(System.currentTimeMillis())
				.expiresOn(Long.sum(System.currentTimeMillis(), time))
				.build();

		if (Players.isOnline(target)) {
			Players.ban(Players.getPlayer(target), punishment);
		} else {
			Players.ban(target, punishment);
		}

	}

}
