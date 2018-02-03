package com.devsteady.onyx.game.clause;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface PlayerDamageEntityClause {
    boolean canDamage(Player player, LivingEntity entity);
}
