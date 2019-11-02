package com.caved_in.commons.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class LauncherListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();
        Material playerBlock = playerLocation.getWorld().getBlockAt(playerLocation).getType();
        switch (playerBlock) {
            case STONE_PRESSURE_PLATE:
            case ACACIA_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
                player.setVelocity(playerLocation.getDirection().multiply(3));
                player.setVelocity(new Vector(player.getVelocity().getX(), 1.0D, player.getVelocity().getZ()));
                break;
            default:
                break;
        }
    }
}
