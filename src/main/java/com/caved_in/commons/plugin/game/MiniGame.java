package com.caved_in.commons.plugin.game;

import com.caved_in.commons.Commons;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.plugin.game.clause.ServerShutdownClause;
import com.caved_in.commons.plugin.game.world.Arena;
import com.caved_in.commons.plugin.game.world.ArenaManager;
import com.caved_in.commons.world.Worlds;
import org.apache.commons.io.FileUtils;
import org.bukkit.event.HandlerList;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.*;

public abstract class MiniGame extends CraftGame {

	private Map<Integer, GameState> gameStates = new HashMap<>();

	private Set<ServerShutdownClause> shutdownClauses = new HashSet<>();

	private int activeState = -1;

	private boolean externalListeners = false;

	private boolean registeredStateListeners = false;

	private boolean gameOver = false;

	private boolean autoSave = true;

	private ArenaManager arenaManager;

	public void onEnable() {
		super.onEnable();
		arenaManager = new ArenaManager(this);
		loadArenas();
		if (!arenaManager.hasArenas()) {
			arenaManager.addArena(new Arena(Worlds.getDefaultWorld()));
		}
		startup();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		if (arenaManager.hasArenas()) {
			saveArenas();
		}
		shutdown();
	}

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

	protected void switchStates() {

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
		return isActiveState(id) || isActiveState(id);
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
		File arenaFolder = getArenaFolder();
		if (!arenaFolder.exists()) {
			arenaFolder.mkdirs();
		}

		Serializer serializer = new Persister();
		Collection<File> arenas = FileUtils.listFiles(arenaFolder, null, false);

		if (arenas.isEmpty()) {
			Commons.debug("No arenas loaded for " + getName());
			return;
		}

		for (File file : arenas) {
			try {
				Arena arena = serializer.read(Arena.class, file);
				arenaManager.addArena(arena);
				Commons.debug("Loaded arena " + arena.toString());
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
			Commons.debug("Saved " + arena.toString() + " to " + arenaFile.getPath());
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

	public abstract void startup();

	public abstract void shutdown();

	public abstract String getVersion();

	public abstract String getAuthor();

	public abstract void initConfig();
}
