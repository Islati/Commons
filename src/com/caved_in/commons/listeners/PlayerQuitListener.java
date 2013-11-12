package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.handlers.Friends.FriendHandler;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener
{
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent Event)
	{
		String playerName = Event.getPlayer().getName();
		if (!Commons.getConfiguration().getWorldConfig().isJoinLeaveMessagesEnabled())
		{
			Event.setQuitMessage(null);
		}
		PlayerHandler.removeData(playerName);
		FriendHandler.removeFriendList(playerName);
		Commons.disguiseDatabase.deletePlayerDisguiseData(playerName);
	}
}
