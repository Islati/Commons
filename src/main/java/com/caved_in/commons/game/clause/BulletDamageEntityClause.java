package com.caved_in.commons.game.clause;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * The BulletDamageEntityClause is used to determine whether or not a player is able to damage the given target.
 * The usage of these clauses is to provide flexibility in how damage is handled between players -> bullets -> other entities.
 */
public interface BulletDamageEntityClause {

    /**
     * Determine whether or not the player is able to damage the target with their bullet.
     * @param shooter player shooting the gun / using the bullets.
     * @param target entity being shot / damaged by the gun / bullets.
     * @return true if the shooter can damage the target, false otherwise.
     */
    boolean damage(Player shooter, LivingEntity target);
}
