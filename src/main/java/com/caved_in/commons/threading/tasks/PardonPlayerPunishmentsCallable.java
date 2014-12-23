package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.bans.PunishmentType;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.player.Players;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.UUID;
import java.util.concurrent.Callable;

public class PardonPlayerPunishmentsCallable implements Callable<Boolean> {
	private String playerPardonName;
	private boolean console = false;
	private UUID callingPlayer;
	private PunishmentType type;

	public PardonPlayerPunishmentsCallable(String playerName, PunishmentType type) {
		this.playerPardonName = playerName;
		this.type = type;
	}

	@Override
	public Boolean call() throws Exception {
		final boolean[] success = new boolean[1];
		ListenableFuture<UUID> getPlayerId = Commons.getInstance().getAsyncExecuter().submit(new GetPlayerUuidCallable(playerPardonName));
		Futures.addCallback(getPlayerId, new FutureCallback<UUID>() {
			@Override
			public void onSuccess(UUID uuid) {
				if (uuid == null) {
					Chat.debug("Cant get UUID for player " + playerPardonName);
					return;
				}

				if (!Players.hasPlayed(uuid)) {
					String neverPlayedMessage = Messages.playerNeverPlayed(playerPardonName);
					if (console) {
						Chat.debug(neverPlayedMessage);
					} else {
						Chat.message(Players.getPlayer(callingPlayer), neverPlayedMessage);
					}
					success[0] = false;
					return;
				}

				if (Players.hasActivePunishment(uuid, type)) {
					Commons.getInstance().getServerDatabase().pardonActivePunishments(uuid);
				}
				success[0] = true;
			}

			@Override
			public void onFailure(Throwable throwable) {
				if (console) {
					Chat.messageConsole(Messages.ERROR_RETRIEVING_PLAYER_DATA);
				} else {
					Chat.sendMessage(Players.getPlayer(callingPlayer), Messages.ERROR_RETRIEVING_PLAYER_DATA);
				}
				success[0] = false;
				Chat.messageConsole(throwable.getMessage());
			}
		}, Commons.getInstance().getAsyncExecuter());
		return success[0];
	}

	public void setConsole(boolean console) {
		this.console = console;
	}

	public void setPardoningPlayer(UUID callingPlayer) {
		this.callingPlayer = callingPlayer;
	}
}
