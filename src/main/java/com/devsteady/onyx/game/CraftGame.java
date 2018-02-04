package com.devsteady.onyx.game;

import com.devsteady.onyx.game.clause.ServerShutdownClause;
import com.devsteady.onyx.game.feature.FeatureManager;
import com.devsteady.onyx.game.feature.GameFeature;
import com.devsteady.onyx.game.players.UserManager;
import com.devsteady.onyx.game.state.GameState;
import com.devsteady.onyx.game.state.GameStateManager;
import com.devsteady.onyx.game.thread.GameUpdateThread;
import com.devsteady.onyx.plugin.BukkitPlugin;

import java.util.HashSet;
import java.util.Set;

/**
 * Barebones implementation of the IGameCore. Extends {@link BukkitPlugin}
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

    @Override
    public void onDisable() {
        super.onDisable();
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

    public void registerGameState(GameState state) {
        getStateManager().addGameState(state);
    }


    public void registerGameStates(GameState... states) {
        for (GameState state : states) {
            registerGameState(state);
        }
    }

}
