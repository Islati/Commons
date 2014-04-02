package com.caved_in.commons;

import com.caved_in.commons.commands.CommandRegister;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.listeners.*;
import com.caved_in.commons.menu.serverselection.ServerMenuGenerator;
import com.caved_in.commons.menu.serverselection.ServerMenuWrapper;
import com.caved_in.commons.npc.NPC;
import com.caved_in.commons.npc.NpcHandler;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.sql.BansSQL;
import com.caved_in.commons.sql.DisguiseSQL;
import com.caved_in.commons.sql.FriendSQL;
import com.caved_in.commons.sql.PlayerSQL;
import com.caved_in.commons.threading.RunnableManager;
import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.warp.Warps;
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

	//TODO Make a way to poll server info from the database
	private static int serverId = 0;

	private static Commons plugin;

	public static BansSQL bansDatabase = null;
	public static DisguiseSQL disguiseDatabase = null;
	public static FriendSQL friendDatabase = null;
	public static PlayerSQL playerDatabase = null;

	public static RunnableManager threadManager;
	public static ServerMenuWrapper serverMenu;

	public static String WARP_DATA_FOLDER = "plugins/Tunnels-Common/Warps/";
	private static String PLUGIN_DATA_FOLDER = "plugins/Tunnels-Common/";

	private static Configuration globalConfig = new Configuration();

	public static Commons getInstance() {
		if (plugin == null) {
			plugin = (Commons) Bukkit.getPluginManager().getPlugin("Tunnels-Common");
		}
		return plugin;
	}

	public static void messageConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(StringUtil.formatColorCodes(message));
	}

	public static boolean reloadConfiguration() {
		return getInstance().initConfiguration();
	}

	public static Configuration getConfiguration() {
		return globalConfig;
	}

	public static WorldConfiguration getWorldConfig() {
		return globalConfig.getWorldConfig();
	}

	public static int getServerId() {
		return serverId;
	}

	@Override
	public void onEnable() {
		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		threadManager = new RunnableManager(this); // New Thread handler

		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}

		File warpsFolder = new File(WARP_DATA_FOLDER);
		if (!warpsFolder.exists()) {
			warpsFolder.mkdirs();
		}

		initConfiguration(); // Init config

		//If the SQL Backend is enabled, then register all the database interfaces
		if (hasSqlBackend()) {
			SqlConfiguration sqlConfig = globalConfig.getSqlConfig();

			// Init connection to bans SQL
			bansDatabase = new BansSQL(sqlConfig);
			// Init Disguise sql
			disguiseDatabase = new DisguiseSQL(sqlConfig);
			// Init friends sql
			friendDatabase = new FriendSQL(sqlConfig);
			// Initialize the players sql
			playerDatabase = new PlayerSQL(sqlConfig);

			//Register the "Keep-Alive" thread for databases
			threadManager.registerSynchRepeatTask("sqlRefresh", new Runnable() {
				@Override
				public void run() {
					bansDatabase.refreshConnection();
					disguiseDatabase.refreshConnection();
					friendDatabase.refreshConnection();
					playerDatabase.refreshConnection();
				}
			}, 36000, 36000);
		}

		//If the commands are to be registered: do so.
		if (getConfiguration().registerCommands()) {
			CommandRegister.registerCommands();
		}

		//If the NPC feature's enabled, then enable it!
		if (getConfiguration().getNPCSEnabled()) {
			NpcHandler npcHandler = NpcHandler.getInstance();

			//Npc Handler startup
			npcHandler.startUp();

			for (NPC npc : NpcHandler.getInstance().getLookup().values()) {
				npcHandler.updateNPC(npc);
			}
		}

		registerListeners(); // Register all our event listeners

		// Load all the warps
		Warps.loadWarps();

		for (Player player : Bukkit.getOnlinePlayers()) {
			Players.addData(player);
			if (getWorldConfig().isCompassMenuEnabled()) {
				if (!Players.hasItem(player, Material.COMPASS)) {
					Players.giveItem(player, Items.makeItem(Material.COMPASS, ChatColor.GREEN + "Server Selector"));
				}
			}
		}
	}

	public static void givePlayerCompassMenu(Player player) {
		return; //TODO: Make this method give the player a compass menu / server selector
	}

	private void registerListeners() {

		WorldConfiguration worldConfig = globalConfig.getWorldConfig();

		if (globalConfig.getNPCSEnabled()) {
			registerListener(new NPCListener());
			messageConsole("&aRegistered the NPC Listener");
		}

		if (!worldConfig.hasExternalChatHandler()) {
			registerListener(new ChatListener());
			messageConsole("&aUsing Tunnels-Common Chat Listener");
		}

		if (worldConfig.isCompassMenuEnabled()) {
			serverMenu = new ServerMenuWrapper("Server Selection", ServerMenuGenerator.generateMenuItems(Commons.getConfiguration().getItemMenuConfig()
					.getXmlItems()));
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

		registerListener(new BlockBreakListener());

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

		// Events that'll be registered regardless of the configuration

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

		//If the server is backed by SQL, then push the specific listeners
		if (hasSqlBackend()) {
			//Used to handle kicking of banned / temp-banned players
			registerListener(new PrePlayerLoginListener());
			messageConsole("&aRegistered the player pre-login listener");
		}

		registerListener(new PlayerQuitListener());
		messageConsole("&aRegistered the player Quit listener");

		registerListener(new InventoryListener());
		messageConsole("&aRegistered the inventory listener");

		registerListener(new PlayerTeleportListener());
		messageConsole("&aRegistered the player teleport listener");
	}

	private void registerListener(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, this);
	}

	private boolean initConfiguration() {
		Serializer configSerializer = new Persister();
		try {
			File configFile = new File(PLUGIN_DATA_FOLDER + "Config.xml");
			if (!configFile.exists()) {
				configSerializer.write(new Configuration(), configFile);
			}

			globalConfig = configSerializer.read(Configuration.class, configFile);
			return true;
		} catch (Exception Ex) {
			Ex.printStackTrace();
			return false;
		}
	}

	@Override
	public void onDisable() {

		if (getConfiguration().getNPCSEnabled()) {
			NpcHandler npcHandler = NpcHandler.getInstance();
			npcHandler.shutdown();
		}

		HandlerList.unregisterAll(this);
		Bukkit.getScheduler().cancelTasks(this);

		for (Player player : Bukkit.getOnlinePlayers()) {
			String playerName = player.getName();
			Players.removeData(playerName);
			if (hasSqlBackend()) {
				disguiseDatabase.deletePlayerDisguiseData(playerName);
			}
		}

		Warps.saveWarps();
	}

	public static boolean hasSqlBackend() {
		return globalConfig.hasSqlBackend();
	}

}
