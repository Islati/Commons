package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.PardonPlayerPunishmentsCallable;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnbanCommand {
	@Command(onlyPlayers = false, identifier = "unban", description = "Unban / pardon a user from their ban", permissions = Perms.COMMAND_UNBAN)
	public void onUnbanCommand(CommandSender sender, @Arg(name = "player") String name) {

		String pardonIssuer = "";
		PardonPlayerPunishmentsCallable pardonPlayerCallable = new PardonPlayerPunishmentsCallable(name, PunishmentType.BAN);

		if (sender instanceof Player) {
			pardonIssuer = sender.getName();
			pardonPlayerCallable.setPardoningPlayer(((Player) sender).getUniqueId());
		} else {
			pardonIssuer = "Console";
			pardonPlayerCallable.setConsole(true);
		}

		ListenableFuture<Boolean> pardonPlayerListener = Commons.getInstance().getAsyncExecuter().submit(pardonPlayerCallable);
		final boolean console = pardonIssuer.equalsIgnoreCase("Console");
		final Player player = console ? null : (Player) sender;
		Futures.addCallback(pardonPlayerListener, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean aBoolean) {
				if (aBoolean) {
					if (console) {
						Commons.messageConsole(Messages.playerPardoned(name));
						return;
					}
					Players.sendMessage(player, Messages.playerPardoned(name));
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
