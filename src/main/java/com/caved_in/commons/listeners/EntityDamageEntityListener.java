package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.event.PlayerDamagePlayerEvent;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.game.item.Weapon;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

public class EntityDamageEntityListener implements Listener {

    private Players playerDataHandler = null;

    public EntityDamageEntityListener() {
        playerDataHandler = Commons.getInstance().getPlayerHandler();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
        Entity attacker = e.getDamager();
        Entity attacked = e.getEntity();

        Player player = null;
        Player pAttacked = null;

        /*
        Check if there was an arrow shot, and the shooter was a player, then we assign
        the player (damaging) to the shooter.
         */
        if (e.getEntityType() == EntityType.ARROW) {
            Arrow arrow = (Arrow) attacker;
            ProjectileSource source = arrow.getShooter();

            if (source == null) {
                return;
            }

            if (!(source instanceof LivingEntity)) {
                return;
            }

            LivingEntity shooter = (LivingEntity) source;
            if (shooter instanceof Player) {
                player = (Player) shooter;
            }
        }

        /*
        If the damaging entity was a snowball then we're going to get the player whom
        shot the snowball and assign them as the attacking player, rightfully so!
         */
        if (e.getEntityType() == EntityType.SNOWBALL) {
            Snowball snowball = (Snowball) attacker;
            ProjectileSource shooter = snowball.getShooter();

            if (shooter == null) {
                return;
            }

            if (!(shooter instanceof Player)) {
                return;
            }

            player = (Player) shooter;
        }

        /*
        Check if the attacker in the event was a player
         */
        if (attacker instanceof Player) {
            player = (Player) attacker;
        }

        /*
        Check if the attacked entity was a player.
         */
        if (attacked instanceof Player) {
            pAttacked = (Player) attacked;
        }

        /*
        Below we're checking if the player attacked is in GodMode, and if they are
        then stop damage!
         */
        if (pAttacked != null && playerDataHandler.getData(pAttacked).hasGodMode()) {
            e.setCancelled(true);
            return;
        }

        //Assure that we've got a player attacking, and a living entity was attacked.
        if (player == null || !(attacked instanceof LivingEntity)) {
            return;
        }

        /*
        If we have both an attacking player, and an attacked player then we've got a pvp event,
        used to remove the boiler-plating when all you really want is when a player damages a
        player! Heyyyyyoooooo custom events.
         */
        if (pAttacked != null) {
            //There's both an attacking and attacked player, so create the pvp event!
            PlayerDamagePlayerEvent pvpEvent = new PlayerDamagePlayerEvent(player, pAttacked, e.getDamage(), e.getFinalDamage(), e.getCause());
            //Call the pvp event
            Plugins.callEvent(pvpEvent);
            //If the pvp event was cancelled, then quit while we're ahead (and cancel this event)
            if (pvpEvent.isCancelled()) {
                e.setCancelled(true);
                return;
            }

            /*
            Re-assign the damage which is being dealt, as it could have been modified.
             */
            e.setDamage(pvpEvent.getDamage());
        }
    }
}
