package com.caved_in.commons.entity;

import com.caved_in.commons.inventory.ArmorBuilder;
import com.caved_in.commons.inventory.ArmorInventory;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Create and spawn entities with ease in both syntax and code-flow.
 *
 * @author TechnicalBro
 */
public class CreatureBuilder {
    /* Type of the mob we're spawning */
    private EntityType type;

    /* The amount of health the creature will have */
    private double health = 0;

    /* What the creatures maximum health is */
    private double maxHealth = 0;

    /* Whether or not it's a baby */
    private boolean baby = false;

    /* Whether or not it's a villager */
    private boolean villager = false;

    /* Used to determine whether or not the creature is powered; Only matters if they're a creeper */
    private boolean powered = false;

    /* Used to determine whether or not the creature is a wither skeleton; only matters if they're a skeleton */
    private Skeleton.SkeletonType skeletonType = Skeleton.SkeletonType.NORMAL;

    /* The name of the entity */
    private String name = "";

    /* The age the creature will be */
    private int age = 0;

    /* The minimum age the creature will be */
    private int ageMin = 0;

    /* The maximum age the creature will be */
    private int ageMax = 0;

    /* Size the slime will be */
    private int size = 0;

    /* Minimum size the slime can be */
    private int sizeMin = 0;

    /* Maximum size the slime can be */
    private int sizeMax = 0;

    /* The armor builder, used to continue the builder experience and parent our creature */
    private ArmorBuilder armorBuilder = new ArmorBuilder();

    public static CreatureBuilder clone(Entity entity) {

        CreatureBuilder builder = new CreatureBuilder(entity.getType());

        if (entity instanceof Ageable) {
            Ageable agedEntity = (Ageable) entity;

            builder.age(agedEntity.getAge());
        }

        if (entity instanceof Villager) {
            Villager villager = (Villager) entity;
            builder.asVillager(true);
        }

        if (entity instanceof Zombie) {
            Zombie zombie = (Zombie) entity;

            builder.asVillager(zombie.isVillager());
        }

        if (entity instanceof Creeper) {
            Creeper creeper = (Creeper) entity;

            builder.powered(creeper.isPowered());
        }

        if (Entities.hasName(entity)) {
            builder.name(entity.getCustomName());
        }

        if (entity instanceof LivingEntity) {
            LivingEntity creature = (LivingEntity) entity;
            builder.maxHealth(Entities.getMaxHealth(creature))
                    .health(Entities.getCurrentHealth(creature))
                    .armor(new ArmorInventory(creature.getEquipment().getArmorContents()));


        }


        if (entity instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) entity;

            builder.skeletonType = skeleton.getSkeletonType();
        }

        if (entity instanceof Slime) {
            Slime slime = (Slime) entity;
            builder.size(slime.getSize());
        }

