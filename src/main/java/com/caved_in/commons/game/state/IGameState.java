package com.caved_in.commons.game.state;

import org.bukkit.event.Listener;

public interface IGameState extends Listener {
    /**
     * Operations to perform when initializing the game state.
     */
    default void setup() {

    }

    /**
     * Check whether or not the state has been setup.
     * @return true if the state has been setup, false otherwise.
     */
    default boolean isSetup() {
        return false;
    }

    /**
     * Change the value of the state setup.
     * If not set to true after the initial setup, the game engine will continue to call {@link IGameState#setup()}
     * @param val value to assign.
     */
    void setSetup(boolean val);

    /**
     * Operations to perform when the game state is destroyed.
     */
    default void destroy() {

    }

    /**
     * Operations to perform when this state is ticked.
     * Logic specific to this state of the game should be written and handled here.
     */
    void update();

    /**
     * Unique identifier of the game state. Used to handle to order, registration, cycling, and execution of Game States.
     * MUST BE UNIQUE FROM ALL REGISTERED STATES.
     * @return unique game-state ID.
     */
    int id();

    /**
     * Whether or not to switch to the next state.
     * @return true to switch to the next state, false to continue executing this state.
     */
    boolean switchState();

    /**
     * Which state is to be executed once this state has been destroyed.
     * @return id of the state to be executed once this state has been destroyed.
     */
    int nextState();
}
