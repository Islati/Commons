package com.caved_in.commons.threading.tasks;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdatePlayerPremiumCallable implements Callable<Boolean> {
	private static final Logger logger = Commons.getInstance().getLogger();

	private UUID playerId;
	private boolean premium;
	private String playerName;
	private boolean fetch = false;

	public UpdatePlayerPremiumCallable(UUID playerId, boolean premium) {
		this.playerId = playerId;
		this.premium = premium;
		fetch = false;
	}

	public UpdatePlayerPremiumCallable(String playerName, boolean premium) {
		this.playerName = playerName;
		this.premium = premium;
		fetch = true;
	}

	@Override
	public Boolean call() throws Exception {
		boolean success;
		if (fetch) {
			playerId = Players.getUUIDFromName(playerName);
		}

		try {
			Commons.database.updatePlayerPremium(playerId, premium);
			success = true;
		} catch (SQLException e) {
			success = false;
			logger.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
		return success;
	}
}
