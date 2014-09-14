package com.caved_in.commons.game.event;

import com.caved_in.commons.game.guns.Bullet;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BulletHitCreatureEvent extends Event implements Cancellable {

	private final Bullet projectile;
	private LivingEntity target;
	private boolean cancelled = false;

	private static final HandlerList handlers = new HandlerList();

	public BulletHitCreatureEvent(Bullet projectile, LivingEntity target) {
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

	public Bullet getProjectile() {
		return projectile;
	}

	public LivingEntity getTarget() {
		return target;
	}

	public static void handle(BulletHitCreatureEvent e) {
		if (e.isCancelled()) {
			return;
		}

		LivingEntity target = e.getTarget();
		Bullet proj = e.getProjectile();

		//Damage the target
		proj.getGun().damage(target, proj.getShooter());
	}
}
