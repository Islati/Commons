package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.fireworks.Fireworks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {
	private Configuration config;

	public EntityExplodeListener() {
		config = Commons.getInstance().getConfiguration();
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (config.hasExplosionFireworks()) {
			Fireworks.playRandomFirework(event.getLocation());
		}

		if (config.enableBlockBreak()) {
			return;
		}
		//Clear all the blocks from the block list to assure they're not removed when exploding
		event.blockList().clear();
	}
}
