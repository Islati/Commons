package com.devsteady.onyx.game.event;

import com.devsteady.onyx.game.guns.BaseBullet;
import com.devsteady.onyx.game.guns.Gun;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event that's called whenever a(n) {@link BaseBullet} or any derived class hits a block.
 */
public class BulletHitBlockEvent extends Event implements Cancellable {
    private static HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private BaseBullet projectile;
    private Block block;

    public BulletHitBlockEvent(BaseBullet projectile, Block block) {
        this.projectile = projectile;
        this.block = block;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public BaseBullet getProjectile() {
        return projectile;
    }

    public Block getBlock() {
        return block;
    }

    public Player getShooter() {
        return projectile.getShooter();
    }

    public static void handle(BulletHitBlockEvent e) {
        if (e.isCancelled()) {
            return;
        }

        BaseBullet bullet = e.getProjectile();

        Gun gun = bullet.getGun();

        if (gun == null) {
            return;
        }

        if (e.getBlock() == null) {
            throw new NullPointerException("Block for BulletHitBlockEvent is null");
        }

        if (e.getShooter() == null) {
            throw new NullPointerException("Shooter for BulletHitBlockEvent is null");
        }

        if (gun.getBulletActions() == null) {
            throw new NullPointerException("Bullet Actions for gun is null");
        }

        gun.getBulletActions().onHit(e.getShooter(), e.getBlock());
    }
}
