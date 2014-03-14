package com.caved_in.commons.listeners;

import com.caved_in.commons.commands.CommandMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcessListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage().toLowerCase();
		if (command.startsWith("/plugins") || command.startsWith("/pl") || command.startsWith("/?") || command.startsWith("/version")) {
			if (!event.getPlayer().isOp()) {
				event.getPlayer().sendMessage(CommandMessage.Deny.getMessage());
				event.setCancelled(true);
			}
		}
	}


}
