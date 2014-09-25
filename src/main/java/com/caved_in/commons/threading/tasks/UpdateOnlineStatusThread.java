package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class UpdateOnlineStatusThread extends BukkitRunnable {
	private static final String UNABLE_TO_UPDATE_MESSAGE = "Unable to execute SQL statements for updating a players online status. \n\tDatabase -> { Servers -> server_online }";

	private UUID id;
	private boolean online;

	public UpdateOnlineStatusThread(UUID id, boolean online) {
		this.id = id;
		this.online = online;
	}

	@Override
	public void run() {
		boolean updated = Commons.database.updateOnlineStatus(id, online);

		if (!updated) {
			Commons.getInstance().getLogger().severe(UNABLE_TO_UPDATE_MESSAGE);
		}
	}
}
