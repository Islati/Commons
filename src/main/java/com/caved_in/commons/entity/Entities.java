package com.caved_in.commons.entity;

import com.caved_in.commons.Commons;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.google.common.collect.Sets;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


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

	public static void setName(LivingEntity Entity, String Name, boolean Visible) {
		setName(Entity, Name);
		Entity.setCustomNameVisible(Visible);
	}

	public static String getDefaultName(LivingEntity Entity) {
		return getDefaultName(Entity.getType());
	}

	public static String getDefaultName(EntityType EntityType) {
		return WordUtils.capitalizeFully(EntityType.name().toLowerCase().replace("_", " "));
	}

	public static int getCurrentHealth(LivingEntity Entity) {
		return (int) ((Damageable) Entity).getHealth();
	}

	public static void addPotionEffect(LivingEntity livingEntity, PotionEffect potionEffect) {
		livingEntity.addPotionEffect(potionEffect);
	}

	public static void setCurrentHealth(LivingEntity livingEntity, int health) {
		if (health <= ((Damageable) livingEntity).getMaxHealth()) {
			((Damageable) livingEntity).setHealth((double) health);
		} else {
			((Damageable) livingEntity).setHealth(((Damageable) livingEntity).getMaxHealth());
		}
	}

	public static int getMaxHealth(LivingEntity livingEntity) {
		return (int) ((Damageable) livingEntity).getMaxHealth();
	}

	public static void setMaxHealth(LivingEntity livingEntity, int health) {
		((Damageable) livingEntity).setMaxHealth((double) health);
	}

	/**
	 * Change the active armor of an entity in the specified slot
	 * to be of the given ItemStack
	 * <p/>
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
		Commons.threadManager.runTaskOneTickLater(new Runnable() {
			@Override
			public void run() {
				entity.remove();
			}
		});
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
}