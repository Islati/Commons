package com.caved_in.commons.handlers.Entity;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;


public class EntityUtility
{
	
	/**
	 * Change the entities name
	 * @param Entity
	 * @param Name
	 */
	public static LivingEntity spawnLivingEntity(Location Location, EntityType Type)
	{
		LivingEntity spawnedEntity = (LivingEntity)Location.getWorld().spawnEntity(Location, Type);
		return spawnedEntity;
	}
	
	/**
	 * Gets a color for the entities health bar based on the percentage of their health
	 * @param entity
	 * @return
	 */
	public static ChatColor getHealthBarColor(double enemyHealthPercentage)
	{
		ChatColor healthBarColor = ChatColor.GREEN;
		if (enemyHealthPercentage >= 35 && enemyHealthPercentage <= 65)
		{
			healthBarColor = ChatColor.YELLOW;
		}
		else if (enemyHealthPercentage < 35)
		{
			healthBarColor = ChatColor.RED;
		}
		return healthBarColor;
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public static ChatColor getHealthBarColor(Damageable entity)
	{
		return getHealthBarColor((entity.getHealth() / entity.getMaxHealth()) * 100);
	}
	
	public static void setName(LivingEntity Entity, String Name)
	{
		Entity.setCustomName(Name);
	}
	
	public static void setName(LivingEntity Entity, String Name, boolean Visible)
	{
		setName(Entity,Name);
		Entity.setCustomNameVisible(Visible);
	}
	
	public static String getDefaultName(LivingEntity Entity)
	{
		return getDefaultName(Entity.getType());
	}
	
	public static String getDefaultName(EntityType EntityType)
	{
		return WordUtils.capitalizeFully(EntityType.name().toLowerCase().replace("_", " "));
	}
	
	public static int getCurrentHealth(LivingEntity Entity)
	{
		return (int)((Damageable)Entity).getHealth();
	}
	
	public static void setCurrentHealth(LivingEntity Entity,int Health)
	{
		if (Health <= ((Damageable)Entity).getMaxHealth())
		{
			((Damageable)Entity).setHealth((double)Health);
		}
		else
		{
			((Damageable)Entity).setHealth(((Damageable)Entity).getMaxHealth());
		}
	}
	
	public static int getMaxHealth(LivingEntity Entity)
	{
		return (int)((Damageable)Entity).getMaxHealth();
	}
	
	public static void setMaxHealth(LivingEntity Entity,int Health)
	{
		((Damageable)Entity).setMaxHealth((double)Health);
	}
	
	public static void setEntityEquipment(LivingEntity Entity, EntityArmorSlot Slot, ItemStack Item)
	{
		switch (Slot)
		{
			case BOOTS:
				Entity.getEquipment().setBoots(Item);
				break;
			case CHEST:
				Entity.getEquipment().setChestplate(Item);
				break;
			case HELMET:
				Entity.getEquipment().setHelmet(Item);
				break;
			case LEGS:
				Entity.getEquipment().setLeggings(Item);
				break;
			case WEAPON:
				Entity.getEquipment().setItemInHand(Item);
				break;
			default:
				break;
		}
	}
	
	public static EntityType getTypeByName(String Name)
	{
		String SwitchName = Name.toLowerCase().replace("_", "");
		switch (SwitchName)
		{
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
	
	public static void cleanAllEntities()
	{
		for(World bukkitWorld : Bukkit.getWorlds())
		{
			for(LivingEntity livingEntity : bukkitWorld.getLivingEntities())
			{
				if (!livingEntity.hasMetadata("NPC"))
				{
					livingEntity.remove();
				}
			}
		}
	}
}