package com.caved_in.commons.plugin;

import com.caved_in.commons.command.CommandController;
import com.caved_in.commons.threading.RunnableManager;
import com.caved_in.commons.threading.executors.BukkitExecutors;
import com.caved_in.commons.threading.executors.BukkitScheduledExecutorService;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BukkitPlugin extends JavaPlugin {

	private BukkitScheduledExecutorService syncExecuter;

	private BukkitScheduledExecutorService asyncExecuter;

	private RunnableManager threadManager;

	public void onEnable() {
		threadManager = new RunnableManager(this);
		syncExecuter = BukkitExecutors.newSynchronous(this);
		asyncExecuter = BukkitExecutors.newAsynchronous(this);

		//If the game doesn't have a data folder then make one
		if (!Plugins.hasDataFolder(this)) {
			Plugins.makeDataFolder(this);
		}

		//Assure we've got our configuration initialized, incase any startup options require it.
		initConfig();

		//Call the startup method, where the game performs its startup logic
		startup();
	}

	public void onDisable() {
		threadManager.cancelTasks();
		shutdown();
		Plugins.unregisterHooks(this);
	}

	public abstract void startup();

	public abstract void shutdown();

	public abstract String getVersion();

	public abstract String getAuthor();

	public abstract void initConfig();

	public void registerCommands(Object... commands) {
		for (Object command : commands) {
			CommandController.registerCommands(this, command);
		}
	}

	public void registerListeners(Listener... listeners) {
		Plugins.registerListeners(this, listeners);
	}

	public BukkitScheduledExecutorService getSyncExecuter() {
		return syncExecuter;
	}

	public BukkitScheduledExecutorService getAsyncExecuter() {
		return asyncExecuter;
	}

	public RunnableManager getThreadManager() {
		return threadManager;
	}
}
