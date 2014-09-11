package com.caved_in.commons.entity;

import com.caved_in.commons.Commons;
import com.caved_in.commons.inventory.ArmorInventory;
import com.caved_in.commons.inventory.ArmorSlot;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.warp.Warp;
import com.google.common.collect.Sets;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Simplifies both tedious and complex actions related to Entities.
 */
public class Entities {

	/**
	 * Spawn entities of the given type at the specified location
	 *
	 * @param entityType type of entity to spawn
	 * @param location   location at which to spawn the entities
	 * @param amount     amount of entities to spawn
	 * @return a set of the entities which were spawned
	 */
	public static Set<LivingEntity> spawnLivingEntity(EntityType entityType, Location location, int amount) {
		Set<LivingEntity> entities = new HashSet<>();
		//Loop and spawn enties until the amount requested has been spawned
		for (int i = 0; i < amount; i++) {
			entities.add(spawnLivingEntity(entityType, location));
		}
		return entities;
	}

	/**
	 * Spawn an entity at a specific location
	 *
	 * @param entityType the type of entity to spawn
	 * @param location   location at which to spawn the entity
	 * @return the entity spawned
	 */
	public static LivingEntity spawnLivingEntity(EntityType entityType, Location location) {
		return (LivingEntity) location.getWorld().spawnEntity(location, entityType);
	}

	/**
	 * Spawn primed tnt at a specific location.
	 *
	 * @param location location at which to spawn tnt.
	 * @return the primed tnt.
	 */
	public static TNTPrimed spawnPrimedTnt(Location location) {
		return location.getWorld().spawn(location, TNTPrimed.class);
	}

	/**
	 * Spawn a zombie at a specific location.
	 *
	 * @param location   location to spawn the zombie at.
	 * @param isBaby     whether or not the zombie is a baby.
	 * @param isVillager whether or not the zombie is a villager.
	 * @return the zombie that was spawned.
	 */
	public static Zombie spawnZombie(Location location, boolean isBaby, boolean isVillager) {
		Zombie zombie = (Zombie) spawnLivingEntity(EntityType.ZOMBIE, location);
		zombie.setBaby(isBaby);
		zombie.setVillager(isVillager);
		return zombie;
	}

	/**
	 * Spawn a zombie pigman at a specific location.
	 *
	 * @param location location to spawn the pig zombie at.
	 * @param isBaby   whether or not the pig zombie is a baby.
	 * @return the pig zombie that was spawned.
	 */
	public static PigZombie spawnPigZombie(Location location, boolean isBaby) {
		PigZombie pigZombie = (PigZombie) spawnLivingEntity(EntityType.PIG_ZOMBIE, location);
		pigZombie.setBaby(isBaby);
		return pigZombie;
	}

	/**
	 * Spawn a baby zombie at a specific location
	 *
	 * @param location   location at which to spawn the baby zombie.
	 * @param isVillager whether or not the baby zombie is a villager
	 * @return the baby zombie that was spawned.
	 */
	public static Zombie spawnBabyZombie(Location location, boolean isVillager) {
		return spawnZombie(location, true, isVillager);
	}

	/**
	 * Spawn a random coloured sheep at a specific location.
	 *
	 * @param location location at which to spawn the sheep.
	 * @return the sheep that was spawned.
	 */
	public static Sheep spawnRandomSheep(Location location) {
		Sheep sheep = (Sheep) spawnLivingEntity(EntityType.SHEEP, location);
		sheep.setColor(Items.getRandomDyeColor());
		return sheep;
	}

	/**
	 * Get the color for a health bar based on the percentage of health an entity has.
	 *
	 * @param enemyHealthPercentage percent of remaining health the entity has.
	 * @return green if they have 66% or more remaining, yellow if it's between 35% and 65%, and red if it's below 35%.
	 */
	public static ChatColor getHealthBarColor(double enemyHealthPercentage) {
		ChatColor healthBarColor = ChatColor.GREEN;
		if (enemyHealthPercentage >= 35 && enemyHealthPercentage <= 65) {
			healthBarColor = ChatColor.YELLOW;
		} else if (enemyHealthPercentage < 35) {
			healthBarColor = ChatColor.RED;
		}
		return healthBarColor;
	}

	/**
	 * Get the color for a health bar based on the percentage of health an entity has.
	 *
	 * @param entity entity to get the health bar color for.
	 * @return green if they have 66% or more remaining, yellow if it's between 35% and 65%, and red if it's below 35%.
	 */
	public static ChatColor getHealthBarColor(Damageable entity) {
		return getHealthBarColor((entity.getHealth() / entity.getMaxHealth()) * 100);
	}

