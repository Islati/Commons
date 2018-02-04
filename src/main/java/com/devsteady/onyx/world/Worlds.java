package com.devsteady.onyx.world;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.entity.Entities;
import com.devsteady.onyx.exceptions.WorldLoadException;
import com.devsteady.onyx.location.Locations;
import com.devsteady.onyx.threading.tasks.ClearDroppedItemsThread;
import com.devsteady.onyx.time.TimeHandler;
import com.devsteady.onyx.time.TimeType;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class Worlds {
    public static World getWorld(String worldName) {
        if (!exists(worldName)) {
            boolean loaded = false;

            try {
                loaded = load(worldName);
            } catch (WorldLoadException e) {
                e.printStackTrace();
            }

            if (!loaded) {
                Chat.debug("Unable to load world " + worldName + " due to errors!");
            }
        }
        return Bukkit.getWorld(worldName);
    }

    public static World getWorld(UUID worldUUID) {
        return Bukkit.getWorld(worldUUID);
    }

    public static boolean exists(String worldName) {
        return Bukkit.getWorld(worldName) != null;
    }

    public static boolean unload(World world) {
        return Bukkit.getServer().unloadWorld(world, false);
    }

    public static boolean load(String worldName) throws WorldLoadException {
        try {
            Bukkit.getServer().createWorld(new WorldCreator(worldName));
//			if (exists(worldName)) {
//				World world = getWorld(worldName);
//				Location spawnLocation = world.getSpawnLocation();
//				world.loadChunk(spawnLocation.getBlockX(),spawnLocation.getBlockZ(),true);
//			}
        } catch (Exception ex) {
            throw new WorldLoadException(ex.getCause());
        }
        return exists(worldName);
    }

    public static void setTime(World world, WorldTime time) {
        world.setTime(time.getTime());
    }

    public static Item dropItem(Location location, ItemStack item, boolean natural) {
        Preconditions.checkNotNull(location);
        Preconditions.checkNotNull(item);

        if (item.getType() == Material.AIR) {
            return null;
        }

        World world = location.getWorld();
        if (natural) {
            return world.dropItemNaturally(location, item);
        } else {
            return world.dropItem(location, item);
        }
    }

    public static World getDefaultWorld() {
        return Bukkit.getWorlds().get(0);
    }

    public static void rollback(World world) {
        boolean save = world.isAutoSave();
        String worldName = world.getName();
        world.setAutoSave(false);
        unload(world);
        try {
            load(worldName);
        } catch (WorldLoadException e) {
            e.printStackTrace();
        }
        world.setAutoSave(save);
    }

    public static int clearDroppedItems(World world) {
        int cleaned = 0;
        /*TODO Add a "clear-Item-effect" where when the items removed an actions performed, like particles or something. */
        for (Item droppedItem : world.getEntitiesByClass(Item.class)) {
            droppedItem.remove();
            cleaned += 1;
        }
        return cleaned;
    }

    public static int clearDroppedItems(Location loc, int radius) {
        int cleaned = 0;
        for (Item item : Entities.getDroppedItemsNearLocation(loc, radius)) {
            item.remove();
            cleaned++;
        }
        return cleaned;
    }

    public static void clearDroppedItems(Location loc, int radius, int delay, TimeType duration) {
        Onyx.getInstance().getThreadManager().runTaskLater(new ClearDroppedItemsThread(loc, radius), TimeHandler.getTimeInTicks(delay, duration));
    }

    /**
     * Get the amount of mobs (all living creatures except players) in a specific world.
     *
     * @param world the world to get the mob-count for.
     * @return the amount of mobs (minus player) in the world.
     */
    public static int getMobCount(World world) {
        int playerCount = getPlayerCount(world);
        int entityCount = world.getLivingEntities().size();

        if (playerCount > 0) {
            if (entityCount < playerCount) {
                return 0;
            }

            return entityCount - playerCount;
        }

        return entityCount;
    }

    /**
     * Cleans all the entities in every world (that isn't an npc or player)
     *
     * @return The amount of entities in total that were slayed across each world;
     */
    public static int cleanAllEntities() {
        int globallyCleaned = 0;
        for (World bukkitWorld : Bukkit.getWorlds()) {
            globallyCleaned += cleanAllEntities(bukkitWorld);
        }
        return globallyCleaned;
    }

    /**
     * Cleans all the entities in the given world
     * that isn't an npc (citizens NPC) or a player
     *
     * @param world world to clean the entitied from.
     * @return number of entities that were remove during the operation.
     */
    public static int cleanAllEntities(World world) {
        int slayed = 0;
        for (LivingEntity livingEntity : world.getLivingEntities()) {
            //If it's not a citizens NPC and it's not an NPC / Player
            if (!livingEntity.hasMetadata("NPC") && !(livingEntity instanceof HumanEntity)) {
                livingEntity.remove();
                slayed++;
            }
        }
        return slayed;
    }


    /**
     * Clean all the entities in a world except the defined types (And players, and citizens NPC'S)
     *
     * @param world       world to clean of livingEntities
     * @param entityTypes entityTypes to not remove
     * @return the amount of entities that were removed during the operation.
     */
    public static int cleanAllEntitiesExcept(World world, EntityType... entityTypes) {
        int slayed = 0;
        Set<EntityType> eTypes = Sets.newHashSet(entityTypes);
        for (LivingEntity livingEntity : world.getLivingEntities()) {
            if (!eTypes.contains(livingEntity.getType())) {
                if (!livingEntity.hasMetadata("NPC") && !(livingEntity instanceof HumanEntity)) {
                    livingEntity.remove();
                    slayed++;
                }
            }
        }
        return slayed;
    }

    public static int getEntityCount(World world) {
        return world.getEntities().size();
    }

    public static int getPlayerCount(World world) {
        return world.getPlayers().size();
    }
}
