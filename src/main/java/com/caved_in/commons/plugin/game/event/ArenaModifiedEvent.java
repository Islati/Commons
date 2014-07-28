package com.caved_in.commons.plugin.game.event;

import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.plugin.game.world.Arena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArenaModifiedEvent extends Event implements Cancellable {
	public static final HandlerList handler = new HandlerList();

	private Arena arena;
	private boolean cancelled = false;

	public ArenaModifiedEvent(Arena arena) {
		this.arena = arena;
		this.cancelled = !arena.getGame().autoSave();
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

		Arena arena = e.getArena();
		arena.getGame().saveArena(arena);
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
