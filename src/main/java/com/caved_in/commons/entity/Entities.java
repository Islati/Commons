package com.caved_in.commons.entity;

import com.caved_in.commons.Commons;
import com.caved_in.commons.inventory.ArmorInventory;
import com.caved_in.commons.inventory.ArmorSlot;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.location.Locations;
import com.caved_in.commons.potion.Potions;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.utilities.NumberUtil;
import com.caved_in.commons.warp.Warp;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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
     * Spawn fireworks at the given location.
     *
     * @param loc location to spawn fireworks at.
     * @return firework that was spawned at the given location.
     */
    public static Firework spawnFireworks(Location loc) {
        return (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
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
     * Spawn an invisible, invincible {@link org.bukkit.entity.Bat} at the given location
     *
     * @param loc location to spawn the bat at.
     * @return the invisible bat that was spawned
     */
    public static Bat spawnInvisibleBat(Location loc) {
        World world = loc.getWorld();
        Bat bat = world.spawn(loc, Bat.class);
        /*
		 Give the bat invisibility..
		 */
        addPotionEffect(bat, Potions.getPotionEffect(PotionEffectType.INVISIBILITY, 1, Integer.MAX_VALUE));
		/*
        .. and also stop them from taking any damage for a loooong time!
		 */
        bat.setNoDamageTicks(Integer.MAX_VALUE);

		/*
		Make sure the bat stays spawned when nobodies near it!
		 */
        bat.setRemoveWhenFarAway(false);

		/*
		and assure the bat has no movement velocity, otherwise shit will go haywire!
		 */
        bat.setVelocity(new Vector(0, 0, 0));
        return bat;
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

    public static boolean hasKiller(LivingEntity entity) {
        return entity.getKiller() != null;
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

    public static String getName(Entity entity) {
        if (!hasName(entity)) {
            return getDefaultName(entity.getType());
        } else {
            return entity.getCustomName();
        }
    }

    public static boolean hasName(Entity entity) {
        return entity.getCustomName() != null;
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
    public static double getCurrentHealth(Damageable entity) {
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
     * <p/>
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
            case MAIN_HAND:
                inv.setItemInMainHand(item);
                break;
            case OFF_HAND:
                inv.setItemInOffHand(item);
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
        inventory.getArmor().forEach((key, value) -> Entities.setEquipment(entity, key, value));
    }

    /**
     * Assign a collection of living-entities to have the armor specific by ArmorInventory param.
     *
     * @param entities  entities to modify the armor of.
     * @param inventory armor to assign to the entities.
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
        EntityType entityType;
        try {
            entityType = EntityType.valueOf(type);
            if (entityType != null) {
                return entityType;
            }
        } catch (Exception e) {

        }

        String entityInput = type.toLowerCase().replace("_", "");
        entityType = MobType.getTypeByName(entityInput);
        if (entityType != null) {
            return entityType;
        } else {
            return EntityType.UNKNOWN;
        }
    }

    /**
     * Simulate player knock-back on an entity
     *
     * @param entity entity to knock back.
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
     * Launch an entity upwards with a given amount (how high to go) and force (multiplier to height & speed)
     *
     * @param entity entity to launch upwards
     * @param amount amount of block to launch the entity up
     * @param force  force to launch the entity at
     */
    public static void launchUpwards(LivingEntity entity, int amount, double force) {
        Vector entityVector = entity.getVelocity();
        entityVector.add(new Vector(0, amount, 0)).multiply(force);
        entity.setVelocity(entityVector);
    }

    /**
     * Launch a snowball from the given entity.
     *
     * @param entity entity to throw the snowball from.
     */
    public static void launchSnowball(LivingEntity entity) {
        launchSnowball(entity, 1, 2);
    }

    /**
     * Launch a snowball from the given entity with a specified force. Higher than 40 may cause visual glitches.
     *
     * @param entity entity to throw the snowball.
     * @param force  force to throw the snowball with.
     */
    public static void launchSnowball(LivingEntity entity, int force) {
        launchSnowball(entity, 1, force);
    }

    /**
     * Make the desired amount of snowballs with a specified force fire from the entity passed.
     *
     * @param entity entity firing the snowballs
     * @param amount amount of snowballs the fire
     * @param force  force to apply to the snowballs
     */
    public static void launchSnowball(LivingEntity entity, int amount, int force) {
        for (int i = 0; i < amount; i++) {
            Snowball snowball = entity.launchProjectile(Snowball.class);
            snowball.setVelocity(snowball.getVelocity().multiply(force));
        }
    }

    /**
     * Force a variable amount of entities to be removed.
     *
     * @param entities entities to remove.
     */
    public static void remove(Entity... entities) {
        for (Entity e : entities) {
            if (e instanceof Player) {
                continue;
            }
            e.remove();
        }
    }

    /**
     * Force an entity to be removed safely, by spawning a thread to remove them one tick later.
     *
     * @param entities entities to remove safely.
     */
    public static void removeEntitySafely(Entity... entities) {
        Commons.getInstance().getThreadManager().runTaskOneTickLater(() -> {
            for (Entity e : entities) {
                if (e instanceof Player) {
                    continue;
                }
                e.remove();
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

    public static LivingEntity getEntityByUUID(UUID id) {
        for (World world : Bukkit.getWorlds()) {
            for (LivingEntity entity : world.getLivingEntities()) {
                if (entity.getUniqueId().equals(id)) {
                    return entity;
                }
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

        List<Entity> nearby = entity.getNearbyEntities(x, y, z);

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
    public static Set<LivingEntity> getLivingEntitiesNearLocation(Location center, double radius) {
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
        Set<Item> items = new HashSet<>();
        for (Item item : center.getWorld().getEntitiesByClass(Item.class)) {
            if (Locations.isEntityInRadius(center, radius, item)) {
                items.add(item);
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
        if (target instanceof Player) {
            if (Commons.getInstance().getPlayerHandler().getData((Player) target).hasGodMode()) {
                return;
            }
        }
        target.damage(damage);

        //todo implement event proc.
    }

    /**
     * Damage the entity with a specific amount of damage, using the damager param as the source of damage.
     *
     * @param target  entity to damage
     * @param damage  damage to apply to the {@param target}
     * @param damager entity applying the damage.
     */
    public static void damage(Damageable target, double damage, LivingEntity damager) {
        if (target instanceof Player) {
            if (Commons.getInstance().getPlayerHandler().getData((Player) target).hasGodMode()) {
                return;
            }
        }

        target.damage(damage, damager);
    }

    /**
     * Burn the entity for a specific amount of ticks.
     *
     * @param target    target to burn/
     * @param fireTicks amount of ticks the fire will last.
     */
    public static void burn(Entity target, int fireTicks) {
        target.setFireTicks(fireTicks);
    }

    /**
     * Burns the target for a specific duration.
     *
     * @param target   target to burn
     * @param amount   how long the target should burn; used with timeType to specify duration.
     * @param timeType the unit of time used to determine how long the target burns for.
     */
    public static void burn(Entity target, int amount, TimeType timeType) {
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
        double v_x = (1.0 + 0.07 * d) * (loc.getX() - entityLoc.getX()) / d;
        double v_y = (1.0 + 0.03 * d) * (loc.getY() - entityLoc.getY()) / d - 0.5 * g * d;
        double v_z = (1.0 + 0.07 * d) * (loc.getZ() - entityLoc.getZ()) / d;

        org.bukkit.util.Vector v = e.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        e.setVelocity(v);
    }

    /**
     * Whether or not an entity has a passenger attached to them.
     *
     * @param entity entity to check for passengers on.
     * @return true if they've got a passenger, false otherwise.
     */
    public static boolean hasPassenger(Entity entity) {
        return entity.getPassenger() != null;
    }

    /**
     * Change the entities max (and current) health based on the given ratio.
     * <p>
     * Example: An entity with 20 health, assigned a ratio of 0.5, will have 10 health
     * after adjusting.
     *
     * @param ratio the ratio to multiply the mobs health by
     */
    public static void adjustHealth(Damageable subject, double ratio) {
        double maxHealth = getMaxHealth(subject);
        double currentHealth = getCurrentHealth(subject);

        double adjustedMax = maxHealth * ratio;
        double adjustedHealth = currentHealth * ratio;

        subject.setMaxHealth(adjustedMax);
        subject.setHealth(adjustedHealth);
    }

    /**
     * Check whether or not the entity is ontop of a block.
     *
     * @param entity entity to check
     * @return true if the entity is ontop of a block (including fence), false otherwise.
     */
    public static boolean onBlock(Entity entity) {
        Location loc = entity.getLocation();

        double locX = loc.getX();
        double locZ = loc.getZ();
        double locY = loc.getY();

        double xMod = locX % 1.0D;
        if (locX < 0.0D) {
            xMod += 1.0D;
        }

        double zMod = locZ % 1.0D;
        if (locZ < 0.0D) {
            zMod += 1.0D;
        }

        int xMin = 0;
        int xMax = 0;
        int zMin = 0;
        int zMax = 0;

        if (xMod < 0.3D) {
            xMin = -1;
        }
        if (xMod > 0.7D) {
            xMax = 1;
        }
        if (zMod < 0.3D) {
            zMin = -1;
        }
        if (zMod > 0.7D) {
            zMax = 1;
        }

        for (int x = xMin; x <= xMax; x++) {
            for (int z = zMin; z <= zMax; z++) {

                Block locBlock = loc.add(x, -0.5D, z).getBlock();

                Material locBlockType = locBlock.getType();
				/* If the block beneath the player isn't air or liquid, then they're on a block! */
                if (locBlockType != Material.AIR && !locBlock.isLiquid()) {
                    return true;
                }

                Material materialBeneathPlayer = loc.add(x, -1.5D, z).getBlock().getType();

				/*
				If the players not standing ontop of something that'd offset their Y
				by 0.5 (half a block, like fence) then continue.
				 */
                if (locY % 0.5D != 0.0D) {
                    continue;
                }

				/*
				Lastly, if the material beneath the player is equal to fence, nether-fence, or cobble
				stone walls, then they are infact standing atop a block!
				 */
                switch (materialBeneathPlayer) {
                    case FENCE:
                    case NETHER_FENCE:
                    case COBBLE_WALL:
                        return true;
                    default:
                        break;
                }
            }
        }
        return false;
    }

    /**
     * Check whether or not the given entity is hostile or docile by nature.
     *
     * @param entity entity to check the nature of.
     * @return true if the given entity is hostile, false otherwise.
     */
    public static boolean isHostile(LivingEntity entity) {
        return MobType.isHostile(entity.getType());
    }

    /**
     * Check if the entity is a mob (livingentity).
     *
     * @param entity entity to check
     * @return true if the entity is a mob entity, false otherwise.
     */
    public static boolean isMob(Entity entity) {
        return MobType.isMob(entity.getType());
    }

    public static void assignData(Entity entity, MobData data) {
        if (entity instanceof Ageable) {
            Ageable ageable = (Ageable) entity;
            if (data.isBaby()) {
                ageable.setBaby();
            } else {
                //If the minimum age is greater than 0, and the maximum age is more than the min
                //then we're going to want to setup an age range between the min and max;
                if (data.getAgeMin() > 0 && data.getAgeMax() > data.getAgeMin()) {
                    ageable.setAge(NumberUtil.getRandomInRange(data.getAgeMin(), data.getAgeMax()));
                } else if (data.getAge() > 0) {
                    ageable.setAge(data.getAge());
                } else {
                    ageable.setAdult();
                }
            }
        }

        if (entity instanceof Zombie) {
            Zombie zombie = (Zombie) entity;
            zombie.setBaby(data.isBaby());
            zombie.setVillager(zombie.isVillager());
        }

        if (entity instanceof Skeleton) {
            Skeleton skeleton = (Skeleton) entity;
            skeleton.setSkeletonType(data.getSkeletonType());
        }

        if (entity instanceof Slime) {
            Slime slime = (Slime) entity;

            //If the slime has a min and max potential size, then assign them a random in that range
            //Otherwise, assign them the default size.
            if (data.getSizeMin() > 0 && data.getSizeMax() > data.getSizeMin()) {
                slime.setSize(NumberUtil.getRandomInRange(data.getSizeMin(), data.getSizeMax()));
            } else if (data.getSize() > 0) {
                slime.setSize(data.getSize());
            }
        }

        if (entity instanceof Creeper) {
            Creeper creeper = (Creeper) entity;
            creeper.setPowered(data.isPowered());
        }

        if (entity instanceof LivingEntity) {
            LivingEntity le = (LivingEntity) entity;
            if (data.getHealth() > 0 && data.getMaxHealth() >= data.getHealth()) {

                Entities.setMaxHealth(le, data.getMaxHealth());
                Entities.setHealth(le, data.getHealth());
            }

            if (!StringUtils.isEmpty(data.getName())) {
                Entities.setName(le, data.getName(), true);
            }

            //Equip the entity with the equipment set
            Entities.setEquipment(le, data.getArmorInventory());
        }
    }
}