package com.caved_in.commons.game.guns;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.clause.BulletDamageEntityClause;
import com.caved_in.commons.game.event.BulletHitBlockEvent;
import com.caved_in.commons.game.event.BulletHitCreatureEvent;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.vector.Vectors;
import com.caved_in.commons.world.Worlds;
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

public abstract class BaseBullet implements Metadatable {

    public static final double BULLET_SCAN_RADIUS = 1.5;
    private static final Commons commons = Commons.getInstance();

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

    /* The item the gun shoots */
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

        Item bullet = Worlds.dropItem(p.getLocation(), itemStack);
        bullet.setPickupDelay(PICKUP_DELAY);
        bullet.setVelocity(Vectors.direction(p.getEyeLocation(), Players.getTargetLocation(p)).multiply(force * 2));

        item = bullet;
//
//        item.setVelocity(direction);
//        item.setPickupDelay(PICKUP_DELAY);

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
            If the item is on the ground, then we're done!
			Remove the item and break the loop!
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
        Commons.getInstance().getThreadManager().runTaskLater(() -> {
            item.remove();
        }, 40 - (long) (getGun().bulletProperties().speed * 2));
    }

    public double getDamage() {
        return damage;
    }

    public Player getShooter() {
        return Players.getPlayer(shooter);
    }

    public Gun getGun() {
        return gun;
    }

    protected ItemStack getItemStack() {
        return itemStack;
    }

    protected Item getItem() {
        return item;
    }

    protected static Random getRandom() {
        return random;
    }

    public double getSpread() {
        return spread;
    }

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
