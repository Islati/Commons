package com.caved_in.commons.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e) {
		EntityDamageEvent.DamageCause cause = e.getCause();

		/*
		As this listener is only registered when fall damage is disabled, we're only
		going to cancel the event when an entity is damaged via falling.
		 */
		if (cause == EntityDamageEvent.DamageCause.FALL) {
			e.setCancelled(true);
		}
	}
}
