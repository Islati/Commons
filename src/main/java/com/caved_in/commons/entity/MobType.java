package com.caved_in.commons.entity;

import org.bukkit.entity.EntityType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MobType {
    ZOMBIE(EntityType.ZOMBIE, true, "zombie", "zombies", "undead"),
    SKELETON(EntityType.SKELETON, true, "skeleton", "skeletons"),
    SPIDER(EntityType.SPIDER, true, "spider", "spiders"),
    CAVE_SPIDER(EntityType.CAVE_SPIDER, true, "cavespider", "cave_spider", "cavespiders", "cspider"),
    GIANT(EntityType.GIANT, true, "giant", "bigzombie", "giants"),
    CREEPER(EntityType.CREEPER, true, "creeper", "creepers"),
    PIG_ZOMBIE(EntityType.PIG_ZOMBIE, true, "pigzombie", "zombiepig", "zombiepigman", "undeadpig"),
    BLAZE(EntityType.BLAZE, true, "blaze"),
    BAT(EntityType.BAT, false, "bat"),
    WITCH(EntityType.WITCH, true, "witch"),
    PIG(EntityType.PIG, false, "pig"),
    COW(EntityType.COW, false, "cow"),
    MUSHROOM_COW(EntityType.MUSHROOM_COW, false, "mushroomcow", "mushroom_cow"),
    SHEEP(EntityType.SHEEP, false, "sheep"),
    WOLF(EntityType.WOLF, true, "wolf", "wolve", "wolves", "dog"),
    ENDERMAN(EntityType.ENDERMAN, true, "enderman", "ender_man"),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, true, "lavaslime", "magmacube", "magma_cube", "lava_slime"),
    SLIME(EntityType.SLIME, true, "slime"),
    GHAST(EntityType.GHAST, true, "ghast", "ghasts", "ghost"),
    OCELOT(EntityType.OCELOT, false, "ocelot", "cat", "ozelot"),
    SQUID(EntityType.SQUID, false, "squid", "octopus", "squids"),
    SNOWMAN(EntityType.SNOWMAN, false, "snowman", "frosty"),
    VILLAGER(EntityType.VILLAGER, false, "villager"),
    IRON_GOLEM(EntityType.IRON_GOLEM, true, "golem", "irongolem", "golumn"),
    CHICKEN(EntityType.CHICKEN, false, "chicken", "shicken"),
    HORSE(EntityType.HORSE, false, "horse", "horsey", "foal"),
    ENDER_DRAGON(EntityType.ENDER_DRAGON, true, "dragon", "enderdragon", "ender_dragon"),
    WITHER(EntityType.WITHER, true, "wither", "witherboss"),
    SILVERFISH(EntityType.SILVERFISH, true, "silverfish", "silver_fish"),
    RABBIT(EntityType.RABBIT, false, "rabbit", "bunny"),
    GUARDIAN(EntityType.GUARDIAN, true, "guardian"),
    /**
     * New in 1.8
     **/
    ENDERMITE(EntityType.ENDERMITE, true, "endermite", "mite"),

    /**
     * New in 1.9
     **/
    SHULKER(EntityType.SHULKER, true, "shulker", "shulk"),

    /**
     * New in 1.10
     */

    POLAR_BEAR(EntityType.POLAR_BEAR,true,"bear","polarbear","polar-bear","polar_bear"),
    STRAY(EntityType.STRAY,true,"stray","strayskeleton"),
    HUSK(EntityType.HUSK,true,"husk","husk"),

    /**
     * New in 1.11
     */
    VINDICATOR(EntityType.VINDICATOR,true,"vindicator","vindicate"),
    EVOKER(EntityType.EVOKER,true,"evoker","evoke","evo"),
    VEX(EntityType.VEX,true,"vex"),
    LLAMA(EntityType.LLAMA,false,"llama","longhorse"),


    /**
     * New in 1.12
     */
    ILLUSIONER(EntityType.ILLUSIONER,true,"illusioner","illager","ill"),
    PARROTS(EntityType.PARROT,false,"parrot","birb");

    private static Map<String, EntityType> entityTypes = new HashMap<>();
    private static Map<EntityType, Boolean> entityHostilityMap = new HashMap<>();

    static {
        for (MobType mobType : EnumSet.allOf(MobType.class)) {
            //Store all the hostilities for each mob type.
            entityHostilityMap.put(mobType.getEntityType(), mobType.isHostile());

            //Log all the names that each type can be identified by
            for (String name : mobType.names) {
                entityTypes.put(name, mobType.entityType);
            }
            entityTypes.put(mobType.getEntityType().name().toLowerCase(), mobType.getEntityType());

        }
    }

    private EntityType entityType;
    private String[] names;
    private boolean hostile;

    MobType(EntityType entityType, boolean hostile, String... names) {
        this.entityType = entityType;
        this.hostile = hostile;
        this.names = names;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String[] getNames() {
        return names;
    }

    public boolean isHostile() {
        return hostile;
    }

    public static EntityType getTypeByName(String name) {
        return entityTypes.get(name.toLowerCase());
    }

    public static boolean isEntityType(String name) {
        return entityTypes.containsKey(name.toLowerCase());
    }

    public static boolean isMob(EntityType type) {
        for (MobType mobType : values()) {
            if (mobType.getEntityType() == type) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHostile(EntityType type) {
        if (!isMob(type)) {
            return false;
        }

        return entityHostilityMap.get(type);
    }
}
