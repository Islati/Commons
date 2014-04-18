package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
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
public class CallableUpdatePlayerPremium implements Callable<Boolean> {
	private UUID playerId;
	private boolean premium;
	private String playerName;
	private boolean fetch;

	public CallableUpdatePlayerPremium(UUID playerId, boolean premium) {
		this.playerId = playerId;
		this.premium = premium;
	}

	public CallableUpdatePlayerPremium(String playerName, boolean premium) {
		this.playerName = playerName;
		this.premium = premium;
		fetch = true;
	}

	@Override
	public Boolean call() throws Exception {
		final UUID[] uuid = new UUID[1];
		final boolean[] success = new boolean[1];
		if (fetch) {
			ListenableFuture<UUID> getPlayerUuid = Commons.asyncExecutor.submit(new CallableGetPlayerUuid(playerName));
			Futures.addCallback(getPlayerUuid, new FutureCallback<UUID>() {
				@Override
				public void onSuccess(UUID id) {
					uuid[0] = id;
					success[0] = true;
				}

				@Override
				public void onFailure(Throwable throwable) {
					throwable.printStackTrace();
					success[0] = false;
				}
			});
		} else {
			uuid[0] = playerId;
			success[0] = true;
		}

		if (success[0]) {
			Commons.database.updatePlayerPremium(uuid[0], premium);
		}
		return success[0];
	}
}
