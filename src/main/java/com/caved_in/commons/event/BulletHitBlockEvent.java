package com.caved_in.commons.event;

import com.caved_in.commons.game.guns.Bullet;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BulletHitBlockEvent extends Event implements Cancellable {
	private static HandlerList handlers = new HandlerList();

	private boolean cancelled = false;

	private Bullet projectile;
	private Block block;

	public BulletHitBlockEvent(Bullet projectile, Block block) {
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

	public Bullet getProjectile() {
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

		e.projectile.getGun().getBulletActions().onHit(e.getBlock());
	}
}
