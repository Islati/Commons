package com.caved_in.commons.game;

/**
 * Barebones implementation of a game state, overriding the defaults defined in {@link GameState} to provide functionality.
 */
public abstract class MiniGameState implements GameState {
    private boolean setup = false;

    public abstract void update();

    public abstract int id();

    public abstract boolean switchState();

    public abstract int nextState();

    public void setSetup(boolean val) {
        setup = val;
    }

    @Override
    public boolean isSetup() {
        return setup;
    }

    @Override
    public abstract void setup();

    public abstract void destroy();
}