	/**
	 * Set the name of an entity.
	 *
	 * @param entity entity the change the name on
	 * @param name   name to give the entity
	 */
	public static void setName(LivingEntity entity, String name) {
		entity.setCustomName(name);
	}

	/**
	 * Set the name of an entity.
	 *
	 * @param entity    the entity to (re)name
	 * @param name      name to give the entity
	 * @param isVisible whether or not the name is visible
	 */
	public static void setName(LivingEntity entity, String name, boolean isVisible) {
		setName(entity, name);
		entity.setCustomNameVisible(isVisible);
	}

	/**
	 * Get the entities name (derived from their type), fully formatted and capitalized properly.
	 *
	 * @param entity the entity to get the name of
	 * @return the entities name fully formatted and capitalized properly.
	 */
	public static String getDefaultName(LivingEntity entity) {
		return getDefaultName(entity.getType());
	}

	/**
	 * Get the type of an entity fully formatted and capitalized.
	 *
	 * @param type type which to parse.
	 * @return type of the entity fully formatted and capitalized.
	 */
	public static String getDefaultName(EntityType type) {
		return WordUtils.capitalizeFully(type.name().toLowerCase().replace("_", " "));
	}

	/**
	 * Get the current health of an entity
	 *
	 * @param entity entity to get the health of
	 * @return the amount of health the entity has
	 */
	public static double getCurrentHealth(LivingEntity entity) {
		return entity.getHealth();
	}

	/**
	 * Apply potion effects to an entity
	 *
	 * @param entity  entity to apply the potion effects to
	 * @param effects effects to apply to the entity
	 */
	public static void addPotionEffect(LivingEntity entity, PotionEffect... effects) {
		for (PotionEffect effect : effects) {
			entity.addPotionEffect(effect);
		}
	}

	/**
	 * Apply a collection of potion effects to an entity
	 *
	 * @param entity  entity to apply the potion effects to
	 * @param effects effects to apply to the entity
	 */
	public static void addPotionEffects(LivingEntity entity, Collection<PotionEffect> effects) {
		entity.addPotionEffects(effects);
	}

	/**
	 * Set the health of an entity within the range of 0 and their maximum health.
	 *
	 * @param entity the entity to set the health of.
	 * @param health value to change their health to.
	 */
	public static void setHealth(LivingEntity entity, double health) {
		double maxHealth = getMaxHealth(entity);
		entity.setHealth(health <= maxHealth ? health : maxHealth);
	}

	/**
	 * Get the maximum health of an entity.
	 *
	 * @param entity entity to get the health of.
	 * @return the entities maximum health.
	 */
	public static double getMaxHealth(Damageable entity) {
		return entity.getMaxHealth();
	}

	/**
	 * Change the maximum health for an entity.
	 *
	 * @param entity entity to set the maximum health for.
	 * @param health value to change their health to.
	 */
	public static void setMaxHealth(LivingEntity entity, double health) {
		entity.setMaxHealth(health);
	}

	/**
	 * Change the active armor of an entity in the specified slot
	 * to be of the given ItemStack
	 * <p>
	 * This method doesn't check if the slot, or item, are valid items for the slot:
	 * It forces the items to be in the slot.
	 *
	 * @param entity entity to parent
	 * @param slot   slot to change on the entity
	 * @param item   item to parent the entity with at the armor slot chosen
	 */
	public static void setEquipment(LivingEntity entity, ArmorSlot slot, ItemStack item) {
		EntityEquipment inv = entity.getEquipment();
		switch (slot) {
			case BOOTS:
				inv.setBoots(item);
				break;
			case CHEST:
				inv.setChestplate(item);
				break;
			case HELMET:
				inv.setHelmet(item);
				break;
			case LEGGINGS:
				inv.setLeggings(item);
				break;
			case WEAPON:
				inv.setItemInHand(item);
				break;
			default:
				break;
		}
	}

	/**
	 * Equip an entity with the specified armor.
	 *
	 * @param entity    entity to parent.
	 * @param inventory armor to parent the entity with.
	 */
	public static void setEquipment(LivingEntity entity, ArmorInventory inventory) {
		inventory.getArmor().entrySet().forEach(entry -> Entities.setEquipment(entity, entry.getKey(), entry.getValue()));
	}

