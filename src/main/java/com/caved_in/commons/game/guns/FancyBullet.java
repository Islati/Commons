package com.caved_in.commons.game.guns;

import com.caved_in.commons.effect.ParticleEffects;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.game.clause.BulletDamageEntityClause;
import com.caved_in.commons.game.event.BulletHitBlockEvent;
import com.caved_in.commons.game.event.BulletHitCreatureEvent;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.time.BasicTicker;
import com.caved_in.commons.utilities.NumberUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Set;
import java.util.UUID;

public class FancyBullet extends Bullet {
	private ParticleEffects effect;

	private BasicTicker ticker = new BasicTicker(10);

	private BulletDamageEntityClause damageConditions = null;

	public FancyBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, Vector spread, ParticleEffects effect) {
		super(shooter, gun, item, force, damage, spread);
		this.effect = effect;
	}

	public FancyBullet(UUID shooter, Gun gun, ItemStack item, double force, double damage, Vector spread, ParticleEffects effect, BulletDamageEntityClause damageConditions) {
		super(shooter, gun, item, force, damage, spread, damageConditions);
		this.effect = effect;
		this.damageConditions = damageConditions;
	}

	public FancyBullet(Player shooter, Gun gun, ItemStack item, double force, double damage, Vector spread, ParticleEffects effect) {
		super(shooter, gun, item, force, damage, spread);
		this.effect = effect;
	}

	@Override
	public void fire() {
		Player p = getShooter();
		UUID shooterId = p.getUniqueId();

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

		Item item = getItem();

		for (int i = 0; i < 100; i++) {
			double modX = px + i * x;
			double modY = py + i * z;
			double modZ = pz + i * y;

			Location l = new Location(pw, modX, modY, modZ);

			ParticleEffects.sendToLocation(effect, l, NumberUtil.getRandomInRange(3, 5));

			//Keep the item along the path?
			item.setVelocity(l.getDirection());

			Block locBlock = l.getBlock();

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
				item.remove();
				return;
			}

			Set<LivingEntity> entities = Entities.getLivingEntitiesNear(item, BULLET_SCAN_RADIUS);

			//If we've got no entities near us, no use to check!
			if (entities.size() == 0) {
				if (!ticker.allow()) {
					ticker.tick();
				} else {
					item.remove();
				}
				return;
			}

			for (LivingEntity entity : entities) {
				if (entity.getUniqueId().equals(shooterId)) {
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
}
