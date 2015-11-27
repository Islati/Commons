package com.caved_in.commons.game;

import com.caved_in.commons.game.clause.ServerShutdownClause;
import com.caved_in.commons.game.listener.UserManagerListener;
import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.game.world.Arena;
import com.caved_in.commons.game.world.ArenaManager;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.world.Worlds;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.logging.Logger;

/**
 * An extension of {@link CraftGame} used to easily create MiniGame-based plugins without all the boiler-plating required when normally setting them up.
 * @param <T> User manager implementation for the minigame to pass it's user classes to upon join, leave, etc.
 */
public abstract class MiniGame<T extends UserManager> extends CraftGame {

    private Map<Integer, GameState> gameStates = new HashMap<>();

    private Set<ServerShutdownClause> shutdownClauses = new HashSet<>();

    private int activeState = -1;

    private boolean externalListeners = false;

    private boolean registeredStateListeners = false;

    private boolean gameOver = false;

    private boolean autoSave = true;

    private ArenaManager arenaManager;

    private UserManagerListener userManagerListener;

    /* The class which our user manager is created from */
    private Class<? extends UserManager> userManagerClass = null;

    private T userManager = null;

    /**
     * Initialize the minigame without direct registration of any classes
     */
    public MiniGame() {

    }

    @Deprecated
    public MiniGame(Class<? extends UserManager> userManager) {
        registerUserManager(userManager);
    }

    @Override
    public void onEnable() {
        arenaManager = new ArenaManager(this);
        loadArenas();
        if (!arenaManager.hasArenas()) {
            arenaManager.addArena(new Arena(Worlds.getDefaultWorld()));
        }

        super.onEnable();

        for (Player player : Players.allPlayers()) {
            userManager.addUser(player);
        }

        if (userManager == null) {
            boolean hasClass = userManagerClass == null;
            debug(String.format("User manager class %s", hasClass ? "is null" : "isn't null"));

            if (hasClass) {
                Constructor constructor = ReflectionUtilities.getConstructor(userManagerClass);
                /* Invoke the user-manager constructor with the user class, and create the user-manager from it */
                this.userManager = ReflectionUtilities.invokeConstructor(constructor);
                debug("Created usermanager of class " + userManagerClass.getCanonicalName());
            } else {
                debug("Unable to locate the usermanager class");
            }
        }

        /*
        Now if the user manager has been initialized, we want to make sure that we're
        able to get the parent plugin from the UserManager- So assign it to be so! :)
         */
        if (userManager != null) {
            userManager.setParent(this);
        }

		/* Create the connection listener that handles the managing of game-player data */
        userManagerListener = new UserManagerListener(this);

		/* Register the game connection listener */
        registerListeners(userManagerListener);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (arenaManager.hasArenas()) {
            saveArenas();
        }

        shutdown();

        userManager.disposeAll();
        userManager = null;
    }

    @Override
    public void update() {
        GameState activeState = getActiveState();

        /*
        If we've got an active state, then continue. Otherwise we're not able to
        register the listeners (They'll be null)
         */
        if (activeState != null) {

            //If there were no listeners registered when supposed to be
            if (!hasExternalListeners() && !registeredStateListeners) {
                Plugins.registerListeners(this, activeState);
                registeredStateListeners = true;
            }

            activeState.update();
        }

        switchStates();

        if (!shutdownClauses.isEmpty() && doShutdown()) {
            onDisable();
        }
    }

    public void switchStates() {

        GameState gameState = getActiveState();

        if (gameState == null) {
            return;
        }

		/* If the active game state hasn't already been setup, then do so*/
        if (!gameState.isSetup()) {
            gameState.setup();
        }

		/* Check if we're able to switch states (conditions have been met)
		* and if not, then halt execution!
		*/
        if (!getActiveState().switchState()) {
            return;
        }

		/* Call the destroy method for the gamestate, or what happens when it's "Shut down" */
        gameState.destroy();

		/* Change the state to no longer be setup, to avoid issues */
        gameState.setSetup(false);

		/* Get and set the next active state */
        GameState nextState = getNextState();
        setActiveState(nextState);

		/*
		If the plugin isn't using exclusively external listeners, then
		unregister all the state listeners from the active state and register
		them for the next state.
		 */
        if (!hasExternalListeners()) {
            HandlerList.unregisterAll(gameState);
            //Get the next game state, and then
            Plugins.registerListeners(this, nextState);
            registeredStateListeners = true;
        }
    }

