package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodChangeListener implements Listener {

	public FoodChangeListener() {
	}

	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent event) {
		if (event.isCancelled()) {
			return;
		}

		if (Commons.getInstance().getConfiguration().enableFoodChange()) {
			return;
		}

		event.setCancelled(true);
	}
}
