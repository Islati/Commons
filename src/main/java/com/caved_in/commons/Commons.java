package com.caved_in.commons;

import com.caved_in.commons.commands.CommandRegister;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.items.ItemHandler;
import com.caved_in.commons.listeners.*;
import com.caved_in.commons.menus.serverselection.ServerMenuGenerator;
import com.caved_in.commons.menus.serverselection.ServerMenuWrapper;
import com.caved_in.commons.player.PlayerHandler;
import com.caved_in.commons.sql.BansSQL;
import com.caved_in.commons.sql.DisguiseSQL;
import com.caved_in.commons.sql.FriendSQL;
import com.caved_in.commons.sql.PlayerSQL;
import com.caved_in.commons.threading.RunnableManager;
import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

public class Commons extends JavaPlugin {
	private static Commons plugin;

	public static BansSQL bansDatabase;
	public static DisguiseSQL disguiseDatabase;
	public static FriendSQL friendDatabase;
	public static PlayerSQL playerDatabase;
	public static RunnableManager threadManager;

	private static Configuration globalConfig = new Configuration();

	public static ServerMenuWrapper serverMenu;

	private static String PLUGIN_DATA_FOLDER = "plugins/Tunnels-Common/";

	public static Commons getCommons() {
		if (plugin == null) {
			plugin = (Commons) Bukkit.getPluginManager().getPlugin("Tunnels-Common");
		}
		return plugin;
	}

	@Override
	public void onEnable() {
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		threadManager = new RunnableManager(this); // New Thread handler

		if (!this.getDataFolder().exists()) {
			this.getDataFolder().mkdir();
		}

		initConfiguration(); // Init config

		SqlConfiguration sqlConfig = globalConfig.getSqlConfig();

		//Init connection to bans SQL
		bansDatabase = new BansSQL(sqlConfig);
		//Init Disguise sql
		disguiseDatabase = new DisguiseSQL(sqlConfig);
		//Init friends sql
		friendDatabase = new FriendSQL(sqlConfig);
		//Initialize the players sql
		playerDatabase = new PlayerSQL(sqlConfig);
		//Register commands
		new CommandRegister(this);

		registerListeners(); //Register all our event listeners

		threadManager.registerSynchRepeatTask("sqlRefresh", new Runnable() {
			@Override
			public void run() {
				bansDatabase.refreshConnection();
				disguiseDatabase.refreshConnection();
				friendDatabase.refreshConnection();
				playerDatabase.refreshConnection();
			}
		}, 36000, 36000); // SQL Keep alive

		for (Player player : Bukkit.getOnlinePlayers()) {
			PlayerHandler.addData(player);
			if (globalConfig.getWorldConfig().isCompassMenuEnabled()) {
				if (!player.getInventory().contains(Material.COMPASS)) {
					player.getInventory().addItem(ItemHandler.makeItemStack(Material.COMPASS, ChatColor.GREEN + "Server Selector"));
				}
			}
		}
	}

	private void registerListeners() {
		WorldConfiguration worldConfig = globalConfig.getWorldConfig();

		if (!worldConfig.hasExternalChatHandler()) {
			registerListener(new ChatListener());
			messageConsole("&aUsing Tunnels-Common Chat Listener");
		}

		if (worldConfig.isCompassMenuEnabled()) {
			serverMenu = new ServerMenuWrapper("Server Selection", ServerMenuGenerator.generateMenuItems(Commons.getConfiguration().getItemMenuConfig().getXmlItems()));
			registerListener(new CompassListener());
			messageConsole("&aRegistered the compass-menu listener");
		}

		if (worldConfig.hasLaunchpadPressurePlates()) {
			registerListener(new LauncherListener()); // Register launch pad listener if its enabled
			messageConsole("&aRegistered the launch pad listener");
		}

		if (worldConfig.isIceSpreadDisabled() || worldConfig.isSnowSpreadDisabled()) {
			registerListener(new BlockFormListener());
			messageConsole("&aRegistered the block spread listener");
		}

		if (worldConfig.isMyceliumSpreadDisabled()) {
			registerListener(new BlockSpreadListener());
			messageConsole("&aRegistered the mycelium spread listener");
		}

		if (worldConfig.isThunderDisabled()) {
			registerListener(new ThungerChangeListener());
			messageConsole("&aRegistered the thunder listener");
		}

		if (worldConfig.isWeatherDisabled()) {
			registerListener(new WeatherChangeListener());
			messageConsole("&aRegistered the Weather-Change listener");
		}

		if (worldConfig.isLightningDisabled()) {
			registerListener(new LightningStrikeListener());
			messageConsole("&aRegistered the lightning listener");
		}

		if (worldConfig.isFireSpreadDisabled()) {
			registerListener(new FireSpreadListener());
			messageConsole("&aRegistered the fire-spread listener");
		}

		if (!worldConfig.isBlockBreakEnabled()) {
			registerListener(new BlockBreakListener());
			messageConsole("&aRegistered the block break listener");
		}

		if (!worldConfig.isItemDropEnabled()) {
			registerListener(new ItemDropListener());
			messageConsole("&aRegistered the item-drop listener");
		}

		if (!worldConfig.isItemPickupEnabled()) {
			registerListener(new ItemPickupListener());
			messageConsole("&aRegistered the item-pickup listener");
		}

		if (!worldConfig.isFoodChangeEnabled()) {
			registerListener(new FoodChangeListener());
			messageConsole("&aRegistered the food change listener");
		}

		//Events that'll be registered regardless of the configuration

		registerListener(new WorldLoadedListener());
		messageConsole("&aRegistered the World-Load listener");

		registerListener(new ServerPingListener());
		messageConsole("&aRegistered the Server Ping listener");

		registerListener(new PlayerLoginListener());
		messageConsole("&aRegistered the player Login listener");

		registerListener(new PlayerJoinListener());
		messageConsole("&aRegistered the player join listener");

		registerListener(new PlayerKickListener());
		messageConsole("&aRegistered the player kick listener");

		registerListener(new CommandPreProcessListener());
		messageConsole("&aRegistered the command pre-process listener");

		registerListener(new PrePlayerLoginListener());
		messageConsole("&aRegistered the player pre-login listener");

		registerListener(new PlayerQuitListener());
		messageConsole("&aRegistered the player Quit listener");

		registerListener(new InventoryListener());
		messageConsole("&aRegistered the inventory listener");
	}

	private void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}

	public static void messageConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(StringUtil.formatColorCodes(message));
	}

	private boolean initConfiguration() {
		Serializer configSerializer = new Persister();
		try {
			File Config = new File(PLUGIN_DATA_FOLDER + "Config.xml");
			if (!Config.exists()) {
				configSerializer.write(new Configuration(), Config);
			}
			globalConfig = configSerializer.read(Configuration.class, Config);
			return true;
		} catch (Exception Ex) {
			Ex.printStackTrace();
			return false;
		}
	}

	public static boolean reloadConfiguration() {
		return getCommons().initConfiguration();
	}

	public static Configuration getConfiguration() {
		return globalConfig;
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		Bukkit.getScheduler().cancelTasks(this);

		for (Player player : Bukkit.getOnlinePlayers()) {
			String playerName = player.getName();
			PlayerHandler.removeData(playerName);
			disguiseDatabase.deletePlayerDisguiseData(playerName);
		}
	}
}
