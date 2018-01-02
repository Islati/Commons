package com.caved_in.commons.game.event;

import com.caved_in.commons.game.CraftGame;
import com.caved_in.commons.game.MiniGame;
import com.caved_in.commons.game.world.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * The ArenaLoadEvent is called whenever an arena is loaded into the minigames ArenaManager
 */
public class ArenaLoadEvent extends Event {
    private static HandlerList handlers = new HandlerList();

    private Arena loaded;

    private CraftGame game;

    public ArenaLoadEvent(CraftGame game, Arena loaded) {
        this.game = game;
        this.loaded = loaded;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public Arena getArena() {
        return loaded;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CraftGame getGame() {
        return game;
    }
}
