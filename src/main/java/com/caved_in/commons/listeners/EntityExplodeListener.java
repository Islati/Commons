package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.fireworks.Fireworks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {
	private WorldConfiguration worldConfig;

	public EntityExplodeListener() {
		worldConfig = Commons.getInstance().getConfiguration().getWorldConfig();
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (worldConfig.hasExplosionFireworks()) {
			Fireworks.playRandomFirework(event.getLocation());
		}

		if (worldConfig.isBlockBreakEnabled()) {
			return;
		}
		//Clear all the blocks from the block list to assure they're not removed when exploding
		event.blockList().clear();
	}
}
