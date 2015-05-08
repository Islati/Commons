package com.caved_in.commons.game.guns;

import com.caved_in.commons.game.gadget.Gadget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * Extension of the {@link Gadget} interface used to provide a template for Guns, and the methods related.
 */
public interface Gun extends Gadget {

    /**
     * Operations to perform when an entity is damaged by a gun/projectile-shot by the player.
     * @param damaged entity damaged.
     * @param shooter player shooting the gun / damaging the entity.
     */
    void damage(LivingEntity damaged, Player shooter);

    /**
     * Operations to perform when the player shoots the gun.
     * @param shooter player shooting the gun.
     */
    void onFire(Player shooter);

    /**
     * Actions attached to the Bullets for the gun, used to handle contact with blocks, entities, etc.
     * @return bullet actions for the gun.
     */
    BulletActions getBulletActions();

    /**
     * Assign the bullet actions for the gun.
     * @param actions actions to assign to the bullets that are shot from this gun.
     */
    void setBulletActions(BulletActions actions);

    /**
     * Retrieve all the properties related to the given gun.
     * @return properties for this gun.
     */
    GunProperties properties();

    /**
     * Retrieve the {@link BulletProperties} for bullets shot with this gun.
     * @return
     */
    BulletProperties bulletProperties();

    /**
     * Assign the {@link GunProperties} for this gun.
     * @param attributes properties to assign to the gun.
     */
    void properties(GunProperties attributes);

    /**
     * Attempt to reload for the given player.
     * @param player player reloading the gun.
     * @return true if the player reloaded, false otherwise.
     */
    boolean reload(Player player);

    /**
     * Check whether or not the given player must reload their gun.
     * @param player player to check
     * @return true if the player has an empty clip (needs reload), false otherwise.
     */
    boolean needsReload(Player player);

    /**
     * @return amount of base damage the gun deals.
     */
    double damage();
}
