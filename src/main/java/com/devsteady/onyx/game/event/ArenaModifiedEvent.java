package com.devsteady.onyx.game.event;

import com.devsteady.onyx.game.world.Arena;
import com.devsteady.onyx.plugin.Plugins;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event called whenever an arena is modified.
 * If not cancelled, the arena has its changes saved to file.
 */
public class ArenaModifiedEvent extends Event implements Cancellable {
    public static final HandlerList handler = new HandlerList();

    private Arena arena;
    private boolean cancelled = false;

    public ArenaModifiedEvent(Arena arena) {
        this.arena = arena;
        this.cancelled = false;
    }

    public Arena getArena() {
        return arena;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static void handle(ArenaModifiedEvent e) {
        if (e.isCancelled()) {
            return;
        }
    }

    public static void throwEvent(Arena arena) {
        ArenaModifiedEvent event = new ArenaModifiedEvent(arena);
        Plugins.callEvent(event);
        handle(event);
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
