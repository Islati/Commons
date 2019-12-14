package com.caved_in.commons.event;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventListener<T extends Event> implements Listener {
    private final List<Consumer<T>> listeners = new ArrayList<>();

    public EventListener(Plugin parent, Class<T> eventClass) {
        this(parent,eventClass,EventPriority.NORMAL);
    }

    public EventListener(Plugin plugin, Class<T> eventClass, EventPriority priority) {
        EventExecutor eventExecutor = (listener, e) -> {
          eventListener((T)e);
        };

        plugin.getServer().getPluginManager().registerEvent(eventClass, this, priority, eventExecutor, plugin);
    }

    public boolean onEvent(Consumer<T> listener) { return this.listeners.add(listener); }

    protected void eventListener(T event) {
        for (Consumer<T> listener : listeners) {
            listener.accept(event);
        }
    }
}
