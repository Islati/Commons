package com.caved_in.commons.threading.callables;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.player.Players;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class PardonPlayerPunishmentsTask implements Callable<Boolean> {
	private String playerPardonName;
	private boolean console = false;
	private UUID callingPlayer;
	private PunishmentType type;

	public PardonPlayerPunishmentsTask(String playerName, PunishmentType type) {
		this.playerPardonName = playerName;
		this.type = type;
	}

	@Override
	public Boolean call() throws Exception {
		final boolean[] success = new boolean[1];
		ListenableFuture<String> getPlayerId = Commons.asyncExecutor.submit(new RetrievePlayerUuid(playerPardonName));
		Futures.addCallback(getPlayerId, new FutureCallback<String>() {
			@Override
			public void onSuccess(String s) {
				final UUID uuid = UUID.fromString(s);
				if (!Players.hasPlayed(uuid)) {
					String neverPlayedMessage = Messages.playerNeverPlayed(playerPardonName);
					if (console) {
						Commons.messageConsole(neverPlayedMessage);
					} else {
						Players.sendMessage(Players.getPlayer(callingPlayer), neverPlayedMessage);
					}
					success[0] = false;
					return;
				}

				if (!Players.hasActivePunishment(uuid, PunishmentType.BAN)) {
					Commons.playerDatabase.pardonActivePunishments(uuid);
				}
				success[0] = true;
			}

			@Override
			public void onFailure(Throwable throwable) {
				if (console) {
					Commons.messageConsole(Messages.ERROR_RETRIEVING_PLAYER_DATA);
				} else {
					Players.sendMessage(Players.getPlayer(callingPlayer), Messages.ERROR_RETRIEVING_PLAYER_DATA);
				}
				success[0] = false;
			}
		}, Commons.asyncExecutor);
		return success[0];
	}

	public void setConsole(boolean console) {
		this.console = console;
	}

	public void setPardoningPlayer(UUID callingPlayer) {
		this.callingPlayer = callingPlayer;
	}
}
