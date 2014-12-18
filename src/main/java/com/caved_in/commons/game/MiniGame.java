package com.caved_in.commons.game;

import com.caved_in.commons.game.clause.ServerShutdownClause;
import com.caved_in.commons.game.listener.GameConnectionListener;
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

public abstract class MiniGame<T extends UserManager> extends CraftGame {

	private Map<Integer, GameState> gameStates = new HashMap<>();

	private Set<ServerShutdownClause> shutdownClauses = new HashSet<>();

	private int activeState = -1;

	private boolean externalListeners = false;

	private boolean registeredStateListeners = false;

	private boolean gameOver = false;

	private boolean autoSave = true;

	private ArenaManager arenaManager;

	private GameConnectionListener connectionListener;

	/* The class which our user manager is created from */
	private Class<? extends UserManager> userManagerClass = null;

	private T userManager = null;

	public MiniGame(Class<? extends UserManager> userManager) {
		this.userManagerClass = userManager;

		/* Create the constructor the the user-manager class */
		Constructor constructor = ReflectionUtilities.getConstructor(userManagerClass);

		/* Invoke the user-manager constructor with the user class, and create the user-manager from it */
		this.userManager = ReflectionUtilities.invokeConstructor(constructor);
	}

	@Override
	public void onEnable() {
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

		/* Create the connection listener that handles the managing of game-player data */
		connectionListener = new GameConnectionListener(this);

		/* Register the game connection listener */
		registerListeners(connectionListener);

		arenaManager = new ArenaManager(this);
		loadArenas();
		if (!arenaManager.hasArenas()) {
			arenaManager.addArena(new Arena(Worlds.getDefaultWorld()));
		}

		for (Player player : Players.allPlayers()) {
			userManager.addUser(player);
		}

		super.onEnable();
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
		//If there were no listeners registered when supposed to be
		if (!hasExternalListeners() && !registeredStateListeners) {
			Plugins.registerListeners(this, activeState);
			registeredStateListeners = true;
		}

		activeState.update();

		switchStates();

		if (!shutdownClauses.isEmpty() && doShutdown()) {
			onDisable();
		}
	}

	public void switchStates() {

		GameState gameState = getActiveState();

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

	public void registerGameState(GameState state) {
		gameStates.put(state.id(), state);
	}

	public void setExternalListeners(boolean value) {
		this.externalListeners = value;
	}

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

	public boolean hasStateBeenActive(int id) {
		return isActiveState(id) || isAfterState(id);
	}

	public boolean isActiveState(int id) {
		return activeState == id;
	}

	public boolean isAfterState(int id) {
		return activeState > id;
	}

	public boolean isBeforeState(int id) {
		return activeState < id;
	}

	public void registerShutdownClauses(ServerShutdownClause... clauses) {
		Collections.addAll(shutdownClauses, clauses);
	}

	public void registerGameStates(GameState... states) {
		for (GameState state : states) {
			registerGameState(state);
		}
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public File getArenaFolder() {
		return new File("plugins/" + getName() + "/Arenas/");
	}

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

	public ArenaManager getArenaManager() {
		return arenaManager;
	}

	public Arena getActiveArena() {
		return arenaManager.getActiveArena();
	}

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

	public void saveArenas() {
		getArenaManager().getArenas().forEach(this::saveArena);
	}

	public boolean autoSave() {
		return autoSave;
	}

	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
	}

	public T getUserManager() {
		return userManager;
	}

	public abstract void startup();

	public abstract void shutdown();

	public abstract String getVersion();

	public abstract String getAuthor();

	public abstract void initConfig();
}
