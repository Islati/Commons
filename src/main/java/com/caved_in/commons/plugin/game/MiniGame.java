package com.caved_in.commons.plugin.game;

import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.plugin.game.clause.ServerShutdownClause;
import org.bukkit.event.HandlerList;

import java.util.*;

public abstract class MiniGame extends CraftGame {

	private Map<Integer, GameState> gameStates = new HashMap<>();

	private Set<ServerShutdownClause> shutdownClauses = new HashSet<>();

	private int activeState = -1;

	private boolean externalListeners = false;

	private boolean registeredStateListeners = false;

	private boolean gameOver = false;

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

	public void onEnable() {
		super.onEnable();
		startup();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		shutdown();
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

	protected void switchStates() {

		GameState gameState = getActiveState();

		if (!getActiveState().switchState()) {
			return;
		}

		GameState nextState = getNextState();
		setActiveState(nextState);

		//If we're not using external listeners
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

	public abstract void startup();

	public abstract void shutdown();

	public abstract String getVersion();

	public abstract String getAuthor();

	public abstract void initConfig();

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

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
}