        return builder;
    }

    public CreatureBuilder(EntityType type) {
        this.type = type;
    }

    public CreatureBuilder(MobType type) {
        this.type = type.getEntityType();

    }

    /**
     * Set the creatures name.
     *
     * @param name name to give the creature
     * @return the creature builder.
     */
    public CreatureBuilder name(String name) {
        this.name = StringUtil.colorize(name);
        return this;
    }

    /**
     * Set the creatures current health.
     *
     * @param health amount of health the creature will have. Can not be greater than their maximum health.
     * @return the creature builder.
     */
    public CreatureBuilder health(double health) {
        this.health = health;
        return this;
    }

    /**
     * Set the creatures maximum health
     *
     * @param maxHealth maximum health the creature will have.
     * @return the creature builder.
     */
    public CreatureBuilder maxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    /**
     * Assign an age to the creature within a specific range.
     *
     * @param min minimum age the creature should have.
     * @param max maximum age the creature could have.
     * @return the creature builder.
     */
    public CreatureBuilder age(int min, int max) {
        this.ageMin = min;
        this.ageMax = max;
        return this;
    }

    /**
     * Assign an age to the creature.
     *
     * @param age age to spawn the creature with.
     * @return the creature builder.
     */
    public CreatureBuilder age(int age) {
        this.age = age;
        return this;
    }

    /**
     * Assign a size to the slime within a range.
     *
     * @param min minimum size the slime will be.
     * @param max maximum size the slime could be.
     * @return the creature builder.
     */
    public CreatureBuilder size(int min, int max) {
        this.sizeMin = min;
        this.sizeMax = max;
        return this;
    }

    /**
     * Assign a size to the slime.
     *
     * @param size size the slime will be.
     * @return the creature builder.
     */
    public CreatureBuilder size(int size) {
        this.size = size;
        return this;
    }

    /**
     * Set the creeper to be powered.
     * Only used if the creature is a creeper.
     *
     * @return the creature builder.
     */
    public CreatureBuilder powered() {
        this.powered = true;
        return this;
    }

    public CreatureBuilder powered(boolean value) {
        this.powered = value;
        return this;
    }

    /**
     * Set the skeleton type to be wither.
     * Only used if the creature is a skeleton.
     *
     * @return the creature builder
     */
    public CreatureBuilder wither() {
        this.skeletonType = Skeleton.SkeletonType.WITHER;
        return this;
    }

    /**
     * Whether or not the creature is a baby.
     * Checked if the creature is able to be a baby, or is age-able (ie. Chickens, cows, pig)
     *
     * @param baby whether or not the creature is a baby.
     * @return the creature builder
     */
    public CreatureBuilder asBaby(boolean baby) {
        this.baby = baby;
        return this;
    }

    /**
     * Whether or not the creature is a villager zombie.
     * Only checked if the entity type is a Zombie.
     *
     * @param villager whether or not it's a villager zombie
     * @return the creature builder
     */
    public CreatureBuilder asVillager(boolean villager) {
        this.villager = villager;
        return this;
    }

    /**
     * Assign equipment for the creature to wear.
     *
     * @return the armorbuilder for this creature builder
     */
    public ArmorBuilder armor() {
        return armorBuilder.parent(this);
    }

    public CreatureBuilder armor(ArmorInventory armor) {
        armor().withHelmet(armor.getHelmet()).withBoots(armor.getBoots()).withChest(armor.getChest()).withLeggings(armor.getLegs()).withMainHand(armor.getMainHand()).withOffHand(armor.getOffHand());
        return this;
    }

    /**
     * Spawn the creature at a specific location with the properties acquired via the builder
     *
     * @param location location to spawn the creature at.
     * @return the creature builder.
     */
    public LivingEntity spawn(Location location) {
        LivingEntity entity = Entities.spawnLivingEntity(type, location);

        if (entity instanceof Ageable) {
            Ageable ageable = (Ageable) entity;
            if (baby) {
                ageable.setBaby();
            } else {
                //If the minimum age is greater than 0, and the maximum age is more than the min
                //then we're going to want to setup an age range between the min and max;
                if (ageMin > 0 && ageMax > ageMin) {
                    ageable.setAge(NumberUtil.getRandomInRange(ageMin, ageMax));
                } else if (age > 0) {
                    ageable.setAge(age);
                } else {
                    ageable.setAdult();
                }
            }
        }

        if (entity instanceof Zombie) {
            Zombie zombie = (Zombie) entity;
            zombie.setBaby(baby);
            zombie.setVillager(villager);
        }

        if (entity instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) entity;
            skeleton.setSkeletonType(skeletonType);
        }

        if (entity instanceof Slime) {
            Slime slime = (Slime) entity;

            //If the slime has a min and max potential size, then assign them a random in that range
            //Otherwise, assign them the default size.
            if (sizeMin > 0 && sizeMax > sizeMin) {
                slime.setSize(NumberUtil.getRandomInRange(sizeMin, sizeMax));
            } else if (size > 0) {
                slime.setSize(size);
            }
        }

        if (entity instanceof Creeper) {
            Creeper creeper = (Creeper) entity;
            creeper.setPowered(powered);
        }

        if (health > 0 && maxHealth >= health) {
            Entities.setMaxHealth(entity, maxHealth);
            Entities.setHealth(entity, health);
        }

        if (!StringUtils.isEmpty(name)) {
            Entities.setName(entity, name, true);
        }

        //Equip the entity with the equipment set
        Entities.setEquipment(entity, armorBuilder.toInventory());
        return entity;
    }

    /**
     * Create a {@link MobData} object with the values used in the builder. Can be used to easily create serializable mob-spawn data.
     * @return a {@link MobData} object with the values used in the builder.
     */
    public MobData toSpawnData() {
        MobData data = new MobData();

        data.setEntityType(type);
        data.setAge(age);
        data.setAgeMax(ageMax);
        data.setAgeMin(ageMin);
        data.setArmorInventory(armorBuilder.toInventory());
        data.setHealth(health);
        data.setMaxHealth(maxHealth);
        data.setBaby(baby);
        data.setVillager(villager);
        data.setName(name);
        data.setSkeletonType(skeletonType);
        data.setPowered(powered);
        data.setSize(size);
        data.setSizeMin(sizeMin);
        data.setSizeMax(sizeMax);

        return data;
    }

    /**
     * Spawn creature(s) at a specific location.
     *
     * @param location location to spawn the creature(s) at.
     * @param amount   amount of creatures to spawn
     * @return a set containing all the creatures spawned.
     */
    public Set<LivingEntity> spawn(Location location, int amount) {
        Set<LivingEntity> entities = new HashSet<>();
        for (int i = 0; i < amount; i++) {
            entities.add(spawn(location));
        }
        return entities;
    }

    protected EntityType getType() {
        return type;
    }

    protected double getHealth() {
        return health;
    }

    protected double getMaxHealth() {
        return maxHealth;
    }

    protected boolean isBaby() {
        return baby;
    }

    protected boolean isVillager() {
        return villager;
    }

    protected boolean isPowered() {
        return powered;
    }

    protected Skeleton.SkeletonType getSkeletonType() {
        return skeletonType;
    }

    protected String getName() {
        return name;
    }

    protected int getAge() {
        return age;
    }

    protected int getAgeMin() {
        return ageMin;
    }

    protected int getAgeMax() {
        return ageMax;
    }

    protected int getSize() {
        return size;
    }

    protected int getSizeMin() {
        return sizeMin;
    }

    protected int getSizeMax() {
        return sizeMax;
    }

    protected ArmorBuilder getArmorBuilder() {
        return armorBuilder;
    }

    /**
     * Create a new creature builder instance.
     *
     * @param type type of entity to build.
     * @return a new creature builder instance with the type defined.
     */
    public static CreatureBuilder of(EntityType type) {
        return new CreatureBuilder(type);
    }
}
