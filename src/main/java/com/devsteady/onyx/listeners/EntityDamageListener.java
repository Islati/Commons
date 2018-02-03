package com.devsteady.onyx.listeners;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.config.Configuration;
import com.devsteady.onyx.player.MinecraftPlayer;
import com.devsteady.onyx.player.Players;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    private Configuration config;

    private Players playerHandler;

    public EntityDamageListener() {
        config = Onyx.getInstance().getConfiguration();

        playerHandler = Onyx.getInstance().getPlayerHandler();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageEvent(EntityDamageEvent e) {
        EntityDamageEvent.DamageCause cause = e.getCause();

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
