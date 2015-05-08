package com.caved_in.commons.game.guns;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.exceptions.ProjectileCreationException;
import com.caved_in.commons.game.clause.BulletDamageEntityClause;
import com.caved_in.commons.item.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BulletBuilder {
    private ItemStack type;
    private double spread = 0.1;
    private double force = 1;
    private UUID shooter;
    private double damage = 0;
    private Gun gun;

    private BulletDamageEntityClause damageConditions;

    private boolean hasLauncher = true;

    private ParticleEffects effect;

    public static BulletBuilder from(BulletProperties properties) {
        return new BulletBuilder(properties);
    }

    public BulletBuilder() {

    }

    public BulletBuilder(BulletProperties properties) {
        this.force = properties.speed;
        this.damage = properties.damage;
        this.damageConditions = properties.damageCondition;
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

    public BulletBuilder trail(ParticleEffects effect) {
        this.effect = effect;
        return this;
    }

    public BulletBuilder spread(double spread) {
        this.spread = spread;
        return this;
    }

    public Bullet shoot() throws ProjectileCreationException {

        Bullet bullet;

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

        if (effect != null) {
            bullet = new FancyBullet(shooter, gun, type, force, damage, spread, effect);
        } else {
            bullet = new Bullet(shooter, gun, type, force, damage, spread);
        }

        bullet.fire();

        return bullet;
    }
}
