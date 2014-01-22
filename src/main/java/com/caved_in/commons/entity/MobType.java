package com.caved_in.commons.entity;

import org.bukkit.entity.EntityType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 22/01/14
 * Time: 1:39 PM
 */
public enum MobType {
	ZOMBIE(EntityType.ZOMBIE, "zombie","zombies","undead"),
	SKELETON(EntityType.SKELETON, "skeleton", "skeletons"),
	SPIDER(EntityType.SPIDER, "spider", "spiders"),
	CAVE_SPIDER(EntityType.CAVE_SPIDER, "cavespider", "cave_spider", "cavespiders", "cspider"),
	GIANT(EntityType.GIANT, "giant", "bigzombie", "giants"),
	CREEPER(EntityType.CREEPER, "creeper", "creepers"),
	PIG_ZOMBIE(EntityType.PIG_ZOMBIE, "pigzombie","zombiepig","zombiepigman","undeadpig"),
	BLAZE(EntityType.BLAZE, "blaze"),
	BAT(EntityType.BAT, "bat"),
	WITCH(EntityType.WITCH,"witch"),
	PIG(EntityType.PIG,"pig"),
	COW(EntityType.COW, "cow"),
	MUSHROOM_COW(EntityType.MUSHROOM_COW,"mushroomcow","mushroom_cow"),
	SHEEP(EntityType.SHEEP,"sheep"),
	WOLF(EntityType.WOLF,"wolf","wolve","wolves","dog"),
	ENDERMAN(EntityType.ENDERMAN,"enderman","ender_man"),
	MAGMA_CUBE(EntityType.MAGMA_CUBE,"lavaslime","magmacube","magma_cube","lava_slime"),
	SLIME(EntityType.SLIME, "slime"),
	GHAST(EntityType.GHAST, "ghast", "ghasts","ghost"),
	OCELOT(EntityType.OCELOT,"ocelot","cat","ozelot"),
	SQUID(EntityType.SQUID,"squid","octopus","squids"),
	SNOWMAN(EntityType.SNOWMAN,"snowman","frosty"),
	VILLAGER(EntityType.VILLAGER,"villager"),
	IRON_GOLEM(EntityType.IRON_GOLEM,"golem","irongolem","golumn"),
	CHICKEN(EntityType.CHICKEN,"chicken","shicken"),
	HORSE(EntityType.HORSE,"horse","horsey","foal"),
//	ENDER_DRAGON(EntityType.ENDER_DRAGON,"dragon","enderdragon","ender_dragon"),
	WITHER(EntityType.WITHER, "wither","witherboss"),
	SILVERFISH(EntityType.SILVERFISH,"silverfish","silver_fish");

	private static Map<String, EntityType> entityTypes = new HashMap<>();

	static {
		for(MobType mobType : EnumSet.allOf(MobType.class)) {
			for(String name : mobType.names) {
				entityTypes.put(name, mobType.entityType);
			}
		}
	}

	private EntityType entityType;
	private String[] names;

	MobType(EntityType entityType, String... names) {
		this.entityType = entityType;
		this.names = names;
	}

	public EntityType getEntityType() {
		return entityType;
	}

	public String[] getNames() {
		return names;
	}

	public static EntityType getTypeByName(String name) {
		return entityTypes.get(name.toLowerCase());
	}

	public static boolean isEntityType(String name) {
		return entityTypes.containsKey(name.toLowerCase());
	}
}
