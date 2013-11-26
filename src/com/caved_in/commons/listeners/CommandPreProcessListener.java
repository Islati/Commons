package com.caved_in.commons.listeners;

import com.caved_in.commons.commands.CommandMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcessListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void CommandPreProcess(PlayerCommandPreprocessEvent Event) {
		String Command = Event.getMessage().toLowerCase();
		if (Command.startsWith("/plugins") || Command.startsWith("/pl") || Command.startsWith("/?") || Command.startsWith("/version")) {
			if (!Event.getPlayer().isOp()) {
				Event.getPlayer().sendMessage(CommandMessage.Deny.getMessage());
				Event.setCancelled(true);
			}
		}
	}


}
