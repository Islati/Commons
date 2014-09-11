package com.caved_in.commons.plugin;

import com.caved_in.commons.command.CommandController;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.item.ItemMessage;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.threading.RunnableManager;
import com.caved_in.commons.threading.executors.BukkitExecutors;
import com.caved_in.commons.threading.executors.BukkitScheduledExecutorService;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BukkitPlugin extends JavaPlugin {

	private Serializer serializer = new Persister();

	private BukkitScheduledExecutorService syncExecuter;

	private BukkitScheduledExecutorService asyncExecuter;

	private RunnableManager threadManager;

	private ItemMessage itemMessage;

	private Logger logger;

	public void onEnable() {
		threadManager = new RunnableManager(this);
		syncExecuter = BukkitExecutors.newSynchronous(this);
		asyncExecuter = BukkitExecutors.newAsynchronous(this);

		if (Plugins.hasProtocolLib()) {
			itemMessage = new ItemMessage(this);
		}

		logger = getLogger();

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

	public void registerGadgets(Gadget... gadgets) {
		for (Gadget gadget : gadgets) {
			Gadgets.registerGadget(gadget);
		}
	}

	public void registerDebugActions(DebugAction... actions) {
		Debugger.addDebugAction(actions);
	}

	public BukkitScheduledExecutorService getSyncExecuter() {
		return syncExecuter;
	}

	public BukkitScheduledExecutorService getAsyncExecuter() {
		return asyncExecuter;
	}

	public Serializer getSerializer() {
		return serializer;
	}


	public RunnableManager getThreadManager() {
		return threadManager;
	}

	public ItemMessage getItemMessage() {
		return itemMessage;
	}

	public void debug(String... message) {
		Players.messageAll(Players.getAllDebugging(), message);
		for (String m : message) {
			logger.log(Level.INFO, m);
		}
	}
}