    /**
     * Check if the server is to shutdown.
     *
     * @return Whether or not to shutdown. If any of the conditions are met where the server is to shut down, true is returned, otherwise false.
     */
    protected boolean doShutdown() {
        for (ServerShutdownClause clause : shutdownClauses) {
            if (clause.shutdown()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Register a {@link GameState} with the minigames engine.
     * If a game-state with the given ID already exists, it's overwritten.
     * @param state state to register.
     */
    public void registerGameState(GameState state) {
        gameStates.put(state.id(), state);
    }

    /**
     * Assign if the MiniGame requires external listeners.
     * If true, you'll have to register your own player connection listeners, as opposed to using the ones that already exist.
     * @param value true to use external listeners, false otherwise.
     */
    @Deprecated
    public void setExternalListeners(boolean value) {
        this.externalListeners = value;
    }

    /**
     * @return Whether or not the MiniGame has external listers handling connections.
     */
    public boolean hasExternalListeners() {
        return externalListeners;
    }

    protected GameState getActiveState() {
        if (activeState == -1) {
            activeState = gameStates.keySet().stream().mapToInt((x) -> x).summaryStatistics().getMin();
        }
        return gameStates.get(activeState);
    }

    protected void setActiveState(GameState state) {
        activeState = state.id();
    }

    protected GameState getNextState() {
        return gameStates.get(getActiveState().nextState());
    }

    /**
     * Check whether or not the state of the given id has been active, or not.
     * @param id id of the state to check
     * @return true if the state is currently active, or was previously active; False otherwise.
     */
    public boolean hasStateBeenActive(int id) {
        return isActiveState(id) || isAfterState(id);
    }

    /**
     * Check whether or not the given state-id is currently active.
     * @param id id of the state to check
     * @return true if the state with the given id is currently active, false otherwise.
     */
    public boolean isActiveState(int id) {
        return activeState == id;
    }

    /**
     * Check if the active state is passed that of the id given.
     * @param id id of the state to check.
     * @return true if the state requested has already passed, false otherwise.
     */
    public boolean isAfterState(int id) {
        return activeState > id;
    }

    /**
     * Check if the active state is before that of the id given.
     * @param id id of the state to check.
     * @return true if the active state comes before the requested ID, false otherwise.
     */
    public boolean isBeforeState(int id) {
        return activeState < id;
    }

    /**
     * Register {@link ServerShutdownClause}'s, that if passed will force the server to stop.
     * @param clauses clauses to register.
     */
    public void registerShutdownClauses(ServerShutdownClause... clauses) {
        Collections.addAll(shutdownClauses, clauses);
    }

    /**
     * Register the given {@link GameState}(s) with the minigame engine.
     * @param states states to register.
     */
    public void registerGameStates(GameState... states) {
        for (GameState state : states) {
            registerGameState(state);
        }
    }

    /**
     * @return whether or not the game is over.
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Set whether or not the game is over.
     * @param gameOver value to assign.
     */
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    /**
     * @return The folder in which arena data resides in, related to the minigame.
     */
    public File getArenaFolder() {
        return new File("plugins/" + getName() + "/Arenas/");
    }

    /**
     * Load the arenas into the arena manager; Used internally. Shouldn't be called.
     */
    public void loadArenas() {
        Logger logger = getLogger();
        File arenaFolder = getArenaFolder();
        if (!arenaFolder.exists()) {
            arenaFolder.mkdirs();
        }

        Serializer serializer = new Persister();
        Collection<File> arenas = FileUtils.listFiles(arenaFolder, null, false);

        if (arenas.isEmpty()) {
            logger.info("No arenas loaded for " + getName());
            return;
        }

        for (File file : arenas) {
            try {
                Arena arena = serializer.read(Arena.class, file);
                arenaManager.addArena(arena);
                logger.info(("Loaded arena " + arena.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @return the {@link ArenaManager} used to handle all the worlds / arenas associated with this minigame.
     */
    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    /**
     * @return {@link Arena} that's currently active.
     */
    public Arena getActiveArena() {
        return arenaManager.getActiveArena();
    }

    /**
     * Save the given arena to file.
     * @param arena arena to save.
     */
    public void saveArena(Arena arena) {
        Serializer serializer = new Persister();
        File arenaFile = new File(getArenaFolder(), arena.getWorldName() + ".xml");
        try {
            serializer.write(arena, arenaFile);
            debug("Saved " + arena.toString() + " to " + arenaFile.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save all the arenas currently handled by the arena manager, to file.
     */
    public void saveArenas() {
        getArenaManager().getArenas().forEach(this::saveArena);
    }

    /**
     * @return whether or not auto-save data is enabled.
     */
    public boolean autoSave() {
        return autoSave;
    }

    /**
     * Change whether or not to automatically save data.
     * @param autoSave value to assign.
     */
    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    /**
     * Register the given class as the MiniGames {@link UserManager}.
     * Loaded via reflection, must contain the default constructor for a UserManager.
     * @param userManagerClass class to register as the user manager.
     */
    public void registerUserManager(Class<? extends UserManager> userManagerClass) {
        this.userManagerClass = userManagerClass;

		/* Create the constructor the the user-manager class */
        Constructor constructor = ReflectionUtilities.getConstructor(userManagerClass);

		/* Invoke the user-manager constructor with the user class, and create the user-manager from it */
        this.userManager = ReflectionUtilities.invokeConstructor(constructor);
    }

    /**
     * Retrieve the UserManager used to handle all the instanced player data, specific to this MiniGame.
     * @return the usermanager used to handle player data.
     */
    public T getUserManager() {
        return userManager;
    }

    /**
     * Operations to perform when the MiniGame is being initialized.
     */
    public abstract void startup();

    /**
     * Operations to perform when the MiniGame is shutting down.
     */
    public abstract void shutdown();

    /**
     * Retrieve the author of the MiniGame
     * @return author of the MiniGame
     */
    public abstract String getAuthor();

    /**
     * Initialize configuration in this method. Called before {@link MiniGame#startup()}
     */
    public abstract void initConfig();
}
