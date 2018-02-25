package com.devsteady.onyx.game;

import com.devsteady.onyx.game.clause.ServerShutdownClause;
import com.devsteady.onyx.game.players.UserManager;
import com.devsteady.onyx.game.world.ArenaManager;

import java.util.*;

/**
 * An extension of {@link CraftGame} used to easily create MiniGame-based plugins without all the boiler-plating required when normally setting them up.
 *
 * @param <T> User manager implementation for the minigame to pass it's user classes to upon join, leave, etc.
 */
public abstract class MiniGame<T extends UserManager> extends CraftGame {

	private Set<ServerShutdownClause> shutdownClauses = new HashSet<>();

	private boolean gameOver = false;

	private boolean autoSave = true;

	private ArenaManager arenaManager;

	/**
	 * Initialize the minigame without direct registration of any classes
	 */
	public MiniGame() {

	}

	@Override
	public void onEnable() {
		super.onEnable();
	}

	@Override
	public void onDisable() {
		super.onDisable();

		shutdown();

		getUserManager().disposeAll();
	}

	@Override
	public void update() {
		super.update();

		if (!shutdownClauses.isEmpty() && doShutdown()) {
			onDisable();
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
	 * Register {@link ServerShutdownClause}'s, that if passed will force the server to stop.
	 *
	 * @param clauses clauses to register.
	 */
	public void registerShutdownClauses(ServerShutdownClause... clauses) {
		Collections.addAll(shutdownClauses, clauses);
	}

	/**
	 * @return whether or not the game is over.
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Set whether or not the game is over.
	 *
	 * @param gameOver value to assign.
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	/**
	 * @return whether or not auto-save data is enabled.
	 */
	public boolean autoSave() {
		return autoSave;
	}

	/**
	 * Change whether or not to automatically save data.
	 *
	 * @param autoSave value to assign.
	 */
	public void setAutoSave(boolean autoSave) {
		this.autoSave = autoSave;
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
	 *
	 * @return author of the MiniGame
	 */
	public abstract String getAuthor();

	/**
	 * Initialize configuration in this method. Called before {@link MiniGame#startup()}
	 */
	public abstract void initConfig();

	/**
	 * Retrieve the user manager for this minigame.
	 * @return User manager for the game.
	 */
	public abstract T getUserManager();
}