	/**
	 * @param entities
	 * @param inventory
	 */
	public static void setEquipment(Collection<? extends LivingEntity> entities, ArmorInventory inventory) {
		entities.forEach(inventory::equip);
	}

	/**
	 * Get an entity type by a string identifier.
	 *
	 * @param type type of entityType to get
	 * @return the entity type matched by the type parameter if found, and otherwise EntityType.UNKNOWN
	 */
	public static EntityType getTypeByName(String type) {
		String entityInput = type.toLowerCase().replace("_", "");
		EntityType entityType = MobType.getTypeByName(entityInput);
		if (entityType != null) {
			return entityType;
		} else {
			return EntityType.UNKNOWN;
		}
	}

	/**
	 * Cleans all the entities in every world (that isn't an npc or player)
	 */
	public static void cleanAllEntities() {
		for (World bukkitWorld : Bukkit.getWorlds()) {
			cleanAllEntities(bukkitWorld);
		}
	}

	/**
	 * Cleans all the entities in the given world
	 * that isn't an npc (citizens NPC) or a player
	 *
	 * @param world
	 */
	public static void cleanAllEntities(World world) {
		for (LivingEntity livingEntity : world.getLivingEntities()) {
			//If it's not a citizens NPC and it's not an NPC / Player
			if (!livingEntity.hasMetadata("NPC") && !(livingEntity instanceof HumanEntity)) {
				livingEntity.remove();
			}
		}
	}

	/**
	 * Clean all the entities in a world except the defined types (And players, and citizens NPC'S)
	 *
	 * @param world       world to clean of livingEntities
	 * @param entityTypes entityTypes to not remove
	 */
	public static void cleanAllEntitiesExcept(World world, EntityType... entityTypes) {
		Set<EntityType> eTypes = Sets.newHashSet(entityTypes);
		for (LivingEntity livingEntity : world.getLivingEntities()) {
			if (!eTypes.contains(livingEntity.getType())) {
				if (!livingEntity.hasMetadata("NPC") && !(livingEntity instanceof HumanEntity)) {
					livingEntity.remove();
				}
			}
		}
	}

	/**
	 * Simulate player knock-back on an entity
	 *
	 * @param entity
	 */
	public static void knockbackEntity(LivingEntity entity) {
		knockbackEntity(entity, -1);
	}

	/**
	 * Knock back en entity with a specified amount of force
	 *
	 * @param entity
	 * @param force
	 */
	public static void knockbackEntity(LivingEntity entity, int force) {
		entity.setVelocity(entity.getLocation().getDirection().multiply(force));
	}

	/**
	 * Force an entity to be removed safely, by spawning a thread to remove them one tick later.
	 *
	 * @param entity entity to remove safely.
	 */
	public static void removeEntitySafely(final LivingEntity entity) {
		Commons.getInstance().getThreadManager().runTaskOneTickLater(entity::remove);
	}

	/**
	 * @param world      world of the entity
	 * @param entityUUID UUID of the entity
	 * @return the entity with the unique id entered, or if it wasn't found, null
	 */
	public static LivingEntity getEntityByUUID(World world, UUID entityUUID) {
		for (LivingEntity entity : world.getLivingEntities()) {
			if (entity.getUniqueId().equals(entityUUID)) {
				return entity;
			}
		}
		return null;
	}

	/**
	 * Get all the living-entities within a bounding box centered around the given entity.
	 *
	 * @param entity entity to operate around.
	 * @param x      half the size of the box along the x axis.
	 * @param y      half the size of the box along the y axis.
	 * @param z      half the size of the box along the z axis.
	 * @return set of nearby living-entities
	 */
	public static Set<LivingEntity> getLivingEntitiesNear(Entity entity, double x, double y, double z) {
		Set<LivingEntity> entities = new HashSet<>();
		entity.getNearbyEntities(x, y, z).stream().filter(e -> e instanceof LivingEntity).forEach(e -> entities.add((LivingEntity) e));
		return entities;
	}

	/**
	 * Get all the living entities within a bounding box centered around the given entity.
	 *
	 * @param entity entity to operate around.
	 * @param radius half the size of the box along all axis.
	 * @return set of all nearby living entities.
	 */
	public static Set<LivingEntity> getLivingEntitiesNear(Entity entity, double radius) {
		return getLivingEntitiesNear(entity, radius, radius, radius);
	}

