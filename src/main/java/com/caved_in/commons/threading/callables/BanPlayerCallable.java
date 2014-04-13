package com.caved_in.commons.threading.callables;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;
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
public class BanPlayerCallable implements Callable<Boolean> {
	private String playerName;
	private UUID playerId;
	private boolean lookup = false;
	private boolean error = false;
	private Punishment punishment;

	public BanPlayerCallable(String playerName, Punishment punishment) {
		this.playerName = playerName;
		this.punishment = punishment;
		lookup = true;
	}

	public BanPlayerCallable(UUID playerId, Punishment punishment) {
		this.playerId = playerId;
		this.punishment = punishment;
	}

	@Override
	public Boolean call() throws Exception {
		if (!lookup) {
			Commons.playerDatabase.insertPlayerBan(punishment, playerId);
			return true;
		}

		ListenableFuture<String> getPlayerUuid = Commons.asyncExecutor.submit(new RetrievePlayerUuid(playerName));
		Futures.addCallback(getPlayerUuid, new FutureCallback<String>() {
			@Override
			public void onSuccess(String s) {
				playerId = UUID.fromString(s);
				Commons.playerDatabase.insertPlayerBan(punishment, playerId);
			}

			@Override
			public void onFailure(Throwable throwable) {
				throwable.printStackTrace();
				error = true;
			}
		});
		return !error;
	}
}
