package com.caved_in.commons.game.guns;

import com.caved_in.commons.block.BlockHitAction;
import com.caved_in.commons.entity.CreatureHitAction;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

public interface BulletActions extends CreatureHitAction, BlockHitAction {
	public void onHit(LivingEntity target);

	public void onHit(Block block);
}
