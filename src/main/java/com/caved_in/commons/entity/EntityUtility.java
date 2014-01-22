package com.caved_in.commons.entity;

import com.caved_in.commons.Commons;
import com.caved_in.commons.potions.PotionHandler;
import com.caved_in.commons.potions.PotionType;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.*;


public class EntityUtility {

	public static Set<LivingEntity> spawnLivingEntity(EntityType entityType, Location location, int amount) {
		Set<LivingEntity> entities = new HashSet<>();
		//Loop and spawn enties until the amount requested has been spawned
		for(int i = 0; i < amount; i++) {
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

	public static void addPotionEffect(LivingEntity livingEntity, PotionType potionType, int durationInTicks) {
		addPotionEffect(livingEntity, PotionHandler.getPotionEffect(potionType, durationInTicks));
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
	 *
	 * This method doesn't check if the slot, or item, are valid items for the slot:
	 * It forces the items to be in the slot.
	 * @param livingEntity
	 * @param entityArmorSlot
	 * @param itemStack
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
	 * @param world
	 */
	public static void cleanAllEntities(World world) {
		for (LivingEntity livingEntity : world.getLivingEntities()) {
			if (!livingEntity.hasMetadata("NPC") && !(livingEntity instanceof Player)) {
				livingEntity.remove();
			}
		}
	}

	/**
	 * Clean all the entities in a world except the defined types (And players, and citizens NPC'S)
	 * @param world world to clean of livingEntities
	 * @param entityTypes entityTypes to not remove
	 */
	public static void cleanAllEntitiesExcept(World world, EntityType... entityTypes) {
		List<EntityType> eTypes = Arrays.asList(entityTypes);
		for (LivingEntity livingEntity : world.getLivingEntities()) {
			if (!eTypes.contains(livingEntity.getType())) {
				if (!livingEntity.hasMetadata("NPC") && !(livingEntity instanceof Player)) {
					livingEntity.remove();
				}
			}
		}
	}

	/**
	 * Simulate player knock-back on an entity
	 * @param entity
	 */
	public static void knockbackEntity(LivingEntity entity) {
		knockbackEntity(entity, -1);
	}

	/**
	 * Knock back en entity with a specified amount of force
	 * @param entity
	 * @param force
	 */
	public static void knockbackEntity(LivingEntity entity, int force) {
		entity.setVelocity(entity.getLocation().getDirection().multiply(force));
	}

	public static void removeEntitySafely(final LivingEntity entity) {
		Commons.threadManager.runTaskOneTickLater(new Runnable()
		{
			@Override
			public void run()
			{
				entity.remove();
			}
		});
	}

	/**
	 *
	 * @param world
	 * @param entityUUID
	 * @return
	 */
	public static LivingEntity getEntityByUUID(World world, UUID entityUUID) {
		for(LivingEntity entity : world.getLivingEntities()) {
			if (entity.getUniqueId().equals(entityUUID)) {
				return entity;
			}
		}
		return null;
	}
}