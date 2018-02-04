package com.devsteady.onyx.game.guns;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.entity.Entities;
import com.devsteady.onyx.game.clause.BulletDamageEntityClause;
import com.devsteady.onyx.game.event.BulletHitBlockEvent;
import com.devsteady.onyx.game.event.BulletHitCreatureEvent;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.plugin.Plugins;
import com.devsteady.onyx.vector.Vectors;
import com.devsteady.onyx.world.Worlds;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;

import java.util.*;

/**
 * Base for all Bullets that are fired by {@link Gun} / {@link BaseGun}.
 * Handles the calculations of where the projectile will hit; Calls a {@link BulletHitBlockEvent} when hitting blocks,
 * or a {@link BulletHitCreatureEvent} event whenever a creature is damaged.
 *
 * Extending this class will allow you to implement your own behaviours for when a bullet travels over a location.
 *
 * Implementations of the class are {@link FancyBullet}, which is currently buggy- Though launches particle effects as it travels, and
 * the {@link Bullet} which is a bare-bones implementation of this class, though traces the path with particles on a very minimal level.
 */
public abstract class BaseBullet implements Metadatable {

    public static final double BULLET_SCAN_RADIUS = 1.5;
    private static final Onyx onyx = Onyx.getInstance();

    private static final Random random = new Random();

    private static final int PICKUP_DELAY = 999999;

    private Map<String, List<MetadataValue>> metadata = new HashMap<>();
    private UUID shooter;

    /* Properties of the gun */
    private double damage;
    private double force;
    private double spread;

    /* The parent object which this bullet's being made for */
    private Gun gun;

    private boolean gunless = false;

    /* The firstPageEnabled the gun shoots */
    private ItemStack itemStack;

    /* The bullet (entity) */
    private Item item;

    /* Damage conditions for this bullet; If the clause
    fails no damage is dealt / no action taken
     */
    private BulletDamageEntityClause damageConditions = null;

