package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.CallablePardonPlayerPunishments;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnbanCommand {
	@Command(name = "unban", description = "Unban / pardon a user from their ban", permission = "commons.command.unban", usage = "/unban [Name]", aliases = {"pardon"})
	public void onUnbanCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Players.sendMessage(sender, Messages.invalidCommandUsage("name"));
			return;
		}

		final String playerToUnban = args[0];
		String pardonIssuer = "";
		CallablePardonPlayerPunishments pardonPlayerCallable = new CallablePardonPlayerPunishments(playerToUnban, PunishmentType.BAN);

		if (sender instanceof Player) {
			pardonIssuer = sender.getName();
			pardonPlayerCallable.setPardoningPlayer(((Player) sender).getUniqueId());
		} else {
			pardonIssuer = "Console";
			pardonPlayerCallable.setConsole(true);
		}

		ListenableFuture<Boolean> pardonPlayerListener = Commons.asyncExecutor.submit(pardonPlayerCallable);
		final boolean console = pardonIssuer.equalsIgnoreCase("Console");
		final Player player = console ? null : (Player) sender;
		Futures.addCallback(pardonPlayerListener, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean aBoolean) {
				if (aBoolean) {
					if (console) {
						Commons.messageConsole(Messages.playerPardoned(playerToUnban));
						return;
					}
					Players.sendMessage(player, Messages.playerPardoned(playerToUnban));
				} else {
					if (console) {
						Commons.messageConsole(Messages.ERROR_RETRIEVING_PLAYER_DATA);
						return;
					}
					Players.sendMessage(player, Messages.ERROR_RETRIEVING_PLAYER_DATA);
				}
			}

			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
			}
		});
	}
}
