package com.caved_in.commons.game.guns;

import com.caved_in.commons.exceptions.ProjectileCreationException;
import com.caved_in.commons.game.gadget.Gun;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.vector.Vectors;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

public class BulletBuilder {
	private ItemStack type;
	private Vector spread = null;
	private double force = 1;
	private UUID shooter;
	private double damage = 0;
	private Gun gun;

	private boolean hasLauncher = true;

	public BulletBuilder() {

	}

	public BulletBuilder(ItemStack type) {
		this.type = type.clone();
	}

	public BulletBuilder type(Material mat) {
		this.type = Items.makeItem(mat);
		return this;
	}

	public BulletBuilder type(ItemStack item) {
		this.type = item.clone();
		return this;
	}

	public BulletBuilder shooter(Player shooter) {
		this.shooter = shooter.getUniqueId();
		return this;
	}

	public BulletBuilder power(double force) {
		this.force = force;
		return this;
	}

	public BulletBuilder damage(double damage) {
		this.damage = damage;
		return this;
	}

	public BulletBuilder gun(Gun gun) {
		this.gun = gun;
		return this;
	}

	public BulletBuilder gunless() {
		hasLauncher = false;
		return this;
	}

	public BulletBuilder spread(Vector vector) {
		this.spread = vector;
		return this;
	}

	public BulletBuilder spread(Location loc, double amt) {
		return spread(Vectors.getSpread(loc, amt));
	}

	public Bullet shoot() throws ProjectileCreationException {

		if (shooter == null) {
			throw new ProjectileCreationException("Projectiles require a shooter");
//			return null;
		}

		if (hasLauncher && gun == null) {
			throw new ProjectileCreationException("All projectiles require a gun");
		}

		if (type == null || type.getType() == Material.AIR) {
			throw new ProjectileCreationException("Projectiles require a type");
		}

		return new Bullet(shooter, gun, type, force, damage, spread);
	}
}
