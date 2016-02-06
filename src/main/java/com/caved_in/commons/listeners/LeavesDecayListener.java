package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecayListener implements Listener {

	private Configuration config = null;

	public LeavesDecayListener() {
		config = Commons.getInstance().getConfiguration();
	}

	@EventHandler
	public void onLeavesDecay(LeavesDecayEvent e) {
		if (config.disableLeavesDecay()) {
			e.setCancelled(true);
		}
	}

}
