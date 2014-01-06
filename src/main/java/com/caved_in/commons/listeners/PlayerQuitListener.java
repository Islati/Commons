package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.friends.FriendHandler;
import com.caved_in.commons.player.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		String playerName = event.getPlayer().getName();
		if (!Commons.getConfiguration().getWorldConfig().isJoinLeaveMessagesEnabled()) {
			event.setQuitMessage(null);
		}
		PlayerHandler.removeData(playerName);
		FriendHandler.removeFriendList(playerName);
		Commons.disguiseDatabase.deletePlayerDisguiseData(playerName);
	}
}
