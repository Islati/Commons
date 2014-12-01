package com.caved_in.commons.game.event;

import com.caved_in.commons.game.guns.BaseBullet;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

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

	public BaseBullet getProjectile() {
		return projectile;
	}

	public LivingEntity getTarget() {
		return target;
	}

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
