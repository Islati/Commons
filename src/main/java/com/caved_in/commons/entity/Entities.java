package com.caved_in.commons.entity;

import com.caved_in.commons.Commons;
import com.caved_in.commons.entity.nms.NametagEntity;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.world.Worlds;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_7_R4.EntityTypes;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


public class Entities {

	/*
	 * Classes for various projectiles, retrieved by getting the NMS class
	 */
	public static final Class<?> ENTITY_SNOWBALL = ReflectionUtilities.getNMSClass("EntitySnowBall");
	public static final Class<?> ENTITY_EGG = ReflectionUtilities.getNMSClass("EntityEgg");
	public static final Class<?> ENTITY_ENDERPEARL = ReflectionUtilities.getNMSClass("EntityEnderPearl");
	public static final Class<?> ENTITY_ARROW = ReflectionUtilities.getNMSClass("EntityArrow");
	public static final Class<?> ENTITY_POTION = ReflectionUtilities.getNMSClass("EntityPotion");
	public static final Class<?> FIREBALL = ReflectionUtilities.getNMSClass("Fireball");
	public static final Class<?> ENTITY_SMALL_FIREBALL = ReflectionUtilities.getNMSClass("EntitySmallFireball");
	public static final Class<?> ENTITY_LARGE_FIREBALL = ReflectionUtilities.getNMSClass("EntityLargeFireball");
	public static final Class<?> ENTITY_WITHERSKULL = ReflectionUtilities.getNMSClass("EntityWitherSkull");

	private static final Method SET__POSITION_ROTATION = ReflectionUtilities.getMethod(ReflectionUtilities.getNMSClass("Entity"), "setPositionRotation", double.class, double.class, double.class, float.class, float.class);

	private static final Class<?> WORLD = ReflectionUtilities.getNMSClass("World");
	private static final Method ADD_ENTITY = ReflectionUtilities.getMethod(WORLD, "addEntity", ReflectionUtilities.getNMSClass("Entity"));

	private static final Method GET_BUKKIT_ENTITY = ReflectionUtilities.getMethod(ReflectionUtilities.getNMSClass("Entity"), "getBukkitEntity");

	public static Object getHandle(Entity entity) {
		return ReflectionUtilities.invokeMethod(ReflectionUtilities.getMethod(entity.getClass(), "getHandle"), entity);
	}

	public static void setPositionRotation(Object entityHandle, double x, double y, double z, float yaw, float pitch) {
		ReflectionUtilities.invokeMethod(SET__POSITION_ROTATION, x, y, z, yaw, pitch);
	}

	public static void addEntity(Object worldhandle, Object entityHandle) {
		ReflectionUtilities.invokeMethod(ADD_ENTITY, worldhandle, entityHandle);
	}

	public static Object invokeProjectile(Class<?> clazz, Object world) {
		if (clazz.isAssignableFrom(ReflectionUtilities.getNMSClass("EntityProjectile")) || world.getClass().isAssignableFrom(ReflectionUtilities.getNMSClass("World"))) {
			Constructor constructor = ReflectionUtilities.getConstructor(clazz, ReflectionUtilities.getNMSClass("World"));
			return ReflectionUtilities.invokeConstructor(constructor, world);
		} else {
			return null;
		}
	}

	public static <T> T getBukkitEntity(Object entityHandle) {
		if (entityHandle.getClass().isAssignableFrom(ReflectionUtilities.getNMSClass("Entity"))) {
			return ReflectionUtilities.invokeMethod(GET_BUKKIT_ENTITY, entityHandle);
		} else {
			return null;
		}
	}

	public static Set<LivingEntity> spawnLivingEntity(EntityType entityType, Location location, int amount) {
		Set<LivingEntity> entities = new HashSet<>();
		//Loop and spawn enties until the amount requested has been spawned
		for (int i = 0; i < amount; i++) {
			entities.add(spawnLivingEntity(entityType, location));
		}
		return entities;
	}

	public static LivingEntity spawnLivingEntity(EntityType entityType, Location location) {
		return (LivingEntity) location.getWorld().spawnEntity(location, entityType);
	}

	public static TNTPrimed spawnPrimedTnt(Location location) {
		return location.getWorld().spawn(location, TNTPrimed.class);
	}

	public static Zombie spawnZombie(Location location, boolean isBaby, boolean isVillager) {
		Zombie zombie = (Zombie) spawnLivingEntity(EntityType.ZOMBIE, location);
		zombie.setBaby(isBaby);
		zombie.setVillager(isVillager);
		return zombie;
	}

	public static PigZombie spawnPigZombie(Location location, boolean isBaby) {
		PigZombie pigZombie = (PigZombie) spawnLivingEntity(EntityType.PIG_ZOMBIE, location);
		pigZombie.setBaby(isBaby);
		return pigZombie;
	}

	public static Zombie spawnBabyZombie(Location location, boolean isVillager) {
		return spawnZombie(location, true, isVillager);
	}

