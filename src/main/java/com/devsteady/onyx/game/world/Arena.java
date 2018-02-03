package com.devsteady.onyx.game.world;

import com.devsteady.onyx.game.CraftGame;
import com.devsteady.onyx.game.event.ArenaModifiedEvent;
import com.devsteady.onyx.utilities.ListUtils;
import com.devsteady.onyx.world.Worlds;
import com.devsteady.onyx.yml.Path;
import com.devsteady.onyx.yml.Skip;
import com.devsteady.onyx.yml.YamlConfig;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena extends YamlConfig implements GameArena {
    @Skip
    private static final Random random = new Random();

    @Skip
    private CraftGame game;

    @Path("id")
    private int id = 0;

    @Path("name")
    @Getter
    @Setter
    private String name;

    @Path("world")
    private String worldName;

    @Path("enabled")
    private boolean enabled = true;

    @Path("stormy")
    private boolean stormy = false;

    @Path("spawns")
    private List<Location> spawns = new ArrayList<>();

    @Path("breakable-blocks")
    private List<Integer> breakables = new ArrayList<>();

    @Path("placeable-blocks")
    private List<Integer> placeables = new ArrayList<>();

    /**
     * Initiate using a file; Generally used to load a serialized arena.
     * @param file file to load the arena data from.
     */
    public Arena(File file) {
        super(file);
    }

    public Arena(int id, String arenaName, String worldName, boolean enabled, boolean stormy, List<Location> spawns, List<Integer> breakables, List<Integer> placeables) {
        this.id = id;
        this.name = arenaName;
        this.worldName = worldName;
        this.enabled = enabled;
        this.stormy = stormy;
        this.spawns = spawns;
        this.breakables = breakables;
        this.placeables = placeables;
    }

    public Arena() {

    }

    public Arena(World world) {
        name= world.getName();
        worldName = world.getName();
        spawns.add(Worlds.getSpawn(world));
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String getWorldName() {
        return worldName;
    }

    @Override
    public World getWorld() {
        return Worlds.getWorld(worldName);
    }

    @Override
    public List<Location> getSpawnLocations() {
        return spawns;
    }

    public int getSpawnCount() {
        return spawns.size();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isLobby() {
        return false;
    }

    @Override
    public boolean isBreakable(Block block) {
        return breakables.contains(block.getTypeId());
    }

    @Override
    public boolean isPlaceable(Block block) {
        return placeables.contains(block.getTypeId());
    }

    public void setBreakable(int id) {
        breakables.add(id);
        ArenaModifiedEvent.throwEvent(this);
    }

    public void setPlaceable(int id) {
        placeables.add(id);
        ArenaModifiedEvent.throwEvent(this);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void addSpawn(Location loc) {
        spawns.add(loc);
        ArenaModifiedEvent.throwEvent(this);
    }

    public void removeSpawn(int num) {
        try {
            spawns.remove(num);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ArenaModifiedEvent.throwEvent(this);
        }
    }

    public boolean hasSpawns() {
        return !spawns.isEmpty();
    }

    public Location getRandomSpawn() {
        List<Location> spawns = getSpawnLocations();
        Validate.notEmpty(spawns);
        return ListUtils.getRandom(spawns);
    }

    public boolean isStormy() {
        return stormy;
    }

    public void setStormy(boolean b) {
        stormy = b;
        ArenaModifiedEvent.throwEvent(this);
    }

    public CraftGame getGame() {
        return game;
    }

    public void setGame(CraftGame game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return String.format("Arena Name: %s\nWorld Name: %s\nSpawn Amount: %s", getName(), getWorldName(), getSpawnLocations().size());
    }
}
