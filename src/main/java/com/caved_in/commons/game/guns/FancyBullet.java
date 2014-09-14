package com.caved_in.commons.game.guns;

import com.caved_in.commons.effect.ParticleEffects;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

public class FancyBullet extends Bullet {
	private ParticleEffects effect;

	public FancyBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, Vector spread, ParticleEffects effect) {
		super(shooter, gun, item, force, damage, spread);
		this.effect = effect;
	}

	public FancyBullet(Player shooter, Gun gun, ItemStack item, double force, double damage, Vector spread, ParticleEffects effect) {
		super(shooter, gun, item, force, damage, spread);
		this.effect = effect;
	}

	@Override
	public void run() {
		super.run();
		ParticleEffects.sendToLocation(effect, getItem().getLocation(), 2);
	}
}