	public static Sheep spawnRainbowSheep(Location location) {
		Sheep sheep = (Sheep) spawnLivingEntity(EntityType.SHEEP, location);
		sheep.setColor(Items.getRandomDyeColor());
		return sheep;
	}

	public static ChatColor getHealthBarColor(double enemyHealthPercentage) {
		ChatColor healthBarColor = ChatColor.GREEN;
		if (enemyHealthPercentage >= 35 && enemyHealthPercentage <= 65) {
			healthBarColor = ChatColor.YELLOW;
		} else if (enemyHealthPercentage < 35) {
			healthBarColor = ChatColor.RED;
		}
		return healthBarColor;
	}

	public static ChatColor getHealthBarColor(Damageable entity) {
		return getHealthBarColor((entity.getHealth() / entity.getMaxHealth()) * 100);
	}

	public static void setName(LivingEntity entity, String name) {
		entity.setCustomName(name);
	}

	public static void setName(LivingEntity entity, String name, boolean isVisible) {
		setName(entity, name);
		entity.setCustomNameVisible(isVisible);
	}

	public static String getDefaultName(LivingEntity entity) {
		return getDefaultName(entity.getType());
	}

	public static String getDefaultName(EntityType type) {
		return WordUtils.capitalizeFully(type.name().toLowerCase().replace("_", " "));
	}

	public static double getCurrentHealth(LivingEntity entity) {
		return entity.getHealth();
	}

	public static void addPotionEffect(LivingEntity entity, PotionEffect... effects) {
		for (PotionEffect effect : effects) {
			entity.addPotionEffect(effect);
		}
	}

	public static void addPotionEffects(LivingEntity entity, Collection<PotionEffect> effects) {
		entity.addPotionEffects(effects);
	}

	public static void setHealth(LivingEntity entity, double health) {
		double maxHealth = getMaxHealth(entity);
		entity.setHealth(health <= maxHealth ? health : maxHealth);
	}

	public static double getMaxHealth(Damageable entity) {
		return entity.getMaxHealth();
	}

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
	 * @param livingEntity    entity to equip
	 * @param entityArmorSlot slot to change on the entity
	 * @param itemStack       item to equip the entity with at the armor slot chosen
	 */
	public static void setEntityEquipment(LivingEntity livingEntity, EntityArmorSlot entityArmorSlot, ItemStack itemStack) {
		switch (entityArmorSlot) {
			case BOOTS:
				livingEntity.getEquipment().setBoots(itemStack);
				break;
			case CHEST:
				livingEntity.getEquipment().setChestplate(itemStack);
				break;
			case HELMET:
				livingEntity.getEquipment().setHelmet(itemStack);
				break;
			case LEGS:
				livingEntity.getEquipment().setLeggings(itemStack);
				break;
			case WEAPON:
				livingEntity.getEquipment().setItemInHand(itemStack);
				break;
			default:
				break;
		}
	}

	public static EntityType getTypeByName(String entityName) {
		String entityInput = entityName.toLowerCase().replace("_", "");
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

	public static void removeEntitySafely(final LivingEntity entity) {
		Commons.threadManager.runTaskOneTickLater(entity::remove);
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

	public static Set<LivingEntity> getLivingEntitiesNearLocation(Location center, int radius) {
		Set<LivingEntity> entities = new HashSet<>();
		for (LivingEntity entity : center.getWorld().getLivingEntities()) {
			if (Locations.isEntityInRadius(center, radius, entity)) {
				entities.add(entity);
			}
		}
		return entities;
//		return center.getWorld().getLivingEntities().stream().filter(entity -> Locations.isEntityInRadius(center, radius, entity)).collect(Collectors.toSet());
	}

	public static Set<Entity> getEntitiesNearLocation(Location center, int radius) {
		Set<Entity> entities = new HashSet<>();
		for (Entity entity : center.getWorld().getEntities()) {
			if (Locations.isEntityInRadius(center, radius, entity)) {
				entities.add(entity);
			}
		}
		return entities;
	}

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

	public static void damage(Damageable target, double damage) {
		target.damage(damage);
	}

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

	public static void registerCustomEntity(Class<? extends net.minecraft.server.v1_7_R4.Entity> entityClass, String name, int id) {
		try {
			List<Map> dataMaps = new ArrayList<>();
			for (Field f : EntityTypes.class.getDeclaredFields()) {
				if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
					f.setAccessible(true);

					dataMaps.add((Map) f.get(null));

				}
			}

			if (dataMaps.get(2).containsKey(id)) {
				dataMaps.get(0).remove(name);
				dataMaps.get(2).remove(id);
			}

			Method method = EntityTypes.class.getDeclaredMethod("a", new Class[]{Class.class, String.class, Integer.TYPE});
			method.setAccessible(true);
			method.invoke(null, entityClass, name, id);
		} catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public static void cleanNameTagEntities() {
		int count = 0;
		for (World world : Worlds.allWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity instanceof NametagEntity) {
					entity.remove();
					count++;
				}
			}
		}
		Commons.debug("Cleaned up " + count + " nametagentities");
	}

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