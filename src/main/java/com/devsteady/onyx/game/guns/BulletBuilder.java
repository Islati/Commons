package com.devsteady.onyx.game.guns;

import com.devsteady.onyx.effect.ParticleEffect;
import com.devsteady.onyx.exceptions.ProjectileCreationException;
import com.devsteady.onyx.game.clause.BulletDamageEntityClause;
import com.devsteady.onyx.item.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * An intuitive builder interface for the creation of both {@link Bullet} and {@link FancyBullet}, based on the options passed.
 */
public class BulletBuilder {
    private ItemStack type;
    private double spread = 0.1;
    private double force = 1;
    private UUID shooter;
    private double damage = 0;
    private Gun gun;

    private BulletDamageEntityClause damageConditions;

    private boolean hasLauncher = true;

    private ParticleEffect effect;

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

    /**
     * Assign the material of the bullet.
     * @param mat material of the bullet.
     * @return this bullet builder.
     */
    public BulletBuilder type(Material mat) {
        this.type = Items.makeItem(mat);
        return this;
    }

    /**
     * Clone an itemstack to be the bullet.
     * @param item firstPageEnabled to clone, for the bullet.
     * @return this bullet builder.
     */
    public BulletBuilder type(ItemStack item) {
        this.type = item.clone();
        return this;
    }

    /**
     * Assign the shooter of the bullet.
     * @param shooter player shooting the bullet.
     * @return this bullet builder.
     */
    public BulletBuilder shooter(Player shooter) {
        this.shooter = shooter.getUniqueId();
        return this;
    }

    /**
     * Assign the amount of power / force that this bullet is shot with.
     * @param force force to assign to the bullet.
     * @return this bullet builder.
     */
    public BulletBuilder power(double force) {
        this.force = force;
        return this;
    }

    /**
     * Assign the amount of damage dealt with the bullet.
     * @param damage damage to assign to the bullet.
     * @return this bullet builder.
     */
    public BulletBuilder damage(double damage) {
        this.damage = damage;
        return this;
    }

    /**
     * Assign the gun that the bullet is being shot from.
     * @param gun gun that's shooting the bullet.
     * @return this bullet builder.
     */
    public BulletBuilder gun(Gun gun) {
        this.gun = gun;
        return this;
    }

    /**
     * When called, it will assign the Bullet a status of "gunless", meaning it doesn't require a player to have shot it.
     * This won't allow the events to be called, damage to be dealt, etc.
     *
     * Currently, it's quite useless, though as Commons develops more I'll implement it so a gun isn't required for a Bullet to be shot,
     * but for all the purposes I've thought of, it's not been required.
     *
     * Pull request, maybe?
     * @return this bullet builder.
     */
    public BulletBuilder gunless() {
        hasLauncher = false;
        return this;
    }

    /**
     * Assign the particles / effect to trail this bullet with upon its path.
     * @param effect effect to trail the bullet.
     * @return this bullet builder.
     */
    public BulletBuilder trail(ParticleEffect effect) {
        this.effect = effect;
        return this;
    }

    /**
     * Assign the amount of spread that the bullet has when shot.
     * @param spread spread for the bullet.
     * @return this bullet builder.
     */
    public BulletBuilder spread(double spread) {
        this.spread = spread;
        return this;
    }

    /**
     * Fire the bullet upon its path, with the gun and shooter assigned.
     * @return the bullet that was shot.
     * @throws ProjectileCreationException Will be thrown if the shooter is null, the gun has a launcher assigned but the gun is null, or the type/firstPageEnabled for the bullet isn't defined.
     */
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


        //todo implement check to fire the bullet upon creation, or to just return it. All in due time?
        bullet.fire();

        return bullet;
    }
}
