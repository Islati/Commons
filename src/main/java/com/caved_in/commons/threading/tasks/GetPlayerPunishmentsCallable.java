package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;

public class GetPlayerPunishmentsCallable implements Callable<Set<Punishment>> {
	private UUID playerId;

	public GetPlayerPunishmentsCallable(UUID playerId) {
		this.playerId = playerId;
	}

	@Override
	public Set<Punishment> call() throws Exception {
		return Commons.getInstance().getServerDatabase().getActivePunishments(playerId);
	}
}
