package com.caved_in.commons.game;

import com.caved_in.commons.game.clause.ServerShutdownClause;
import com.caved_in.commons.game.feature.FeatureManager;
import com.caved_in.commons.game.feature.GameFeature;
import com.caved_in.commons.game.players.UserManager;
import com.caved_in.commons.game.state.GameState;
import com.caved_in.commons.game.state.GameStateManager;
import com.caved_in.commons.game.state.IGameState;
import com.caved_in.commons.game.thread.GameUpdateThread;
import com.caved_in.commons.plugin.BukkitPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Barebones implementation of the IGameCore. Extends {@link com.caved_in.commons.plugin.BukkitPlugin}
 *
 * @param <T> UserManager used to handle all the user data associated with the Game.
 */
public abstract class CraftGame<T extends UserManager> extends BukkitPlugin implements IGameCore {

    private FeatureManager featureManager;

    private Set<ServerShutdownClause> shutdownClauses = new HashSet<>();

    private GameStateManager stateManager;

    @Override
    public void onEnable() {
        /*
        Initialize the feature manager!
         */
        featureManager = new FeatureManager(this);

        /*
        Initialize the game state manager
         */
        stateManager = new GameStateManager(this);

        super.onEnable();

        //Create the core update thread and begin running it immediately, with the desired delay.
        GameUpdateThread updateThread = new GameUpdateThread(this);
        getThreadManager().registerSyncRepeatTask("Game Update", updateThread, 20, tickDelay());
    }

    public abstract void startup();

    public abstract void shutdown();

    public abstract String getAuthor();

    public abstract void initConfig();

    @Override
    public void update() {
        stateManager.update();
    }

    public abstract long tickDelay();

    public abstract T getUserManager();

    public FeatureManager getFeatureManager() {
        return featureManager;
    }

    public GameStateManager getStateManager() {
        return stateManager;
    }

    public void registerFeatures(GameFeature... features) {
        getFeatureManager().addFeatures(features);
        /*
        Register the listeners attached to each feature!
         */
        registerListeners(features);
    }

    /**
     * Register a {@link IGameState} with the minigames engine.
     * If a game-state with the given ID already exists, it's overwritten.
     *
     * @param state state to register.
     */
    public void registerGameState(GameState state) {
        getStateManager().addGameState(state);
    }


    /**
     * Register the given {@link IGameState}(s) with the minigame engine.
     *
     * @param states states to register.
     */
    public void registerGameStates(GameState... states) {
        for (GameState state : states) {
            registerGameState(state);
        }
    }

}
