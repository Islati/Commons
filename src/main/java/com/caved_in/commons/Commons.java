package com.caved_in.commons;

import com.caved_in.commons.command.CommandRegister;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.config.WarpConfig;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.debug.actions.*;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.listeners.*;
import com.caved_in.commons.menu.menus.serverselection.ServerSelectionMenu;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.sql.ServerDatabaseConnector;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.warp.Warps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.logging.Logger;

public class Commons extends BukkitPlugin {
	private static Logger logger;

	//TODO Make a way to poll server info from the database
	private static int serverId = 0;

	private static Commons plugin;

	public static ServerSelectionMenu serverMenu;

	public static final String WARP_DATA_FOLDER = "plugins/Commons/Warps/";
	public static final String PLUGIN_DATA_FOLDER = "plugins/Commons/";
	public static final String DEBUG_DATA_FOLDER = "plugins/Commons/Debug/";

	private static Configuration globalConfig = new Configuration();

	//Database connectors
	public static ServerDatabaseConnector database = null;

	public static synchronized Commons getInstance() {
		if (plugin == null) {
			plugin = (Commons) Bukkit.getPluginManager().getPlugin("Commons");
		}
		return plugin;
	}

	public static void messageConsole(String... messages) {
		messageConsole(Arrays.asList(messages));
	}

	public static void messageConsole(Collection<String> messages) {
		for (String message : messages) {
			Bukkit.getConsoleSender().sendMessage(StringUtil.formatColorCodes(message));
		}
	}

	public static void reloadConfiguration() {
		getInstance().initConfig();
	}

	public static Configuration getConfiguration() {
		return globalConfig;
	}

	public static WorldConfiguration getWorldConfig() {
		return globalConfig.getWorldConfig();
	}

	public static WarpConfig getWarpConfig() {
		return globalConfig.getWarpConfig();
	}

	public static int getServerId() {
		return serverId;
	}

	private void registerDebugActions() {
		Debugger.addDebugAction(
				new DebugPlayerSyncData(),
				new DebugHandItem(),
				new DebugHandItemSerialize(),
				new DebugItemDeserialize(),
				new DebugTimeHandler(),
				new DebugDropInventory(),
				new DebugPlayerDirection(),
				new DebugDelayedMessage(),
				new DebugArmorBuilder(),
				new DebugCreatureBuilder()
		);
	}

	private void registerListeners() {
		WorldConfiguration worldConfig = globalConfig.getWorldConfig();

		if (!worldConfig.hasExternalChatHandler()) {
			registerListeners(new ChatListener());
			debug("&aUsing Commons Chat Listener");
		}

		if (worldConfig.isCompassMenuEnabled()) {
			serverMenu = new ServerSelectionMenu("Server Selection", Commons.getConfiguration().getItemMenuConfig().getXmlItems());
		}

		registerListeners(new PlayerInteractListener());
		debug("&aRegistered Gadget Listener");

		if (worldConfig.hasLaunchpadPressurePlates()) {
			registerListeners(new LauncherListener()); // Register launch pad listener if its enabled
			debug("&aRegistered the launch pad listener");
		}

		if (worldConfig.isIceSpreadDisabled() || worldConfig.isSnowSpreadDisabled()) {
			registerListeners(new BlockFormListener());
			debug("&aRegistered the block spread listener");
		}

		if (worldConfig.isMyceliumSpreadDisabled()) {
			registerListeners(new BlockSpreadListener());
			debug("&aRegistered the mycelium spread listener");
		}

		if (worldConfig.isThunderDisabled()) {
			registerListeners(new ThungerChangeListener());
			debug("&aRegistered the thunder listener");
		}

		if (worldConfig.isWeatherDisabled()) {
			registerListeners(new WeatherChangeListener());
			debug("&aRegistered the Weather-Change listener");
		}

		if (worldConfig.isLightningDisabled()) {
			registerListeners(new LightningStrikeListener());
			debug("&aRegistered the lightning listener");
		}

		if (worldConfig.isFireSpreadDisabled()) {
			registerListeners(new FireSpreadListener());
			debug("&aRegistered the fire-spread listener");
		}

		debug("&aRegistered the block-break listener");
		registerListeners(new BlockBreakListener());

		debug("&aRegistered the creeper explode listener");
		registerListeners(new EntityExplodeListener());

		if (!worldConfig.isItemDropEnabled()) {
			registerListeners(new ItemDropListener());
			debug("&aRegistered the item-drop listener");
		}

		if (!worldConfig.isItemPickupEnabled()) {
			registerListeners(new ItemPickupListener());
			debug("&aRegistered the item-pickup listener");
		}

		if (!worldConfig.isFoodChangeEnabled()) {
			registerListeners(new FoodChangeListener());
			debug("&aRegistered the food change listener");
		}

		// Events that'll be registered regardless of the configuration

		registerListeners(new WorldLoadedListener());
		debug("&aRegistered the World-Load listener");

		registerListeners(new ServerPingListener());
		debug("&aRegistered the Server Ping listener");

		registerListeners(new PlayerLoginListener());
		debug("&aRegistered the player Login listener");

		registerListeners(new PlayerJoinListener());
		debug("&aRegistered the player join listener");

		registerListeners(new PlayerKickListener());
		debug("&aRegistered the player kick listener");

		registerListeners(new CommandPreProcessListener());
		debug("&aRegistered the command pre-process listener");

		//If the server is backed by SQL, then push the specific listeners
		if (hasSqlBackend()) {
			//Used to handle kicking of banned / temp-banned players
			registerListeners(new PrePlayerLoginListener());
			debug("&aRegistered the player pre-login listener");
		}

		registerListeners(new PlayerQuitListener());
		debug("&aRegistered the player Quit listener");

		registerListeners(new InventoryListener());
		debug("&aRegistered the inventory listener & ItemMenu Listeners");

		registerListeners(new PlayerTeleportListener());
		debug("&aRegistered the player teleport listener");
	}