    public BaseBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, double spread, BulletDamageEntityClause damageConditions) {
        this(Players.getPlayer(shooter), gun, item, force, damage, spread);
        this.damageConditions = damageConditions;
    }

    public BaseBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, double spread) {
        this(Players.getPlayer(shooter), gun, item, force, damage, spread);
    }

    public BaseBullet(Player shooter, Gun gun, ItemStack item, double force, double damage, double spread) {
        this.shooter = shooter.getUniqueId();
        this.gun = gun;
        this.force = force;
        this.itemStack = item;
        this.damage = damage;
        this.spread = spread;
    }

    @Override
    public void setMetadata(String key, MetadataValue value) {
        if (!hasMetadata(key)) {
            metadata.put(key, Arrays.asList(value));
            return;
        }

        metadata.get(key).add(value);
    }

    @Override
    public List<MetadataValue> getMetadata(String key) {
        return metadata.get(key);
    }

    @Override
    public boolean hasMetadata(String key) {
        return metadata.containsKey(key);
    }

    @Override
    public void removeMetadata(String s, Plugin plugin) {
        metadata.remove(s);
    }

    /**
     * Fire the bullet upon it's path, upon the path the player is currently aiming.
     *
     * Before calling this method a player, gun, itemstack, force, damage, and spread ABSOLUTELY MUST
     * be assigned.
     *
     * This is set through instancing a new Bullet Class.
     */
    public void fire() {
        Player p = getShooter();

        Location eyeLoc = p.getEyeLocation();
        double px = eyeLoc.getX();
        double py = eyeLoc.getY();
        double pz = eyeLoc.getZ();
        double yaw = Math.toRadians(eyeLoc.getYaw() + 90.0f);
        double pitch = Math.toRadians(eyeLoc.getPitch() + 90.0f);

        //Generate the applicable spread on each axis (x,y,z)
        double[] appliableSpread = {
                (random.nextDouble() - random.nextDouble()) * spread * 0.1,
                (random.nextDouble() - random.nextDouble()) * spread * 0.1,
                (random.nextDouble() - random.nextDouble()) * spread * 0.1
        };

        /*
        Calculate the x,y, and z co-ords without spread applied!
         */
        double x = Math.sin(pitch) * Math.cos(yaw) + appliableSpread[0];
        double y = Math.sin(pitch) * Math.sin(yaw) + appliableSpread[1];
        double z = Math.cos(pitch) + appliableSpread[2];

        World pw = p.getWorld();

        Item bullet = Worlds.dropItem(p.getLocation(), itemStack,true);
        bullet.setPickupDelay(PICKUP_DELAY);
        bullet.setVelocity(Vectors.direction(p.getEyeLocation(), Players.getTargetLocation(p)).multiply(force * 2));

        item = bullet;
//
//        firstPageEnabled.setVelocity(direction);
//        firstPageEnabled.setPickupDelay(PICKUP_DELAY);

        int range = getGun().properties().range;

        for (int i = 0; i < range; i++) {
            double modX = px + i * x;
            double modY = py + i * z;
            double modZ = pz + i * y;

            Location l = new Location(pw, modX, modY, modZ);

            /* Call the on-travel implementation (Whichever it may be!) */
            onTravel(l);

            Block locBlock = l.getBlock();

			/*
            If the bullet hits a block, and the block is solid, then we want to
			fire a BulletHitBlockEvent with the block that was hit!
			 */
            if (locBlock.getType().isSolid()) {
                BulletHitBlockEvent event = new BulletHitBlockEvent(this, locBlock);
                Plugins.callEvent(event);
                BulletHitBlockEvent.handle(event);
                removeBullet();
                break;
            }

			/*
            If the firstPageEnabled is on the ground, then we're done!
			Remove the firstPageEnabled and break the loop!
			 */
            if (item.isOnGround()) {
                Chat.debug("Bullet is on the ground!");
                removeBullet();
                return;
            }

            Set<LivingEntity> entities = Entities.getLivingEntitiesNearLocation(l, 2);
            for (LivingEntity entity : entities) {
                if (entity.getUniqueId().equals(shooter)) {
                    continue;
                }

                if (damageConditions != null && !damageConditions.damage(p, entity)) {
                    continue;
                }

                //Call the bullet hit creature event, because they hit some unfortunate being ;)
                BulletHitCreatureEvent event = new BulletHitCreatureEvent(this, entity);
                Plugins.callEvent(event);
                BulletHitCreatureEvent.handle(event);
                removeBullet();
                return;
            }
        }
    }

    /*
    Removes bullets 1.5 seconds
     */
    private void removeBullet() {
        Onyx.getInstance().getThreadManager().runTaskLater(() -> {
            item.remove();
        }, 40 - (long) (getGun().bulletProperties().speed * 2));
    }

    public double getDamage() {
        return damage;
    }

    /**
     * Retrieve the player who's responsible for shooting the bullet.
     * @return player who's shooting the gun.
     */
    public Player getShooter() {
        return Players.getPlayer(shooter);
    }

    /**
     * Retrieve the {@link Gun} which this bullet is being fired from.
     * @return gun which this bulle tis being fired from.
     */
    public Gun getGun() {
        return gun;
    }

    protected ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Get the firstPageEnabled that the
     * @return
     */
    protected Item getItem() {
        return item;
    }

    protected static Random getRandom() {
        return random;
    }

    /**
     * Get the amount of spread that's applied to the Bullet.
     * @return amount of spread that's applied to the Bullet.
     */
    public double getSpread() {
        return spread;
    }

    /**
     * Change the firstPageEnabled that the bullet is attached to.
     * DO NOT EVER CALL THIS. EVER. I WILL FIND YOU. <3
     * @param i firstPageEnabled to assign.
     */
    protected void setItem(Item i) {
        this.item = i;
    }

    /**
     * Whenever a bullet traverses a location, this method is called.
     *
     * @param l location which the bullet is currently at.
     */
    public abstract void onTravel(Location l);


}
