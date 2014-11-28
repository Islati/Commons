package com.caved_in.commons.game.guns;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.clause.BulletDamageEntityClause;
import com.caved_in.commons.game.event.BulletHitBlockEvent;
import com.caved_in.commons.game.event.BulletHitCreatureEvent;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.time.BasicTicker;
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
import org.bukkit.util.Vector;

import java.util.*;

public class Bullet implements Metadatable {
    public static final double BULLET_SCAN_RADIUS = 1.5;
    private static final Commons commons = Commons.getInstance();

    private static final int PICKUP_DELAY = 999999;

    private Map<String, List<MetadataValue>> metadata = new HashMap<>();
    private UUID shooter;
    private double damage;
    private Gun gun;

    private ItemStack itemStack;

    private double force;

    private Item item;

    private BulletDamageEntityClause damageConditions = null;

    public Bullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, Vector spread, BulletDamageEntityClause damageConditions) {
        this(Players.getPlayer(shooter), gun, item, force, damage, spread);
        this.damageConditions = damageConditions;
    }

    public Bullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, Vector spread) {
        this(Players.getPlayer(shooter), gun, item, force, damage, spread);
    }

    public Bullet(Player shooter, Gun gun, ItemStack item, double force, double damage, Vector spread) {
        this(shooter, gun, item, force, damage);

        //Launch the vector
//		runTaskTimer(commons, 1, 1);
    }

    public Bullet(Player shooter, Gun gun, ItemStack item, double force, double damage) {
        this.shooter = shooter.getUniqueId();
        this.gun = gun;
        this.force = force;
        this.itemStack = item;
        this.damage = damage;
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

        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);

        World pw = p.getWorld();

        this.item = Worlds.dropItem(p.getLocation(), itemStack);
        this.item.setPickupDelay(PICKUP_DELAY);
        this.item.setVelocity(p.getLocation().getDirection().multiply(10));

//
//        item.setVelocity(direction);
//        item.setPickupDelay(PICKUP_DELAY);

        BasicTicker ticker = new BasicTicker(7);

        for (int i = 0; i < 100; i++) {
            double modX = px + i * x;
            double modY = py + i * z;
            double modZ = pz + i * y;

            Location l = new Location(pw, modX, modY, modZ);

            Block locBlock = l.getBlock();
            item.setVelocity(l.getDirection());

            if (ticker.allow()) {
                ParticleEffects.sendToLocation(ParticleEffects.INSTANT_SPELL, l, 2);
                ticker.reset();
            } else {
                ticker.tick();
            }

			/*
            If the bullet hits a block, and the block is solid, then we want to
			fire a BulletHitBlockEvent with the block that was hit!
			 */
            if (locBlock.getType().isSolid()) {
                BulletHitBlockEvent event = new BulletHitBlockEvent(this, locBlock);
                Plugins.callEvent(event);
                BulletHitBlockEvent.handle(event);
                item.remove();
                break;
            }

			/*
            If the item is on the ground, then we're done!
			Remove the item and break the loop!
			 */
            if (item.isOnGround()) {
                Chat.debug("Bullet is on the ground!");
                item.remove();
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

                item.remove();
                return;
            }
        }

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

//    protected Item getItem() {
//        return item;
//    }
}
