package com.caved_in.commons.game.guns;

import com.caved_in.commons.block.BlockHitAction;
import com.caved_in.commons.entity.CreatureHitAction;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

/**
 * A template to attach behaviours to when a {@link BaseBullet} implementation hits an entity, or a block.
 */
public interface BulletActions extends CreatureHitAction, BlockHitAction {
    /**
     * Actions to perform whenever a bullet hits the given entity.
     * @param player player who shot the bullet.
     * @param target target who was shot.
     */
    void onHit(Player player, LivingEntity target);

    /**
     * Actions to perform whenever a bullet hits the given block.
     * @param player player who shot the bullet.
     * @param block block that was shot.
     */
    void onHit(Player player, Block block);
}
