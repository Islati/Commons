package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodChangeListener implements Listener {

	private WorldConfiguration worldConfig;

	public FoodChangeListener() {
		worldConfig = Commons.getInstance().getConfiguration().getWorldConfig();
	}

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (worldConfig.isFoodChangeEnabled()) {
			return;
		}
		event.setCancelled(true);
	}
}
