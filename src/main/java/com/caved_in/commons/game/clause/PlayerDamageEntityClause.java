package com.caved_in.commons.game.clause;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface PlayerDamageEntityClause {
    public boolean canDamage(Player player, LivingEntity entity);
}
