package com.caved_in.commons.game.clause;

import org.bukkit.entity.LivingEntity;

/**
 * A clause used to determine whether or not an entity can be damaged.
 */
public interface DamageEntityClause {

    /**
     * Determine whether or not the given entity is able to be damaged.
     * @param entity entity taking damage.
     * @return true if the entity can be damaged, false otherwise.
     */
    boolean damage(LivingEntity entity);
}
