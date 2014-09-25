package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.tasks.UpdateOnlineStatusThread;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.UUID;

public class PlayerKickListener implements Listener {
	@EventHandler
	public void onPlayerKicked(PlayerKickEvent event) {
		if (!Commons.getConfiguration().getWorldConfig().hasJoinMessages()) {
			event.setLeaveMessage(null);
		}

		UUID playerId = event.getPlayer().getUniqueId();

		//Update the player's online status in the database
		Commons.getInstance().getThreadManager().runTaskAsync(new UpdateOnlineStatusThread(playerId, false));

		Players.removeData(playerId);
//		if (Commons.hasSqlBackend()) {
//			Commons.disguiseDatabase.deletePlayerDisguiseData(playerName);
//		}
	}
}