	/**
	 * Select all the living entities of the given types within a bounding box centered around the given entity.
	 *
	 * @param entity entity to operate around.
	 * @param x      half the size of the box along the x axis.
	 * @param y      half the size of the box along the y axis.
	 * @param z      half the size of the box along the z axis.
	 * @param types  type(s) of entities to search for.
	 * @return set of all nearby entities whose type is of those given.
	 */
	public static Set<Entity> selectEntitiesNear(Entity entity, double x, double y, double z, EntityType... types) {
		Set<Entity> entities = new HashSet<>();

		List<Entity> nearby = entity.getNearbyEntities(z, y, z);

		//If the types are null, they want all the entities near the location!
		if (types == null || types.length == 0) {
			entities = Sets.newHashSet(nearby);
			return entities;
		}

		Set<EntityType> typeSet = Sets.newHashSet(types);
		entities = nearby.stream().filter(e -> typeSet.contains(e.getType())).collect(Collectors.toSet());
		return entities;
	}

	/**
	 * Get all living entities within a radius of the location
	 *
	 * @param center location at which to search
	 * @param radius radius to scan around the location.
	 * @return a set of living entities which were near the location
	 */
	public static Set<LivingEntity> getLivingEntitiesNearLocation(Location center, int radius) {
		Set<LivingEntity> entities = new HashSet<>();
		for (LivingEntity entity : center.getWorld().getLivingEntities()) {
			if (Locations.isEntityInRadius(center, radius, entity)) {
				entities.add(entity);
			}
		}
		return entities;
	}

	/**
	 * Get all entities within a radius of the location
	 *
	 * @param center location at which to search
	 * @param radius radius to scan around the location.
	 * @return a set of entities around the location center
	 */
	public static Set<Entity> getEntitiesNearLocation(Location center, int radius) {
		Set<Entity> entities = new HashSet<>();
		for (Entity entity : center.getWorld().getEntities()) {
			if (Locations.isEntityInRadius(center, radius, entity)) {
				entities.add(entity);
			}
		}
		return entities;
	}

	/**
	 * Select entities of specific type(s) near a location.
	 *
	 * @param location location at which to search for entities
	 * @param radius   radius around the location to search entities
	 * @param types    type(s) of entities to search for
	 * @return a HashSet of any entity near the location which type matches those given
	 */
	public static Set<Entity> selectEntitiesNearLocation(Location location, int radius, EntityType... types) {
		Set<EntityType> validEntityTypes = Sets.newHashSet(types);
		return getEntitiesNearLocation(location, radius).stream().filter(e -> validEntityTypes.contains(e.getType())).collect(Collectors.toSet());
	}

	/**
	 * Select living entities of specific type(s) near a location.
	 *
	 * @param location location at which to search for entities
	 * @param radius   radius around the location to search entities
	 * @param types    type(s) of entities to search for
	 * @return a HashSet of all the living entities around the location whose type matches one of those given.
	 */
	public static Set<LivingEntity> selectLivingEntitiesNearLocation(Location location, int radius, EntityType... types) {
		Set<EntityType> validEntityTypes = Sets.newHashSet(types);
		return getLivingEntitiesNearLocation(location, radius).stream().filter(e -> validEntityTypes.contains(e.getType())).collect(Collectors.toSet());
	}

	/**
	 * Get all the items on the ground within a radius around the location.
	 *
	 * @param center location at which to search for items
	 * @param radius radius to scan around the location.
	 * @return a set of items that were found on the ground. If none were found, an empty hashset is returned.
	 */
	public static Set<Item> getDroppedItemsNearLocation(Location center, int radius) {
		Set<Entity> entities = getEntitiesNearLocation(center, radius);
		Set<Item> items = new HashSet<>();
		for (Entity entity : entities) {
			if (entity instanceof Item) {
				items.add((Item) entity);
			}
		}
		return items;
	}

	/**
	 * Reduce a given collection of entities to the desired entity types.
	 *
	 * @param entities collection of entities to search through
	 * @param types    type(s) of entity to search for
	 * @return a collection of livingentities which type matches those given.
	 */
	public static Set<LivingEntity> filterCollection(Collection<LivingEntity> entities, EntityType... types) {
		Set<EntityType> validTypes = Sets.newHashSet(types);
		return entities.stream().filter(e -> validTypes.contains(e.getType())).collect(Collectors.toSet());
	}

