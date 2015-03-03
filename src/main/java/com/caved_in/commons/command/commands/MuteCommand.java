package com.caved_in.commons.command.commands;

import com.caved_in.commons.bans.Punishment;
import com.caved_in.commons.bans.PunishmentBuilder;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.DateUtils;
import com.mysql.jdbc.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MuteCommand {

	@Command(identifier = "mute", permissions = Perms.COMMAND_MUTE, onlyPlayers = false)
	@Flags(identifier = {"t"}, description = {"Time to ban the player for"})
	public void onMuteCommand(CommandSender sender, @Arg(name = "player-name") String name, @Arg(name = "time", def = "0") String timeDuration, @Arg(name = "reason") String reason) {
		final UUID senderId = (sender instanceof Player) ? ((Player) sender).getUniqueId() : UUID.randomUUID();

		long time = 0;

		boolean forever = false;
		if (("0".equalsIgnoreCase(timeDuration) || StringUtils.isNullOrEmpty(timeDuration))) {
			forever = true;
		} else {
			try {
				time = DateUtils.parseDateDiff(timeDuration, true);
				Chat.debug("Duration: " + timeDuration, "Time = " + time);
			} catch (Exception e) {
				Chat.message(sender, "&eThe time you entered has an invalid format, try again &aplease&e!");
				return;
			}
		}

		Punishment punishment = new PunishmentBuilder()
				.withType(PunishmentType.MUTE)
				.withIssuer(senderId)
				.withReason(reason)
				.permanent(forever)
				.issuedOn(System.currentTimeMillis())
				.expiresOn(Long.sum(System.currentTimeMillis(), time))
				.build();

		if (Players.isOnline(name)) {
			Players.mute(Players.getPlayer(name), punishment);
		} else {
			Players.mute(name, punishment);
		}
		Chat.message(sender, String.format("&e%s&a has been &cmuted!", name));
	}

}
