package com.devsteady.onyx.game.state;

import com.devsteady.onyx.game.CraftGame;
import com.devsteady.onyx.plugin.Plugins;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.Map;

public class GameStateManager {
    private int activeState = -1;
    private boolean registeredActiveStateListeners = false;

    private Map<Integer, GameState> gameStates = new HashMap<>();

    private CraftGame core;

    public GameStateManager(CraftGame core) {
        this.core = core;
    }

    public void addGameState(GameState state) {
        gameStates.put(state.id(),state);
    }

    protected GameState getActiveState() {
        if (activeState == -1) {
            activeState = gameStates.keySet().stream().mapToInt((x) -> x).summaryStatistics().getMin();
        }
        return gameStates.get(activeState);
    }

    public int getActiveStateId() {
        return getActiveState().id();
    }

    public void update() {
        GameState activeState = getActiveState();

        /* Make sure we have an active state to perform actions on */
        if (activeState != null) {
            activeState.update();
        }

        handleStateManagement();
    }

    private void handleStateManagement() {
        GameState state = getActiveState();

        if (state == null) {
            return;
        }

        if (!state.isSetup()) {
            state.setup();
        }

        if (!state.hasListenersRegistered()) {
            Plugins.registerListeners(core,state);
            state.setListenersRegistered(true);
        }

        /*
        We'll switch the game states if we're required to.
        Only to the next state however, though, leave the option to switch to a specific state
        on the table for those shwifty individuals.
         */
        if (switchActiveState()) {
            switchState(getNextState());
        }
    }

    public void switchState(GameState state) {
        GameState activeState = getActiveState();

        if (activeState.hasListenersRegistered()) {
            activeState.destroy();
            activeState.setSetup(false);
            HandlerList.unregisterAll(activeState);
        }

        setActiveState(state);
    }

    public boolean switchActiveState() {
        GameState state = getActiveState();

        if (state == null) {
            return false;
        }

        return state.switchState();
    }

    public GameState getNextState() {
        return gameStates.get(getActiveState().nextState());
    }

    public void setActiveState(GameState state) {
        activeState = state.id();
    }

}
