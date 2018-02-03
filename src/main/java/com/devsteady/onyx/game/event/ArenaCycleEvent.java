package com.devsteady.onyx.game.event;

import com.devsteady.onyx.game.CraftGame;
import com.devsteady.onyx.game.world.Arena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * An event called whenever an arena is cycled inside the MiniGame engine.
 */
public class ArenaCycleEvent extends Event implements Cancellable {
    private static HandlerList handlers = new HandlerList();

    private boolean cancelled = false;

    private Arena from;

    private Arena to;

    private CraftGame game;

    public ArenaCycleEvent(CraftGame game, Arena from, Arena to) {
        this.game = game;
        this.from = from;
        this.to = to;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public Arena getFrom() {
        return from;
    }

    public Arena getTo() {
        return to;
    }

    public void setTo(Arena arena) {
        this.to = arena;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CraftGame getGame() {
        return game;
    }
}
