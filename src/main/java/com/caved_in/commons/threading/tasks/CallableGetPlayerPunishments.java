package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
import com.caved_in.commons.bans.Punishment;

import java.util.Set;
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
public class CallableGetPlayerPunishments implements Callable<Set<Punishment>> {
	private UUID playerId;

	public CallableGetPlayerPunishments(UUID playerId) {
		this.playerId = playerId;
	}

	@Override
	public Set<Punishment> call() throws Exception {
		return Commons.database.getActivePunishments(playerId);
	}
}
