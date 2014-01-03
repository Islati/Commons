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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class EntityUtility {

	/**
	 * Change the entities name
	 *
	 * @param Entity
	 * @param Name
	 */
	public static LivingEntity spawnLivingEntity(Location Location, EntityType Type) {
		LivingEntity spawnedEntity = (LivingEntity) Location.getWorld().spawnEntity(Location, Type);
		return spawnedEntity;
	}

	/**
	 * Gets a color for the entities health bar based on the percentage of their health
	 *
	 * @param entity
	 * @return
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
	 * @param entity
	 * @return
	 */
	public static ChatColor getHealthBarColor(Damageable entity) {
		return getHealthBarColor((entity.getHealth() / entity.getMaxHealth()) * 100);
	}

	/**
	 * Change the name of a LivingEntity
	 * @param entity The entity to modify
	 * @param name Name we wish to give our entity
	 */
	public static void setName(LivingEntity entity, String name) {
		entity.setCustomName(name);
	}

	/**
	 * Change the name of a living entity, and set whether or not
	 * its name is always visible
	 * @param Entity
	 * @param Name
	 * @param Visible
	 */
	public static void setName(LivingEntity Entity, String Name, boolean Visible) {
		setName(Entity, Name);
		Entity.setCustomNameVisible(Visible);
	}

	/**
	 * Gets the default name for an entity based on it's type
	 * @param Entity
	 * @return
	 */
	public static String getDefaultName(LivingEntity Entity) {
		return getDefaultName(Entity.getType());
	}

	/**
	 * Gets the default name of an entity of the given type
	 * @param EntityType
	 * @return
	 */
	public static String getDefaultName(EntityType EntityType) {
		return WordUtils.capitalizeFully(EntityType.name().toLowerCase().replace("_", " "));
	}

	/**
	 * Get the current health for an entity, as an integer
	 * @param Entity
	 * @return
	 */
	public static int getCurrentHealth(LivingEntity Entity) {
		return (int) ((Damageable) Entity).getHealth();
	}

	/**
	 * Give an entity a potion effect
	 * @param livingEntity entity to give the potion effect to
	 * @param potionEffect the potion effect in which to give the player
	 */
	public static void addPotionEffect(LivingEntity livingEntity, PotionEffect potionEffect) {
		livingEntity.addPotionEffect(potionEffect);
	}

	/**
	 * Give an entity a potion effect of the given type for a specific duration
	 * @param livingEntity entity to give the potion effect to
	 * @param potionType effect type to give the player
	 * @param durationInTicks duration of the potion effect (in ticks. 20 ticks = 1 second)
	 */
	public static void addPotionEffect(LivingEntity livingEntity, PotionType potionType, int durationInTicks) {
		addPotionEffect(livingEntity, PotionHandler.getPotionEffect(potionType, durationInTicks));
	}

	/**
	 *
	 * @param livingEntity
	 * @param health
	 */
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

	/**
	 * Get an entities type by it's name
	 * @param Name
	 * @return
	 */
	public static EntityType getTypeByName(String Name) {
		String SwitchName = Name.toLowerCase().replace("_", "");
		switch (SwitchName) {
			case "zombie":
			case "zombies":
			case "undead":
				return EntityType.ZOMBIE;
			case "skeleton":
			case "skeletons":
				return EntityType.SKELETON;
			case "spider":
			case "spiders":
				return EntityType.SPIDER;
			case "cavespider":
			case "cavespiders":
				return EntityType.CAVE_SPIDER;
			case "giant":
			case "giants":
				return EntityType.GIANT;
			case "creeper":
			case "creepers":
				return EntityType.CREEPER;
			case "pigzombie":
			case "zombiepig":
			case "zombiepigman":
			case "undeadpig":
				return EntityType.PIG_ZOMBIE;
			case "blaze":
				return EntityType.BLAZE;
			case "bat":
				return EntityType.BAT;
			case "witch":
				return EntityType.WITCH;
			case "pig":
				return EntityType.PIG;
			case "cow":
			case "cows":
				return EntityType.COW;
			case "mushroomcow":
				return EntityType.MUSHROOM_COW;
			case "sheep":
				return EntityType.SHEEP;
			case "wolf":
			case "wolves":
				return EntityType.WOLF;
			case "enderman":
				return EntityType.ENDERMAN;
			case "lavaslime":
			case "magmacube":
				return EntityType.MAGMA_CUBE;
			case "slime":
				return EntityType.SLIME;
			case "ghast":
			case "ghasts":
				return EntityType.GHAST;
			case "ocelot":
			case "ozelot":
			case "cat":
				return EntityType.OCELOT;
			case "squid":
			case "squids":
				return EntityType.SQUID;
			case "snowman":
				return EntityType.SNOWMAN;
			case "villager":
				return EntityType.VILLAGER;
			case "golem":
			case "irongolem":
			case "golumn":
				return EntityType.IRON_GOLEM;
			case "chicken":
				return EntityType.CHICKEN;
			case "horse":
				return EntityType.HORSE;
			case "silverfish":
				return EntityType.SILVERFISH;
			default:
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