package com.caved_in.commons.game.guns;

import com.caved_in.commons.Commons;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.event.BulletHitBlockEvent;
import com.caved_in.commons.game.event.BulletHitCreatureEvent;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.time.BasicTicker;
import com.caved_in.commons.vector.Vectors;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class Bullet extends BukkitRunnable implements Metadatable {
	private static final double BULLET_SCAN_RADIUS = 1.5;
	private static final Commons commons = Commons.getInstance();

	private static final int PICKUP_DELAY = 999999;

	private Map<String, List<MetadataValue>> metadata = new HashMap<>();
	private UUID shooter;
	private Item item;
	private double damage;
	private Gun gun;

	private BasicTicker ticker = new BasicTicker(10);


	public Bullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, Vector spread) {
		this(Players.getPlayer(shooter), gun, item, force, damage, spread);
	}

	public Bullet(Player shooter, Gun gun, ItemStack item, double force, double damage, Vector spread) {
		this.shooter = shooter.getUniqueId();
		this.gun = gun;

		Location loc = shooter.getLocation().add(0, 1, 0);
//		loc.setY(0.05);

		Item droppedItem = Worlds.dropItem(loc/*.add(0,0.5,0)*/, item);
//		droppedItem.setVelocity(loc.getDirection().normalize().multiply(force));

		/* New method of shooting & shits */
		Vector direction = Vectors.direction(shooter.getLocation(), Players.getTargetLocation(shooter)).multiply(force);
		droppedItem.setVelocity(direction);
		droppedItem.setPickupDelay(PICKUP_DELAY);
		this.item = droppedItem;
		this.damage = damage;
		//Launch the vector
		runTaskTimer(commons, 1, 1);
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

	@Override
	public void run() {
		Location loc = item.getLocation();

		Block blockBeneath = loc.getBlock().getRelative(BlockFace.DOWN);
		//If the item we're throwing hit something, then stop it from going else-where.
		if (blockBeneath.getType() != Material.AIR) {
			//Call the bullet hit-block event, as bullets are hitting blocks here!
			BulletHitBlockEvent event = new BulletHitBlockEvent(this, blockBeneath);
			Plugins.callEvent(event);
			BulletHitBlockEvent.handle(event);

			cancel();
			item.remove();
			return;
		}

		if (item.isOnGround()) {
			cancel();
			item.remove();
		}

		Set<LivingEntity> entities = Entities.getLivingEntitiesNear(item, BULLET_SCAN_RADIUS);

		//If we've got no entities near us, no use to check!
		if (entities.size() == 0) {
			if (!ticker.allow()) {
				ticker.tick();
			} else {
				item.remove();
				cancel();
			}
			return;
		}

		for (LivingEntity entity : entities) {
			if (entity.getUniqueId().equals(shooter)) {
				continue;
			}

			//Call the bullet hit creature event, because they hit some unfortunate being ;)
			BulletHitCreatureEvent event = new BulletHitCreatureEvent(this, entity);
			Plugins.callEvent(event);
			BulletHitCreatureEvent.handle(event);

			item.remove();
			cancel();
			return;
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

	protected Item getItem() {
		return item;
	}
}
