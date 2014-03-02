package com.caved_in.commons.npc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.caved_in.commons.npc.NPC;
import com.caved_in.commons.npc.utils.Action;

public class PlayerInteractNPCEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private final NPC npc;
    private final Action action;
    private final Player player;

    public PlayerInteractNPCEvent(NPC npc, Action action, Player player) {
        this.npc = npc;
        this.action = action;
        this.player = player;
    }

    public Action getAction() {
        return action;
    }

    public Player getPlayer() {
        return player;
    }

    public NPC getNPC() {
        return npc;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean flag) {
        this.cancelled = flag;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
