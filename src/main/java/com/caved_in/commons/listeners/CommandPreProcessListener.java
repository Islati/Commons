package com.caved_in.commons.listeners;

import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.player.PlayerWrapper;
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
		PlayerWrapper playerWrapper = Players.getData(player);
		if (playerWrapper.isInDebugMode()) {
			Debugger.debugCommandPreProcessEvent(player, event);
		}
	}
}
