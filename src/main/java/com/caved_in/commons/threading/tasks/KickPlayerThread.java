package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.player.Players;

import java.util.UUID;

/**
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <brandon@caved.in> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Brandon Curtis.
 * ----------------------------------------------------------------------------
 */
public class KickPlayerThread implements Runnable {
	private UUID id;
	private String reason;

	public KickPlayerThread(UUID id, String reason) {
		this.id = id;
		this.reason = reason;
	}

	@Override
	public void run() {
		Players.kick(Players.getPlayer(id), reason);
	}
}