	/**
	 * Removes all entities from the collection which type matches any of those given.
	 *
	 * @param entities collection of entities to search through
	 * @param types    type(s) of entities to remove from the collection
	 * @return a filtered hash-set with all entities except those who's type matched.
	 */
	public static Set<LivingEntity> reduceCollection(Collection<LivingEntity> entities, EntityType... types) {
		Set<EntityType> invalidTypes = Sets.newHashSet(types);
		return entities.stream().filter(e -> !invalidTypes.contains(e.getType())).collect(Collectors.toSet());
	}

	/**
	 * Damage the entity by a specific amount
	 *
	 * @param target target to damage
	 * @param damage amount of damage to deal
	 */
	public static void damage(Damageable target, double damage) {
		target.damage(damage);
	}

	public static void damage(Damageable target, double damage, LivingEntity damager) {
		target.damage(damage, damager);
	}

	/**
	 * Burn the entity for a specific amount of ticks.
	 *
	 * @param target    target to burn/
	 * @param fireTicks amount of ticks the fire will last.
	 */
	public static void burn(Damageable target, int fireTicks) {
		target.setFireTicks(fireTicks);
	}

	/**
	 * Burns the target for a specific duration.
	 *
	 * @param target   target to burn
	 * @param amount   how long the target should burn; used with timeType to specify duration.
	 * @param timeType the unit of time used to determine how long the target burns for.
	 */
	public static void burn(Damageable target, int amount, TimeType timeType) {
		target.setFireTicks((int) TimeHandler.getTimeInTicks(amount, timeType));
	}

	/**
	 * For each target passed, an entity damage event with max damage is created; if the event isn't cancelled the
	 * target is killed.
	 *
	 * @param targets targets to kill.
	 * @see #kill(org.bukkit.entity.Damageable)
	 */
	public static void kill(Damageable... targets) {
		for (Damageable entity : targets) {
			kill(entity);
		}
	}

	/**
	 * Creates an entity damage event with max damage, if the event isn't cancelled the target is killed.
	 *
	 * @param target target to kill
	 */
	public static void kill(Damageable target) {
		EntityDamageEvent event = new EntityDamageEvent(target, EntityDamageEvent.DamageCause.CUSTOM, Double.MAX_VALUE);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) {
			return;
		}
		target.setHealth(0.0);
	}

	/**
	 * Remove all the active potion effects from the living entity
	 *
	 * @param entity entity to remove the potion effects from
	 */
	public static void removePotionEffects(LivingEntity entity) {
		for (PotionEffect effect : entity.getActivePotionEffects()) {
			entity.removePotionEffect(effect.getType());
		}
	}

	/**
	 * Stop the entity from burning
	 *
	 * @param entity entity to stop burning
	 * @see org.bukkit.entity.Entity#setFireTicks(int)
	 */
	public static void removeFire(Entity entity) {
		entity.setFireTicks(0);
	}

	/**
	 * Restore the living entity health back to max.
	 *
	 * @param livingEntity the entity to heal.
	 */
	public static void restoreHealth(LivingEntity livingEntity) {
		setHealth(livingEntity, getMaxHealth(livingEntity));
	}

	/**
	 * Teleport the entity to the target location
	 *
	 * @param entity         entity to teleport
	 * @param targetLocation the location to bring the entity to
	 */
	public static void teleport(Entity entity, Location targetLocation) {
		entity.teleport(targetLocation);
	}

	/**
	 * Teleport the entity to the target warp
	 *
	 * @param entity the entity to teleport
	 * @param warp   the warp to bring the entity to
	 */
	public static void teleport(Entity entity, Warp warp) {
		teleport(entity, warp.getLocation());
	}

	/**
	 * Pull an entity to a location, as if they were metals under a magnet.
	 *
	 * @param e   entity to pull
	 * @param loc location to bring the entity towards.
	 */
	public static void pullEntityToLocation(final Entity e, Location loc) {
		Location entityLoc = e.getLocation();

		entityLoc.setY(entityLoc.getY() + 0.5);
		e.teleport(entityLoc);

		double g = -0.08;
		double d = loc.distance(entityLoc);
		double t = d;
		double v_x = (1.0 + 0.07 * t) * (loc.getX() - entityLoc.getX()) / t;
		double v_y = (1.0 + 0.03 * t) * (loc.getY() - entityLoc.getY()) / t - 0.5 * g * t;
		double v_z = (1.0 + 0.07 * t) * (loc.getZ() - entityLoc.getZ()) / t;

		org.bukkit.util.Vector v = e.getVelocity();
		v.setX(v_x);
		v.setY(v_y);
		v.setZ(v_z);
		e.setVelocity(v);
	}
}