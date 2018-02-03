package com.devsteady.onyx.game.event;

import com.devsteady.onyx.game.guns.BaseBullet;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event called whenever a {@link BaseBullet}, or any extension class, damages a(n) entity.
 * If cancelled, the entity in question will not be damaged.
 */
public class BulletHitCreatureEvent extends Event implements Cancellable {

    private final BaseBullet projectile;
    private LivingEntity target;
    private boolean cancelled = false;

    private static final HandlerList handlers = new HandlerList();

    public BulletHitCreatureEvent(BaseBullet projectile, LivingEntity target) {
        this.projectile = projectile;
        this.target = target;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * @return bullet used to damage the target.
     */
    public BaseBullet getProjectile() {
        return projectile;
    }

    /**
     * @return entity taking damage from the bullet.
     */
    public LivingEntity getTarget() {
        return target;
    }

    /**
     * Check whether or not the bullet shot comes from a gun!
     * Note: If the bullet doesn't have a gun, this event will not automatically handle damaging the entity, as it requires a source for the damage.
     * @return true if the bullet has a gun associated, false otherwise.
     */
    public boolean hasGun() {
        return projectile.getGun() != null;
    }

    public static void handle(BulletHitCreatureEvent e) {
        if (e.isCancelled()) {
            return;
        }

        LivingEntity target = e.getTarget();
        BaseBullet proj = e.getProjectile();

        if (!e.hasGun()) {
            return;
        }

        //Damage the target
        proj.getGun().damage(target, proj.getShooter());
    }
}
