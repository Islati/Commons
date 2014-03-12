package com.caved_in.commons.threading.executors;

import com.google.common.collect.MapMaker;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

class PluginDisabledListener implements Listener {
	private static ConcurrentMap<Plugin, PluginDisabledListener> listeners = new MapMaker().weakKeys().makeMap();

	// Objects that must be disabled
	private Set<Future<?>> futures = Collections.newSetFromMap(new WeakHashMap<Future<?>, Boolean>());
	private Set<ExecutorService> services = Collections.newSetFromMap(new WeakHashMap<ExecutorService, Boolean>());
	private Object setLock = new Object();

	// The plugin we're looking for
	private final Plugin plugin;
	private boolean disabled;

	private PluginDisabledListener(Plugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Retrieve the associated disabled listener.
	 *
	 * @param plugin - the plugin.
	 * @return Associated listener.
	 */
	public static PluginDisabledListener getListener(final Plugin plugin) {
		PluginDisabledListener result = listeners.get(plugin);

		if (result == null) {
			final PluginDisabledListener created = new PluginDisabledListener(plugin);
			result = listeners.putIfAbsent(plugin, created);

			if (result == null) {
				// Register listener - we can't use the normal method as the plugin might not be enabled yet
				BukkitFutures.registerEventExecutor(plugin, PluginDisableEvent.class, EventPriority.NORMAL, new EventExecutor() {
					@Override
					public void execute(Listener listener, Event event) throws EventException {
						if (event instanceof PluginDisableEvent) {
							created.onPluginDisabled((PluginDisableEvent) event);
						}
					}
				});

				result = created;
			}
		}
		return result;
	}

	/**
	 * Ensure that the given future will be cancelled when the plugin is disabled.
	 *
	 * @param future - the future to cancel.
	 */
	public void addFuture(final ListenableFuture<?> future) {
		synchronized (setLock) {
			if (disabled) {
				processFuture(future);
			} else {
				futures.add(future);
			}
		}

		// Remove the future when it has computed
		Futures.addCallback(future, new FutureCallback<Object>() {
			@Override
			public void onSuccess(Object value) {
				synchronized (setLock) {
					futures.remove(future);
				}
			}

			@Override
			public void onFailure(Throwable ex) {
				synchronized (setLock) {
					futures.remove(future);
				}
			}
		});
	}

	/**
	 * Ensure that a given service is shutdown when the plugin is disabled.
	 *
	 * @param service - the service.
	 */
	public void addService(ExecutorService service) {
		synchronized (setLock) {
			if (disabled) {
				processService(service);
			} else {
				services.add(service);
			}
		}
	}

	// Will be registered manually
	public void onPluginDisabled(PluginDisableEvent e) {
		if (e.getPlugin().equals(plugin)) {
			synchronized (setLock) {
				disabled = true;

				// Cancel all unfinished futures
				for (Future<?> future : futures) {
					processFuture(future);
				}
				for (ExecutorService service : services) {
					processService(service);
				}
			}
		}
	}

	private void processFuture(Future<?> future) {
		if (!future.isDone()) {
			future.cancel(true);
		}
	}

	private void processService(ExecutorService service) {
		service.shutdownNow();
	}
}