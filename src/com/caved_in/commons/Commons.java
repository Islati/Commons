package com.caved_in.commons;

import java.io.File;

import com.caved_in.commons.commands.CommandRegister;
import com.caved_in.commons.handlers.Friends.FriendHandler;
import com.caved_in.commons.handlers.Menus.ServerSelection.ServerMenuGenerator;
import com.caved_in.commons.handlers.Menus.ServerSelection.ServerMenuWrapper;
import com.caved_in.commons.handlers.Player.PlayerHandler;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.handlers.Items.ItemHandler;
import com.caved_in.commons.handlers.SQL.BansSQL;
import com.caved_in.commons.handlers.SQL.DisguiseSQL;
import com.caved_in.commons.handlers.SQL.FriendSQL;
import com.caved_in.commons.handlers.SQL.PlayerSQL;
import com.caved_in.commons.handlers.Threading.RunnableManager;
import com.caved_in.commons.handlers.Utilities.StringUtil;
import com.caved_in.commons.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Commons extends JavaPlugin
{
	public static BansSQL bansDatabase;
	public static DisguiseSQL disguiseDatabase;
	public static FriendSQL friendDatabase;
	public static PlayerSQL playerDatabase;
	public static RunnableManager threadManager;

	private static Configuration globalConfig = new Configuration();
	
	public static ServerMenuWrapper serverMenu;

	private static String PLUGIN_DATA_FOLDER = "plugins/Tunnels-Common/";

	public static Commons getCommons()
	{
		return (Commons) Bukkit.getPluginManager().getPlugin("Tunnels-Common");
	}

	@Override
	public void onEnable()
	{
		Bukkit.getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
		threadManager = new RunnableManager(this); // New Thread handler

		if (!this.getDataFolder().exists())
		{
			this.getDataFolder().mkdir();
		}

		initConfiguration(); // Init config

		SqlConfiguration sqlConfig = globalConfig.getSqlConfig();

		bansDatabase = new BansSQL(sqlConfig); // Init connection to bans SQL
		disguiseDatabase = new DisguiseSQL(sqlConfig); // Init Disguise sql
		friendDatabase = new FriendSQL(sqlConfig); // Init friends sql
		playerDatabase = new PlayerSQL(sqlConfig);
		new CommandRegister(this); // Register commands

		registerListeners(); //Register all our event listeners

		threadManager.registerSynchRepeatTask("sqlRefresh", new Runnable() {
			@Override
			public void run()
			{
				bansDatabase.Refresh();
				disguiseDatabase.refreshConnection();
				friendDatabase.refresh();
				playerDatabase.refreshConnection();
			}
		},36000,36000); // SQL Keep alive

		for(Player player : Bukkit.getOnlinePlayers())
		{
			PlayerHandler.addData(player);
			FriendHandler.addFriendList(player.getName());
			if (globalConfig.getWorldConfig().isCompassMenuEnabled())
			{
				if (!player.getInventory().contains(Material.COMPASS))
				{
					player.getInventory().addItem(ItemHandler.makeItemStack(Material.COMPASS, ChatColor.GREEN + "Server Selector"));
				}
			}
		}
	}

	private void registerListeners()
	{
		if (!globalConfig.getWorldConfig().hasExternalChatHandler())
		{
			registerListener(new ChatListener());
			messageConsole("&aUsing Tunnels-Common Chat Listener");
		}

		if (globalConfig.getWorldConfig().isCompassMenuEnabled())
		{
			serverMenu = new ServerMenuWrapper("Server Selection", ServerMenuGenerator.generateMenuItems(Commons.getConfiguration().getItemMenuConfig().getXmlItems()));
			registerListener(new CompassListener());
			messageConsole("&aRegistered the compass-menu listener");
		}

		if (globalConfig.getWorldConfig().hasLaunchpadPressurePlates())
		{
			registerListener(new LauncherListener()); // Register launch pad listener if its enabled
			messageConsole("&aRegistered the launch pad listener");
		}

		if (globalConfig.getWorldConfig().isIceSpreadDisabled() || globalConfig.getWorldConfig().isSnowSpreadDisabled())
		{
			registerListener(new BlockFormListener());
			messageConsole("&aRegistered the block spread listener");
		}

		if (globalConfig.getWorldConfig().isMyceliumSpreadDisabled())
		{
			registerListener(new BlockSpreadListener());
			messageConsole("&aRegistered the mycelium spread listener");
		}

		if (globalConfig.getWorldConfig().isThunderDisabled())
		{
			registerListener(new ThungerChangeListener());
			messageConsole("&aRegistered the thunder listener");
		}

		if (globalConfig.getWorldConfig().isWeatherDisabled())
		{
			registerListener(new WeatherChangeListener());
			messageConsole("&aRegistered the Weather-Change listener");
		}

		if (globalConfig.getWorldConfig().isLightningDisabled())
		{
			registerListener(new LightningStrikeListener());
			messageConsole("&aRegistered the lightning listener");
		}

		if (!globalConfig.getWorldConfig().isBlockBreakEnabled())
		{
			registerListener(new BlockBreakListener());
			messageConsole("&aRegistered the block break listener");
		}

		if (!globalConfig.getWorldConfig().isItemDropEnabled())
		{
			registerListener(new ItemDropListener());
			messageConsole("&aRegistered the item-drop listener");
		}

		if (!globalConfig.getWorldConfig().isItemPickupEnabled())
		{
			registerListener(new ItemPickupListener());
			messageConsole("&aRegistered the item-pickup listener");
		}

		if (!globalConfig.getWorldConfig().isFoodChangeEnabled())
		{
			registerListener(new FoodChangeListener());
			messageConsole("&aRegistered the food change listener");
		}

		registerListener(new WorldLoadedListener());
		messageConsole("&aRegistered the World-Load listener");

		registerListener(new ServerPingListener());
		messageConsole("&aRegistered the Server Ping listener");

		registerListener(new PlayerLoginListener());
		messageConsole("&aRegistered the Player Login listener");

		registerListener(new PlayerJoinListener());
		messageConsole("&aRegistered the player join listener");

		registerListener(new PlayerKickListener());
		messageConsole("&aRegistered the player kick listener");

		registerListener(new CommandPreProcessListener());
		messageConsole("&aRegistered the command pre-process listener");

		registerListener(new PrePlayerLoginListener());
		messageConsole("&aRegistered the player pre-login listener");

		registerListener(new PlayerQuitListener());
		messageConsole("&aRegistered the Player Quit listener");
	}

	private void registerListener(Listener listener)
	{
		Bukkit.getPluginManager().registerEvents(listener,this);
	}

	public static void messageConsole(String message)
	{
		Bukkit.getConsoleSender().sendMessage(StringUtil.formatColorCodes(message));
	}

	private boolean initConfiguration()
	{
		Serializer configSerializer = new Persister();
		try
		{
			File Config = new File(PLUGIN_DATA_FOLDER + "Config.xml");
			if (!Config.exists())
			{
				configSerializer.write(new Configuration(), Config);
			}
			globalConfig = configSerializer.read(Configuration.class, Config);
			return true;
		}
		catch (Exception Ex)
		{
			Ex.printStackTrace();
			return false;
		}
	}

	public static boolean reloadConfiguration()
	{
		return getCommons().initConfiguration();
	}

	public static Configuration getConfiguration()
	{
		return globalConfig;
	}

	@Override
	public void onDisable()
	{
		HandlerList.unregisterAll(this);
		Bukkit.getScheduler().cancelTasks(this);
		
		for(Player player : Bukkit.getOnlinePlayers())
		{
			PlayerHandler.removeData(player.getName());
			FriendHandler.removeFriendList(player.getName());
			disguiseDatabase.deletePlayerDisguiseData(player.getName());
		}
	}
}
