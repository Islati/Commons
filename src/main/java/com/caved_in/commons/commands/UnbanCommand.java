package com.caved_in.commons.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.callables.PardonPlayerPunishmentsTask;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 7:56 PM
 */
public class UnbanCommand {
	@CommandController.CommandHandler(name = "unban", description = "Unban / pardon a user from their ban", permission = "tunnels.common.unban", usage = "/unban [Name]", aliases = {"pardon"})
	public void onUnbanCommand(CommandSender sender, String[] args) {
		if (args.length == 0) {
			Players.sendMessage(sender, Messages.invalidCommandUsage("name"));
			return;
		}

		final String playerToUnban = args[0];
		String pardonIssuer = "";
		PardonPlayerPunishmentsTask pardonPlayerCallable = new PardonPlayerPunishmentsTask(playerToUnban, PunishmentType.BAN);

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