	public void startup() {
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		File warpsFolder = new File(WARP_DATA_FOLDER);
		if (!warpsFolder.exists()) {
			warpsFolder.mkdirs();
		}

		File debugFolder = new File(DEBUG_DATA_FOLDER);
		if (!debugFolder.exists()) {
			debugFolder.mkdirs();
		}

		//If the SQL Backend is enabled, then register all the database interfaces
		if (hasSqlBackend()) {
			SqlConfiguration sqlConfig = globalConfig.getSqlConfig();
			//Create the database connection
			database = new ServerDatabaseConnector(sqlConfig);
		}

		//If the commands are to be registered: do so.
		if (getConfiguration().registerCommands()) {
			CommandRegister.registerCommands();
		}

		//Register the debugger actions and triggers to 'case test' features in-game
		registerDebugActions();

		registerListeners(); // Register all our event listeners

		// Load all the warps
		Warps.loadWarps();

		//Load all the players data
		for (Player player : Players.allPlayers()) {
			Players.addData(player);
		}

		//If the compass menu is enabled give everyone it
		if (getWorldConfig().isCompassMenuEnabled()) {
			for (Player player : Players.allPlayers()) {
				givePlayerCompassMenu(player);
			}
		}
	}

	@Override
	public void shutdown() {
		for (Player player : Players.allPlayers()) {
			UUID playerId = player.getUniqueId();
			Players.removeData(playerId);
		}
		Warps.saveWarps();
	}

	@Override
	public String getVersion() {
		return "1.5.0";
	}

	@Override
	public String getAuthor() {
		return "Brandon Curtis";
	}

	@Override
	public void initConfig() {
		Serializer configSerializer = new Persister();
		try {
			File configFile = new File(PLUGIN_DATA_FOLDER + "Config.xml");
			if (!configFile.exists()) {
				configSerializer.write(new Configuration(), configFile);
			}
			globalConfig = configSerializer.read(Configuration.class, configFile);
		} catch (Exception Ex) {
			Ex.printStackTrace();
		}
	}

	public static boolean hasSqlBackend() {
		return globalConfig.hasSqlBackend();
	}

	public static boolean bukkitVersionMatches(String versionNumber) {
		return Bukkit.getVersion().contains(versionNumber);
	}

	public static void reload() {
		Bukkit.reload();
	}

	private void givePlayerCompassMenu(Player player) {
		if (Players.hasItem(player, Material.COMPASS)) {
			return;
		}

		Players.giveItem(player, Items.makeItem(Material.COMPASS, ChatColor.GREEN + "Server Selector"));
	}
}