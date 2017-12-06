package com.caved_in.commons.game.world;

import com.caved_in.commons.game.MiniGame;
import com.caved_in.commons.game.event.ArenaModifiedEvent;
import com.caved_in.commons.utilities.ListUtils;
import com.caved_in.commons.world.Worlds;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.Skip;
import com.caved_in.commons.yml.YamlConfig;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.simpleframework.xml.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Arena extends YamlConfig implements GameArena {
    @Skip
    private static final Random random = new Random();

    @Skip
    private MiniGame game;

    @Path("id")
    private int id = 0;

    @Path("name")
    private String arenaName;

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

    public Arena(int id, String arenaName, String worldName, boolean enabled, boolean stormy, List<Location> spawns, List<Integer> breakables, List<Integer> placeables) {
        this.id = id;
        this.arenaName = arenaName;
        this.worldName = worldName;
        this.enabled = enabled;
        this.stormy = stormy;
        this.spawns = spawns;
        this.breakables = breakables;
        this.placeables = placeables;
    }

    public Arena(World world) {
        arenaName = world.getName();
        worldName = world.getName();
        spawns.add(Worlds.getSpawn(world));
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public String getArenaName() {
        return arenaName;
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
        List<Location> locs = spawns.stream().collect(Collectors.toList());
        return locs;
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

    public MiniGame getGame() {
        return game;
    }

    public void setGame(MiniGame game) {
        this.game = game;
    }

    @Override
    public String toString() {
        return String.format("Arena Name: %s\nWorld Name: %s\nSpawn Amount: %s", getArenaName(), getWorldName(), getSpawnLocations().size());
    }
}
