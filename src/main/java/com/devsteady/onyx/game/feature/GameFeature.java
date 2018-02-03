package com.devsteady.onyx.game.feature;

import org.bukkit.event.Listener;

public abstract class GameFeature implements Listener {
    private String id;

    private boolean enabled = true;

    public GameFeature(String id) {
        this.id = id;
    }

    public GameFeature enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String identifier() {
        return id;
    }

    public abstract boolean allowExecute();

    public abstract void tick();

    public boolean enabled() {
        return enabled;
    }
}
