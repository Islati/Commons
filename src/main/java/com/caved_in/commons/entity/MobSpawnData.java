package com.caved_in.commons.entity;

import com.caved_in.commons.inventory.ArmorInventory;
import com.mysql.jdbc.StringUtils;
import lombok.ToString;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/*
TODO The mob spawn data is created through a gui in game, and passed through multiple menus and items to
give a super fluid experience.
 */
@Root(name = "mob-data")
@ToString(exclude = {"entityType", "skeletalType"}, includeFieldNames = true)
public class MobSpawnData {

    @Element(name = "mob-type")
    private String mobType;

    private EntityType entityType;

    /* The amount of health the creature will have */
    @Element(name = "health", required = false)
    private double health = 0;

    /* What the creatures maximum health is */
    @Element(name = "max-health", required = false)
    private double maxHealth = 0;

    /* Whether or not it's a baby */
    @Element(name = "baby", required = false)
    private boolean baby = false;

    /* Whether or not it's a villager */
    @Element(name = "villager", required = false)
    private boolean villager = false;

    /* Used to determine whether or not the creature is powered; Only matters if they're a creeper */
    @Element(name = "powered", required = false)
    private boolean powered = false;

    /* Used to determine whether or not the creature is a wither skeleton; only matters if they're a skeleton */
    private Skeleton.SkeletonType skeletonType = Skeleton.SkeletonType.NORMAL;

    @Element(name = "skeleton-type", required = false)
    private String skeletalType;

    /* The name of the entity */
    @Element(name = "name", required = false)
    private String name = "";

    /* The age the creature will be */
    @Element(name = "age", required = false)
    private int age = 0;

    /* The minimum age the creature will be */
    @Element(name = "age-min", required = false)
    private int ageMin = 0;

    /* The maximum age the creature will be */
    @Element(name = "age-max", required = false)
    private int ageMax = 0;

    /* Size the slime will be */
    @Element(name = "slime-size", required = false)
    private int size = 0;

    /* Minimum size the slime can be */
    @Element(name = "slime-min", required = false)
    private int sizeMin = 0;

    /* Maximum size the slime can be */
    @Element(name = "size-max", required = false)
    private int sizeMax = 0;

    @Element(name = "armor", type = ArmorInventory.class, required = false)
    private ArmorInventory armorInventory = new ArmorInventory();

    public MobSpawnData(@Element(name = "mob-type") String mobType, @Element(name = "health", required = false) double health, @Element(name = "max-health", required = false) double maxHealth, @Element(name = "baby", required = false) boolean baby, @Element(name = "villager", required = false) boolean villager, @Element(name = "powered", required = false) boolean powered, @Element(name = "skeleton-type", required = false) String skeletalType, @Element(name = "name", required = false) String name, @Element(name = "age", required = false) int age, @Element(name = "age-min", required = false) int ageMin, @Element(name = "age-max", required = false) int ageMax, @Element(name = "slime-size", required = false) int size, @Element(name = "slime-min", required = false) int sizeMin, @Element(name = "size-max", required = false) int sizeMax, @Element(name = "armor", type = ArmorInventory.class, required = false) ArmorInventory armorInventory) {
        this.mobType = mobType;
        this.entityType = Entities.getTypeByName(mobType);
        this.health = health;
        this.maxHealth = maxHealth;
        this.baby = baby;
        this.villager = villager;
        this.powered = powered;
        this.skeletalType = skeletalType;

        if (skeletalType != null) {
            this.skeletonType = Skeleton.SkeletonType.valueOf(skeletalType);
        } else {
            this.skeletonType = Skeleton.SkeletonType.NORMAL;
        }

        this.name = name;
        this.age = age;
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.size = size;
        this.sizeMin = sizeMin;
        this.sizeMax = sizeMax;
        this.armorInventory = armorInventory;
    }

    public MobSpawnData() {

    }

    public CreatureBuilder toBuilder() {
        CreatureBuilder builder = CreatureBuilder.of(entityType);

        if (name != null && !StringUtils.isNullOrEmpty(name)) {
            builder.name(name);
        }

        if (health > 0) {
            builder.health(health);
        }

        if (maxHealth > 0) {
            builder.maxHealth(maxHealth);
        }

        if (age > 0) {
            builder.age(age);
        }

        if (ageMin > 0 && ageMax > ageMin) {
            builder.age(ageMin, ageMax);
        }

        if (size > 0) {
            builder.size(size);
        }

        if (sizeMin > 0 && sizeMax > sizeMin) {
            builder.size(sizeMin, sizeMax);
        }

        if (powered) {
            builder.powered();
        }

        if (skeletonType == Skeleton.SkeletonType.WITHER) {
            builder.wither();
        }

        builder.asBaby(baby);

        builder.asVillager(villager);

        builder.armor(armorInventory);

        return builder;
    }


    public EntityType getEntityType() {
        return entityType;
    }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public boolean isBaby() {
        return baby;
    }

    public boolean isVillager() {
        return villager;
    }

    public boolean isPowered() {
        return powered;
    }

    public Skeleton.SkeletonType getSkeletonType() {
        return skeletonType;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public int getSize() {
        return size;
    }

    public int getSizeMin() {
        return sizeMin;
    }

    public int getSizeMax() {
        return sizeMax;
    }

    public ArmorInventory getArmorInventory() {
        return armorInventory;
    }

    public void setEntityType(EntityType entityType) {
        this.entityType = entityType;
        mobType = entityType.name();
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setBaby(boolean baby) {
        this.baby = baby;
    }

    public void setVillager(boolean villager) {
        this.villager = villager;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    public void setSkeletonType(Skeleton.SkeletonType skeletonType) {
        this.skeletonType = skeletonType;
        skeletalType = skeletonType.name();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSizeMin(int sizeMin) {
        this.sizeMin = sizeMin;
    }

    public void setSizeMax(int sizeMax) {
        this.sizeMax = sizeMax;
    }

    public void setArmorInventory(ArmorInventory armorInventory) {
        this.armorInventory = armorInventory;
    }
}
