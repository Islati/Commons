package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.threading.tasks.PardonPlayerPunishmentsCallable;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

//TODO Fix.
public class UnmuteCommand {
	@Command(onlyPlayers = false, identifier = "unmute", description = "Pardon a player of their mute", permissions = Perms.COMMAND_UNMUTE)
	public void onUnbanCommand(CommandSender sender, @Arg(name = "player") String name) {

		String pardonIssuer = "";
		PardonPlayerPunishmentsCallable pardonPlayerCallable = new PardonPlayerPunishmentsCallable(name, PunishmentType.MUTE);

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
						Chat.messageConsole(Messages.playerPardoned(name));
						return;
					}
					Chat.message(player, Messages.playerPardoned(name));
				} else {
					if (console) {
						Chat.messageConsole(Messages.ERROR_RETRIEVING_PLAYER_DATA);
						return;
					}
					Chat.message(player, Messages.ERROR_RETRIEVING_PLAYER_DATA);
				}
			}

			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
			}
		});
	}
}
