package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private WorldConfiguration worldConfig;

    private Players playerHandler;

    public EntityDamageListener() {
        worldConfig = Commons.getInstance().getConfiguration().getWorldConfig();

        playerHandler = Commons.getInstance().getPlayerHandler();
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent e) {
        EntityDamageEvent.DamageCause cause = e.getCause();

        if (!worldConfig.hasFallDamage()) {
            /*
			As this listener is only registered when fall damage is disabled, we're only
			going to cancel the event when an entity is damaged via falling.
			 */
            if (cause == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
            }
        }

        Entity damaged = e.getEntity();
        if (!(damaged instanceof Player)) {
            return;
        }

        Player damagedPlayer = (Player) damaged;
        MinecraftPlayer mcPlayer = playerHandler.getData(damagedPlayer);

        if (mcPlayer.hasGodMode()) {
//			if (!damagedPlayer.hasPermission(Perms.COMMAND_GOD_MODE)) {
//				mcPlayer.setGodMode(false);
//				return;
//			}
            e.setCancelled(true);
        }
    }
}
