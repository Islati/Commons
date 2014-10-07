package com.caved_in.commons.game.clause;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface BulletDamageEntityClause {
	public boolean damage(Player shooter, LivingEntity target);
}
