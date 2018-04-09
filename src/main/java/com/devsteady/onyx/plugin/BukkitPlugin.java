package com.devsteady.onyx.plugin;

import com.devsteady.onyx.command.CommandHandler;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.debug.DebugHandler;
import com.devsteady.onyx.debug.Debugger;
import com.devsteady.onyx.game.gadget.Gadget;
import com.devsteady.onyx.game.gadget.Gadgets;
import com.devsteady.onyx.item.ItemMessage;
import com.devsteady.onyx.threading.RunnableManager;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;


public abstract class BukkitPlugin extends JavaPlugin implements OnyxPlugin {

	private RunnableManager threadManager;

	private ItemMessage itemMessage;

	private Logger logger = null;

	private CommandHandler commandHandler;

	/**
	 * Debug Handler defines the actions to take when performing a debug.
	 **/
	@Getter
	@Setter
	private DebugHandler debugHandler = null;

	public void onEnable() {
		initLogger();

		debugHandler = new DebugHandler(this);

        /*
		Create the command handler for annotation-based commands.
         */
		commandHandler = new CommandHandler(this);

		/*
		Create the thread manager, used to wrap tasks.
         */
		threadManager = new RunnableManager(this);

		if (Plugins.hasProtocolLib()) {
			/*
			If protocolLib is enabled then we also want to create the ItemMessage
            handler, where you use firstPageEnabled meta packets to send actionbar-like messages
             */
			itemMessage = new ItemMessage(this);
		}

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

	@Override
	public String getVersion() {
		return getDescription().getVersion();
	}

	public abstract String getAuthor();

	public abstract void initConfig();

	public void registerCommands(Object... commands) {
		commandHandler.registerCommands(commands);
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

	public RunnableManager getThreadManager() {
		return threadManager;
	}

	public ItemMessage getItemMessage() {
		return itemMessage;
	}

	public void debug(String... message) {
		getDebugHandler().process(message);
	}

	public Logger getPluginLogger() {
		return logger;
	}

	protected void initLogger() {
		if (logger != null) {
			return;
		}
		logger = new PluginLogger(this);
		logger.setUseParentHandlers(true);
	}
}
