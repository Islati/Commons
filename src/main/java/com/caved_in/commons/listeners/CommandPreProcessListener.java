package com.caved_in.commons.listeners;

import com.caved_in.commons.commands.CommandMessage;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcessListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String command = event.getMessage().toLowerCase();
		if (command.startsWith("/plugins") || command.startsWith("/pl") || command.startsWith("/?") || command.startsWith("/version")) {
			if (!player.isOp()) {
				Players.sendMessage(player, CommandMessage.Deny.getMessage());
				event.setCancelled(true);
			}
		}
	}


}
