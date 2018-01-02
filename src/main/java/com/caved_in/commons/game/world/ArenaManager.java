package com.caved_in.commons.game.world;

import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.game.event.ArenaCycleEvent;
import com.caved_in.commons.game.event.ArenaLoadEvent;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.yml.InvalidConfigurationException;
import com.caved_in.commons.yml.Path;
import com.caved_in.commons.yml.Skip;
import com.caved_in.commons.yml.YamlConfig;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.bukkit.Location;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

public class ArenaManager extends YamlConfig implements ArenaHandler<Arena> {
    @Skip
    private static final Random random = new Random();

    @Path("arenas")
    private Map<String, Arena> arenas = new HashMap<>();

    @Skip
    private String activeArena = "";

    @Skip
    private CraftGame game;

    public ArenaManager(CraftGame game) {
        this.game = game;
    }

    @Override
    public Arena getArena(String name) {
        return arenas.get(name);
    }

    @Override
    public boolean addArena(Arena arena) {
        arenas.put(arena.getArenaName(), arena);

		/*
        Call the arena load event!
		Used by games that require an action to be performed whenever an arena is loaded.
		 */
        ArenaLoadEvent event = new ArenaLoadEvent(game, arena);
        Plugins.callEvent(event);

        return true;
    }

    public Collection<Arena> getArenas() {
        return arenas.values();
    }

    @Override
    public void cycleArena() {
        Logger logger = game.getLogger();
        if (arenas.isEmpty()) {
            logger.info("Can't cycle arena because none are loaded");
            return;
        }

        List<String> arenaNames = Lists.newArrayList(arenas.keySet());

        if (arenas.size() < 3) {
            activeArena = arenaNames.get(0);
            logger.info("Not enough arenas loaded to cycle data. Min 3 required.");
            return;
        }


        String randomArena = "";
        do {
            randomArena = arenaNames.get(random.nextInt(arenaNames.size()));
        } while (activeArena.equalsIgnoreCase(randomArena));


        Arena active = getActiveArena();

		/*
		If this isn't the first arena that's been into play,
		then we can call an arena-cycle event!

		Just incase any of the minigames require a different arena to come into play ;)
		  */
        if (activeArena != null) {
            ArenaCycleEvent cycleEvent = new ArenaCycleEvent(game, active, getArena(randomArena));
            Plugins.callEvent(cycleEvent);
            if (cycleEvent.isCancelled()) {
                return;
            }

            randomArena = cycleEvent.getTo().getArenaName();
        }

        activeArena = randomArena;
    }

    @Override
    public void setActiveArena(Arena arena) {
        activeArena = arena.getWorldName();
    }

    public void setActiveArena(String world) {
        activeArena = world;
    }

    @Override
    public Arena getActiveArena() {
        if (Strings.isNullOrEmpty(activeArena)) {
            return null;
        }
        return arenas.get(activeArena);
    }

    @Override
    public void loadArenas() throws InvalidConfigurationException {
        File arenaDataFile = new File(game.getDataFolder(),"arenas.yml");

        if (!arenaDataFile.exists()) {
            init(arenaDataFile);
        }

        load(arenaDataFile);
    }

    @Override
    public void saveArenas() throws InvalidConfigurationException {
        File arenaDataFile = new File(game.getDataFolder(),"arenas.yml");

        if (!arenaDataFile.exists()) {
            init(arenaDataFile);
        }

        save(arenaDataFile);
    }

    @Override
    public void removeArena(Arena arena) {
        arenas.remove(arena.getArenaName());
    }

    @Override
    public boolean hasArenas() {
        return !arenas.isEmpty();
    }

    public void addSpawnToActiveArena(Location loc) {
        getActiveArena().addSpawn(loc);
    }

    public boolean isArena(String name) {
        return arenas.containsKey(name);
    }
}
