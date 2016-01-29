package com.caved_in.commons;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.PrivateMessageManager;
import com.caved_in.commons.command.RegisterCommandMethodException;
import com.caved_in.commons.config.CommonsXmlConfiguration;
import com.caved_in.commons.config.CommonsYamlConfiguration;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.file.TextFile;
import com.caved_in.commons.item.ItemSetManager;
import com.caved_in.commons.item.SavedItemManager;
import com.caved_in.commons.listeners.*;
import com.caved_in.commons.menu.ChatMenuCommandListener;
import com.caved_in.commons.nms.NMS;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.sql.ServerDatabaseConnector;
import com.caved_in.commons.warp.Warps;
import com.caved_in.commons.world.Worlds;
import com.caved_in.commons.yml.InvalidConfigurationException;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.mcstats.metrics.Metrics;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Commons extends BukkitPlugin {
	private static Commons plugin;

	public static final String WARP_DATA_FOLDER = "plugins/Commons/Warps/";
	public static final String PLUGIN_DATA_FOLDER = "plugins/Commons/";
	public static final String DEBUG_DATA_FOLDER = "plugins/Commons/Debug/";
	public static final String ITEM_DATA_FOLDER = "plugins/Commons/Items";
	public static final String ITEM_SET_DATA_FOLDER = "plugins/Commons/ItemSets/";
	public static final String RULES_LOCATION = "plugins/Commons/rules.txt";
	public static final String TELEPORT_MENU_DISABLED_LOCATION = "plugins/Commons/disabled-teleport-menus.txt";
	public static final String DATA_OPTION_FILE = "plugins/Commons/data-option.txt";

	private static Configuration globalConfig = null;

	/*
	A(n) instance of the worlds class
	to internally manage worlds and apply their settings
	specified in the WorldConfiguration section of Commons Config.
	 */
	private Worlds worlds;

	/*
	A(n) instance of the players class
	to internally manage data of players for Commons.
	 */
	private Players players;

	/*
	Create the item-set manager, which allows us to save a players inventories
	and swap them out at any time; Useful for creative, kits, etc.

	Initialized on a class-level so there's no discrepancies before the setup method is called.
	 */
	private ItemSetManager itemSetManager = new ItemSetManager();

	/*
	Used to manage private messages between players.
	 */
	private PrivateMessageManager privateMessageManager;

	/*
	Instance of the Commons database connector; Handles
	saving and loading of data to and from the database.
	 */
	private ServerDatabaseConnector database = null;

	private ChatMenuCommandListener chatMenuListener = null;

	public static synchronized Commons getInstance() {
		if (plugin == null) {
			plugin = (Commons) Plugins.getPlugin("Commons");
		}
		return plugin;
	}


	public void startup() {
		/*
		Initialize all NMS!
         */
		NMS.init();

		//Use reflection to prepare for custom enchants.
		prepForCustomEnchantments();

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
			getLogger().info("Metrics for Commons has been Enabled!");
		} catch (IOException e) {
			getLogger().info("Metrics for Commons failed to enable!");
		}

		chatMenuListener = new ChatMenuCommandListener(this);

		/*
		Create the private message manager; used to track private messages for players on the server.
         */
		privateMessageManager = new PrivateMessageManager();

        /*
		Create the worlds instance
         */
		worlds = new Worlds();

        /*
		Create the players instance, used internally to track commons-required
        player data for methods, and tasks.

        Externally the Players class provides a static API
         */
		players = new Players();

		//If the SQL Backend is enabled, then register all the database interfaces
		if (globalConfig.hasSqlBackend()) {
			SqlConfiguration sqlConfig = new SqlConfiguration(
					globalConfig.getMysqlHost(),
					globalConfig.getMysqlPort(),
					globalConfig.getMysqlDatabaseName(),
					globalConfig.getMysqlUsername(),
					globalConfig.getMysqlPassword(),
					globalConfig.trackOnlinePlayerStatus()
			);
			//Create the database connection
			database = new ServerDatabaseConnector(sqlConfig);

			getThreadManager().runTaskAsync(() -> {
				database.updateServerOnlineStatus(true);
			});
		}

		//If the commands are to be registered: do so.
		if (getConfiguration().registerCommands()) {
			try {
				registerCommandsByPackage("com.caved_in.commons.command.commands");
			} catch (RegisterCommandMethodException e) {
				e.printStackTrace();
				debug("Unable to register commands; If you're using the no-commands version of commons assure '<register-commands>' inside of Config.xml is set to false. Otherwise, send the stack trace to our developers for assistance.");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		//Register the debugger actions and triggers to 'case test' features in-game
		try {
			registerDebugActionsByPackage("com.caved_in.commons.debug.actions");
		} catch (Exception e) {
			e.printStackTrace();
			Chat.debug("Unable to register all debug actions. To register via class and reflection it requires a constructor with 0 arguments");
		}

		registerListeners(); // Register all our event listeners

		// Load all the warps
		Warps.loadWarps();

		//Load all the players data
		for (Player player : Players.allPlayers()) {
			players.addData(player);
		}
	}

	@Override
	public void shutdown() {
		/*
		Update the server-online status for the server that's stopping
        to false!
         */
		if (hasDatabaseBackend()) {
			database.updateServerOnlineStatus(false);
			getLogger().info("Updated the online-status for this server to false! b-bye!");
		}

		for (Player player : Players.allPlayers()) {
			UUID playerId = player.getUniqueId();
			Players.removeData(playerId);
		}
		Warps.saveWarps();
	}

	@Override
	public String getAuthor() {
		return "Brandon Curtis";
	}

	public static class TeleportMenuSettings {
		private List<String> disabledUuids = new ArrayList<>();

		private static TeleportMenuSettings instance;

		private TextFile textFile;

		public static TeleportMenuSettings getInstance() {
			return instance;
		}

		public static void init(String path) {
			instance = new TeleportMenuSettings(path);
		}

		protected TeleportMenuSettings(String filePath) {
			textFile = new TextFile(filePath);
		}

		public boolean hasMenuDisabled(UUID id) {
			return disabledUuids.contains(id.toString());
		}

		public void disableMenu(UUID id) {
			disabledUuids.add(id.toString());
			save();
		}

		public void enableMenu(UUID id) {
			disabledUuids.remove(id.toString());
			save();
		}

		private void save() {
			textFile.overwriteFile(disabledUuids);
		}
	}

	public static class Rules {
		private static List<String> rules = Lists.newArrayList(
				"1. You may not use vulgar or abusive language.",
				"2. You musn't be racist.",
				"3. You musn't use hacks or (unnapproved) mods that give you an unfair advantage",
				"4. You may not spam",
				"5. You may not advertise",
				"6. You musn't use excessive caps",
				"7. You may not advertise any links that are not Tunnels related",
				"8. You musn't abuse glitches or game exploits",
				"9. You may not troll any of the members, or ellicit ill behaviour in any way.",
				"10. You must be respectful to players",
				"11. Do not abuse glitches, and report them if found",
				"12. Do not steal, nor cheat the server or players",
				"13. AFK Machines are forbidden."
		);

		private static File file;

		private static Rules instance;

		public static void init(File file) {
			instance = new Rules(file);
		}

		public static Rules getInstance() {
			return instance;
		}

		protected Rules(File f) {
			file = f;

			/*
			If the rules file doesn't exist, then create it!

			 */
			if (!file.exists()) {

				//todo implement option for players who have their swear filter off, to things like "don't be a dick"
				try {
					FileUtils.writeLines(file, rules);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}


			load();
		}

		public static void load() {
			try {
				rules = FileUtils.readLines(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void add(String rule) {
			rules.add(String.format("%s. %s", rules.size() + 1, rule));
			try {
				FileUtils.writeLines(file, rules, false);
			} catch (IOException e) {
				//unable to add rules.
				e.printStackTrace();
			}
		}

		public static List<String> getRules() {
			return rules;
		}

	}

	public static int getServerId() {
		return 0;
	}

	private void prepForCustomEnchantments() {
		ReflectionUtilities.setField(Enchantment.class, "acceptingNew", null, true);
	}

	private void registerListeners() {

		registerListeners(new ChatListener());
		debug("&aCreated the Chat Listener");

		if (globalConfig.hasLaunchpadPressurePlates()) {
			registerListeners(new LauncherListener()); // Register fire pad listener if its enabled
			debug("&aRegistered the fire pad listener");
		}

		if (globalConfig.disableIceAccumulation() || globalConfig.disableSnowAccumulation()) {
			registerListeners(new BlockFormListener());
			debug("&aRegistered the block spread listener");
		}

		if (globalConfig.disableMyceliumSpread()) {
			registerListeners(new BlockSpreadListener());
			debug("&aRegistered the mycelium spread listener");
		}

		if (globalConfig.disableThunder()) {
			registerListeners(new ThungerChangeListener());
			debug("&aRegistered the thunder listener");
		}

		if (globalConfig.disableWeather()) {
			registerListeners(new WeatherChangeListener());
			debug("&aRegistered the Weather-Change listener");
		}

		if (globalConfig.disableLightning()) {
			registerListeners(new LightningStrikeListener());
			debug("&aRegistered the lightning listener");
		}

		if (globalConfig.disableFireSpread()) {
			registerListeners(new FireSpreadListener());
			debug("&aRegistered the fire-spread listener");
		}

		if (!globalConfig.enableItemPickup()) {
			registerListeners(new ItemPickupListener());
			debug("&aRegistered the item-pickup listener");
		}

		if (!globalConfig.enableFoodChange()) {
			registerListeners(new FoodChangeListener());
			debug("&aRegistered the food change listener");
		}

		//If the server is backed by SQL, then push the specific listeners
		if (globalConfig.hasSqlBackend()) {
			//Used to handle kicking of banned / temp-banned players
			registerListeners(new PrePlayerLoginListener());
			debug("&aRegistered the player pre-login listener");
		}

		registerListeners(
				//Used for gadgets, interaction restriction, etc.
				new PlayerInteractListener(),
				new BlockBreakPlaceListener(),
				new EntityExplodeListener(),
				new WorldLoadedListener(),
				new ServerPingListener(),
				new PlayerLoginListener(),
				new PlayerJoinListener(),
				new PlayerKickListener(),
				new InventoryListener(),
				new PlayerTeleportListener(),
				new PlayerQuitListener(),
				//Listen to the command pre-process event so we can spit params at debuggers, and drop disabled commands
				new CommandPreProcessListener(),
				//Listen to when a player dies so we can get the return location, incase they use /back
				new PlayerDeathListener(),
				//Used to handle the dropping of weapons. and items in general.
				new ItemDropListener(),
				//Used with the Weapons API.
				new EntityDamageEntityListener(),
				new ItemBreakListener(),
				new ItemDamageListener(),
				new EntityDamageListener(),
				new SignEditListener(),
				new LeavesDecayListener()
		);
	}

	public boolean hasDatabaseBackend() {
		return Commons.getInstance().getConfiguration().hasSqlBackend();
	}

	public void reloadConfiguration() {
		getInstance().initConfig();
	}

	public Configuration getConfiguration() {
		return globalConfig;
	}

	public static boolean bukkitVersionMatches(String versionNumber) {
		return Plugins.getBukkitVersion().contains(versionNumber);
	}

	public ItemSetManager getItemSetManager() {
		return itemSetManager;
	}

	public ServerDatabaseConnector getServerDatabase() {
		return database;
	}

	public Players getPlayerHandler() {
		return players;
	}

	public PrivateMessageManager getPrivateMessageManager() {
		return privateMessageManager;
	}

	public Worlds getWorldHandler() {
		return worlds;
	}

	public boolean isServerFull() {
		return Players.getOnlineCount() >= Bukkit.getMaxPlayers();
	}

	public ChatMenuCommandListener getChatMenuListener() {
		return chatMenuListener;
	}


	private void alternateCommonsConfig(Configuration currentConfig, Configuration targetConfig) {
		/*
		Configure all the database options!
		 */
		targetConfig.setMysqlBackend(currentConfig.hasSqlBackend());
		targetConfig.setMysqlHost(currentConfig.getMysqlHost());
		targetConfig.setMysqlPort(currentConfig.getMysqlPort());
		targetConfig.setMysqlDatabaseName(currentConfig.getMysqlDatabaseName());
		targetConfig.setMysqlUsername(currentConfig.getMysqlUsername());
		targetConfig.setMysqlPassword(currentConfig.getMysqlPassword());
		targetConfig.setTrackOnlinePlayerStatus(currentConfig.trackOnlinePlayerStatus());
		targetConfig.setServerName(currentConfig.getServerName());

		/*
		Commands configuration!
		 */
		targetConfig.registerCommands(currentConfig.registerCommands());
		targetConfig.enableBukkitCommands(currentConfig.enableBukkitCommands());
		targetConfig.enablePluginsCommand(currentConfig.enablePluginsCommand());

		/*
		Server-specific configuration!
		 */
		targetConfig.enableJoinMessages(currentConfig.enableJoinMessages());
		targetConfig.enableLeaveMessages(currentConfig.enableLeaveMessages());
		targetConfig.enableKickMessages(currentConfig.enableKickMessages());
		targetConfig.externalChatPlugin(currentConfig.hasExternalChatPlugin());
		targetConfig.silenceChat(currentConfig.isChatSilenced());

		/*
		Premium Only mode configuration!
		 */
		targetConfig.setPremiumOnlyMode(currentConfig.isPremiumOnlyMode());
		targetConfig.setPremiumUserPermission(currentConfig.getPremiumUserPermission());
		targetConfig.premiumOnlyModeKickMessage(currentConfig.getPremiumOnlyModeKickMessage());
		targetConfig.kickNonPremiumPlayerWhenFull(currentConfig.kickNonPremiumPlayerWhenFull());
		targetConfig.setKickNonPremiumMessage(currentConfig.kickNonPremiumMessage());

		/*
		World configuration!
		 */
		targetConfig.teleportToSpawnOnJoin(currentConfig.teleportToSpawnOnJoin());
		targetConfig.disableWeather(currentConfig.disableWeather());
		targetConfig.disableLightning(currentConfig.disableLightning());
		targetConfig.disableThunder(currentConfig.disableThunder());
		targetConfig.disableIceAccumulation(currentConfig.disableIceAccumulation());
		targetConfig.disableSnowAccumulation(currentConfig.disableSnowAccumulation());
		targetConfig.disableMyceliumSpread(currentConfig.disableMyceliumSpread());
		targetConfig.disableFireSpread(currentConfig.disableFireSpread());
		targetConfig.launchpadPressurePlates(currentConfig.hasLaunchpadPressurePlates());
		targetConfig.enableBlockBreak(currentConfig.enableBlockBreak());
		targetConfig.enableItemDrop(currentConfig.enableItemDrop());
		targetConfig.enableItemPickup(currentConfig.enableItemPickup());
		targetConfig.enableFoodChange(currentConfig.enableFoodChange());
		targetConfig.explosionFireworks(currentConfig.hasExplosionFireworks());
		targetConfig.enableFallDamage(currentConfig.enableFallDamage());
		targetConfig.disableLeavesDecay(currentConfig.disableLeavesDecay());

		/*
		Maintenance mode configuration!
		 */
		targetConfig.setMaintenanceMode(currentConfig.isMaintenanceModeEnabled());
		targetConfig.maintenanceModeKickMessage(currentConfig.maintenanceModeKickMessage());
		targetConfig.maintenanceModeMotd(currentConfig.maintenanceModeMotd());

		/*
		Debug configuration!
		 */
		targetConfig.enableStackTraceEvent(currentConfig.enableStackTraceEvent());
		targetConfig.enableStackTraceBook(currentConfig.enableStackTraceBook());
		targetConfig.enableStackTraceChat(currentConfig.enableStackTraceChat());

		/*
		Warps Configuration!
		 */
		targetConfig.enableWarpsMenu(currentConfig.enableWarpsMenu());
	}

	@Override
	public void initConfig() {
		Serializer configSerializer = new Persister();

		File dataOptionFile = new File(DATA_OPTION_FILE);

		File xmlConfigFile = new File(PLUGIN_DATA_FOLDER + "Config.xml");
		File ymlConfigFile = new File(PLUGIN_DATA_FOLDER + "config.yml");


		/*
		Check if the warps folder exists, and if not
		then create it!
		 */
		File warpsFolder = new File(WARP_DATA_FOLDER);
		if (!warpsFolder.exists()) {
			warpsFolder.mkdirs();
		}

		/*
		Create the items folder, where
		serializable items are stored
		 */
		File itemsFolder = new File(ITEM_DATA_FOLDER);
		if (!itemsFolder.exists()) {
			itemsFolder.mkdirs();
		}

        /*
		Create the sets folder for the item manager; used to save and load- track and update item sets.
         */
		File itemSetsFolder = new File(ITEM_SET_DATA_FOLDER);
		if (!itemSetsFolder.exists()) {
			itemSetsFolder.mkdirs();
		}

		Collection<File> itemSetFiles = FileUtils.listFiles(itemSetsFolder, null, false);
		if (itemSetFiles.size() > 0) {
			/* Load all the files in the item folder, into the item set manager */
			for (File file : itemSetFiles) {
				try {
					ItemSetManager.ItemSet set = configSerializer.read(ItemSetManager.ItemSet.class, file);

					if (set == null) {
						continue;
					}

					itemSetManager.addSet(set);
					debug(String.format("Loaded itemset '%s' into the ItemSet Manager", set.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		Collection<File> itemFiles = FileUtils.listFiles(itemsFolder, null, false);

		if (itemFiles.size() > 0) {
			/* Load all the files in the item folder, into the saved item manager */
			for (File file : itemFiles) {
				SavedItemManager.loadItem(file);
			}
		}

		/*
		Check if the debug data folder exists, and if not then create it!
		 */
		File debugFolder = new File(DEBUG_DATA_FOLDER);
		if (!debugFolder.exists()) {
			debugFolder.mkdirs();
		}

		/*
		Initialize the rules class!
		 */
		Rules.init(new File(RULES_LOCATION));

        /*
		Initialize the Teleport Menu Settings
         */
		TeleportMenuSettings.init(TELEPORT_MENU_DISABLED_LOCATION);

		/*
		This is a fresh install of commons!
		 */
		if (!dataOptionFile.exists() && !xmlConfigFile.exists() && !ymlConfigFile.exists()) {

			debug(
					"[===============================]",
					"|        COMMONS NOTICE         |",
					"|-------------------------------|",
					"|                               |",
					"|   Commons now support both    |",
					"|   XML and YAML (YML) Config   |",
					"|    as of release 1.8.8-3.     |",
					"|                               |",
					"|  From here out, Commons uses  |",
					"|     YML based config files    |",
					"|           by default.         |",
					"|                               |",
					"|  This may be the first time   |",
					"| you've ever used Commons, or  |",
					"| simply the first time you've  |",
					"| used it since release 1.8.8-3 |",
					"| but either way, please assure |",
					"| you've chosen your data type  |",
					"| in the 'data-option.txt' file |",
					"| inside 'Commons' plugin-data  |",
					"|            folder.            |",
					"|                               |",
					"| This can be changed down the  |",
					"| road if you choose to do so,  |",
					"| though data and config files  |",
					"|  in the opposing format will  |",
					"|    have to be converted.      |",
					"|   (Conversion is automatic)   |",
					"|                               |",
					"|   For information regarding   |",
					"| this change, and its affects, |",
					"|  visit the Commons wiki, or   |",
					"|      its release page.        |",
					"|                               |",
					"|-------------------------------|",
					"|                               |",
					"|  Thanks for choosing Commons! |",
					"[===============================]"
			);

			try {
				FileUtils.writeLines(dataOptionFile, Arrays.asList(
						"commons-data-format=yml"
				));
			} catch (IOException e) {
				e.printStackTrace();
			}

			globalConfig = new CommonsYamlConfiguration();

			try {
				((CommonsYamlConfiguration) globalConfig).init(ymlConfigFile);
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}

		if (!dataOptionFile.exists()) {
			/*
			This usually happens when servers are upgrading from a previous
			Commons version, to version 1.8.8-3

			If they have an XML Configuration file and no data option file,
			then we're going to make their default the XML Config (that they had previously)
			though at the same time inform them of how to convert, and so forth.
			 */
			if (xmlConfigFile.exists()) {
				try {
					FileUtils.writeLines(dataOptionFile, Arrays.asList(
							"commons-data-format=xml"
					));
				} catch (IOException e) {
					e.printStackTrace();
				}

				debug(
						"[===============================]",
						"|        COMMONS NOTICE         |",
						"|-------------------------------|",
						"|                               |",
						"|   Commons now support both    |",
						"|   XML and YAML (YML) Config   |",
						"|    as of release 1.8.8-3.     |",
						"|                               |",
						"|  From here out, Commons uses  |",
						"|     YML based config files    |",
						"|           by default.         |",
						"|                               |",
						"|  It seems you've used commons |",
						"|  before, and have an existing |",
						"|  configuration file available |",
						"| so we'll continue using that. |",
						"|                               |",
						"| To Convert to YML config, you |",
						"| must change 'data-option.txt' |",
						"| inside Commons plugin folder. |",
						"|                               |",
						"|     Supported options are     |",
						"| 'commons-data-format=yml' and |",
						"|    'commons-data-format=xml'  |",
						"|   which signal your format.   |",
						"|                               |",
						"| This can be changed down the  |",
						"| road if you choose to do so,  |",
						"| though data and config files  |",
						"|  in the opposing format will  |",
						"|    have to be converted.      |",
						"|   (Conversion is automatic)   |",
						"|                               |",
						"|   For information regarding   |",
						"| this change, and its affects, |",
						"|  visit the Commons wiki, or   |",
						"|      its release page.        |",
						"|                               |",
						"|-------------------------------|",
						"|                               |",
						"|  Thanks for choosing Commons! |",
						"[===============================]"
				);

				//They've got an XML File for config.
				try {
					globalConfig = configSerializer.read(CommonsXmlConfiguration.class, xmlConfigFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}

			/*
			If there's no data option file present, and there's a YML file present
			then it's likely someone deleted their data-option.txt

			We'll send a nice notice to the console, and inform them of the issues caused
			by doing this. Hopefully warning them to not do it again!

			It's also a fail-safe to prevent loading a different configuration file / type
			when it's not required.
			 */
			if (ymlConfigFile.exists()) {
				debug(
						"[===============================]",
						"|        COMMONS NOTICE         |",
						"|-------------------------------|",
						"|                               |",
						"|   Commons now support both    |",
						"|   XML and YAML (YML) Config   |",
						"|    as of release 1.8.8-3.     |",
						"|                               |",
						"| It seems you have config.yml  |",
						"|  as your config file, though  |",
						"|  you have no data-option.txt  |",
						"|                               |",
						"|  To properly config and data  |",
						"| throughout commons we require |",
						"| this file be present. It will |",
						"|  be re-created, though please |",
						"| don't remove this file again. |",
						"|                               |",
						"|-------------------------------|",
						"|                               |",
						"|  Thanks for choosing Commons! |",
						"[===============================]"
				);

				try {
					FileUtils.writeLines(dataOptionFile, Arrays.asList(
							"commons-data-format=yml"
					));
				} catch (IOException e) {
					e.printStackTrace();
				}

				globalConfig = new CommonsYamlConfiguration();
				try {
					((CommonsYamlConfiguration) globalConfig).init(ymlConfigFile);
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
					debug(
							"Commons 'plugin.yml' is invalid, and will cause",
							"the plugin to malfunction without valid",
							"configuration available....",
							"Please check the formatting of your config file, or delete",
							"it and regenerate it via restarting the server",
							"to continue!",
							"----------------",
							"COMMONS HAS BEEN DISABLED",
							"----------------");
					Plugins.disablePlugin(this);
					return;
				}
			} else {
				/*
				No data-option.txt and no config file, then we're going to want to
				 create the config.yml file by default.

				 Give them a notice of this functionality, and also explain
				 that an XML based configuration file is also
				 available if they choose!
				 */

				debug(
						"[===============================]",
						"|        COMMONS NOTICE         |",
						"|-------------------------------|",
						"|                               |",
						"|   Commons now support both    |",
						"|   XML and YAML (YML) Config   |",
						"|    as of release 1.8.8-3.     |",
						"|                               |",
						"|  From here out, Commons uses  |",
						"|     YML based config files    |",
						"|           by default.         |",
						"|                               |",
						"|  This may be the first time   |",
						"| you've ever used Commons, or  |",
						"| simply the first time you've  |",
						"| used it since release 1.8.8-3 |",
						"| but either way, please assure |",
						"| you've chosen your data type  |",
						"| in the 'data-option.txt' file |",
						"| inside 'Commons' plugin-data  |",
						"|            folder.            |",
						"|                               |",
						"| This can be changed down the  |",
						"| road if you choose to do so,  |",
						"| though data and config files  |",
						"|  in the opposing format will  |",
						"|    have to be converted.      |",
						"|                               |",
						"|   For information regarding   |",
						"| this change, and its affects, |",
						"|  visit the Commons wiki, or   |",
						"|      its release page.        |",
						"|                               |",
						"|-------------------------------|",
						"|                               |",
						"|  Thanks for choosing Commons! |",
						"[===============================]"
				);

				try {
					FileUtils.writeLines(dataOptionFile, Arrays.asList(
							"commons-data-format=yml"
					));
				} catch (IOException e) {
					e.printStackTrace();
				}

				globalConfig = new CommonsYamlConfiguration();
				try {
					((CommonsYamlConfiguration) globalConfig).init(ymlConfigFile);
				} catch (InvalidConfigurationException e) {
					//INVALID INITIAL CONFIG?
					e.printStackTrace();
				}
			}

		} else {
			/*
			In this case they actually have a data-option.txt file present,
			so we're going to parse their file for type of chosen configuration
			and see if they're converting, or simply loading the previous choice they had made.

			If they're converting, they'll have (for example) commons-data-format=yml
			in their data-option.txt and a 'Config.xml' file present in their
			plugin data folder (Or vice versa)
			 */
			String dataFileContents = null;
			try {
				dataFileContents = FileUtils.readFileToString(dataOptionFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

			/*
			If there's an error while attempting to load their data-option file
			then we're going to give them a nice message warning about this,
			and then disable commons from loading to prevent any further Errors from happening!
			 */
			if (dataFileContents == null) {
				debug(
						"[================================]",
						"|        COMMONS NOTICE          |",
						"|--------------------------------|",
						"|                                |",
						"| It appears you have an error   |",
						"| or invalid format in the file  |",
						"|  'data-option.txt' inside of   |",
						"|    Commons plugin folder.      |",
						"|                                |",
						"| Please fix this error/syntax   |",
						"| or delete it and then restart  |",
						"| your server to regenerate it.  |",
						"|                                |",
						"| This file is essential to the  |",
						"| method Commons uses to convert |",
						"|between xml and yml based files |",
						"|                                |",
						"|            ........            |",
						"| Meaning something, or someone  |",
						"|along the way broke a key part  |",
						"|of Commons functionality! Oops! |",
						"|            ........            |",
						"|                                |",
						"| No big deal. Follow the steps  |",
						"| above and everything should go |",
						"| back to normal in no time! :)  |",
						"|                                |",
						"|--------------------------------|",
						"|                                |",
						"|  ~Thanks for choosing Commons! |",
						"[================================]"
				);
				Plugins.disablePlugin(this);

				return;
			}
			/*
			Though if it makes it this far then we're going to continue with parsing
			their chosen config format, and perhaps performing a Conversion!
			 */

			String[] options = dataFileContents.split("=");
			String option = options[1].trim().replace("\n", "").replace("\r", "");
			if (option.equalsIgnoreCase("xml")) {
				/*
				They've chosen to use XMl as the configuration option.
				We check if they have a YML based configuration file available
				and if they do perform a conversion!

				If they don't have a YML based file, and they have an XML file then they're
				simply loading the configuration they had previously.
				 */

				if (ymlConfigFile.exists()) {
					CommonsYamlConfiguration yamlConfig = new CommonsYamlConfiguration();
					try {
						yamlConfig.load(ymlConfigFile);
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();
						debug(
								"Commons 'plugin.yml' is invalid, and will cause",
								"the plugin to malfunction without valid",
								"configuration available....",
								"Before continueing with the conversion from yml to XML",
								"Please check the formatting of your config file, or delete",
								"it and regenerate it via restarting the server",
								"to continue!",
								"",
								"Choosing to regenerate your config file, and deleting config.yml",
								"Will render you a fresh configuration!",
								"If this is fine by you, then so be it; Just don't say we didn't heave warning :)",
								"----------------",
								"COMMONS HAS BEEN DISABLED",
								"----------------"
						);
						Plugins.disablePlugin(this);
						return;
					}

					CommonsXmlConfiguration cxmlConfig = new CommonsXmlConfiguration();

					/*
					Attempting to move all the values from the previous config.yml
					to the new Config.xml file!

					Then after that, we delete the config.yml! (So there's no further confusion :)
					 */
					alternateCommonsConfig(yamlConfig, cxmlConfig);
					globalConfig = cxmlConfig;
					/*
					Delete the previous config.yml file to avoid issues!
					 */
					try {
						FileUtils.forceDelete(ymlConfigFile);
					} catch (IOException e) {
						e.printStackTrace();
					}

					debug(
							"[================================]",
							"|        COMMONS NOTICE          |",
							"|--------------------------------|",
							"|                                |",
							"|      ~~~ITS A SUCCESS~~~       |",
							"|                                |",
							"|  You've converted from using a |",
							"| yml based config file to using |",
							"| an xml based configuration file|",
							"|                                |",
							"| To convert back simply change  |",
							"|    the option to yml in your   |",
							"|  data-option.txt file and then |",
							"|  restart your server to start  |",
							"|the automatic conversion process|",
							"|                                |",
							"|--------------------------------|",
							"|                                |",
							"|  ~Thanks for choosing Commons! |",
							"[================================]"
					);
				} else {
					/*
					In this case, they've chosen XML as their configuration, and don't have
					a previous config.yml file to convert from!

					If they have an existing Config.xml file we'll load that bad boy up
					and use that as configuration (Meaning they already chose xml as the option before)
					though if not, they must have DELETED their configuration file in a previous attempt....
					 */

					if (xmlConfigFile.exists()) {
						try {
							globalConfig = configSerializer.read(CommonsXmlConfiguration.class, xmlConfigFile);
							if (globalConfig == null) {
							} else {
							}
						} catch (Exception e) {
							e.printStackTrace();
							Plugins.disablePlugin(this);
							return;
						}
					} else {
						//todo send notice about not deleting configuration
						/*
						In this case, they've chosen XMl as their config type, and don't (or no longer)
						have their Config.xml file! This is an issue, so we'll sort this out!
						 */
						globalConfig = new CommonsXmlConfiguration();

						try {
							configSerializer.write(globalConfig, xmlConfigFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else if (option.equalsIgnoreCase("yml")) {

				/*
				This means they're converting from a previous configuration file
				and wish to use YML instead!

				Begin the process!
				 */
				if (xmlConfigFile.exists()) {
					CommonsXmlConfiguration xmlConfiguration = null;
					try {
						xmlConfiguration = configSerializer.read(CommonsXmlConfiguration.class, xmlConfigFile);
					} catch (Exception e) {
						e.printStackTrace();
						debug(
								"====================================================",
								"There was an error while processing your Config.xml",
								"File inside of plugins/Commons/Config.xml",
								"To prevent this from happening again you need",
								"To assure the format and syntax of Config.xml",
								"Is correct (in accordance to standard XML Documents",
								"",
								"COMMONS HAS BEEN DISABLED UNTIL THIS ISSUE IS CORRECTED",
								"",
								"RESTART YOUR SERVER ONCE FIXED TO CONTINUE AS DESIRED!",
								"===================================================="
						);
						Plugins.disablePlugin(this);
						return;
					}

					/*
					If a yml file already exists and so does an XML then delete the previous
					yml file to avoid issues with instancing a new version! (Such as path changes, and such)
					 */
					if (ymlConfigFile.exists()) {
						try {
							FileUtils.forceDelete(ymlConfigFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					CommonsYamlConfiguration yamlConfiguration = new CommonsYamlConfiguration();
					alternateCommonsConfig(xmlConfiguration, yamlConfiguration);
					globalConfig = yamlConfiguration;
					try {
						yamlConfiguration.init(ymlConfigFile);
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();

						debug(
								"Commons 'plugin.yml' is invalid, and will cause",
								"the plugin to malfunction without valid",
								"configuration available....",
								"Before continueing with the conversion from XML to yml",
								"Please check the formatting of your config file, or delete",
								"it and regenerate it via restarting the server",
								"to continue!",
								"",
								"Choosing to regenerate your config file, and deleting config.xml/config.yml",
								"Will render you a fresh configuration!",
								"If this is fine by you, then so be it; Just don't say we didn't heave warning :)",
								"----------------",
								"COMMONS HAS BEEN DISABLED",
								"----------------"
						);
						Plugins.disablePlugin(this);
						return;
					}

					try {
						FileUtils.forceDelete(xmlConfigFile);
						debug("Deleted the previous Config.xml file to avoid confusion, and future potential errors!",
								"Your Commons config is now based out of config.yml");
					} catch (IOException e) {
						e.printStackTrace();
						debug("Failed to delete the previous Config.xml from your Commons plugin folder.");
					}


					debug(
							"[================================]",
							"|        COMMONS NOTICE          |",
							"|--------------------------------|",
							"|                                |",
							"|      ~~~ITS A SUCCESS~~~       |",
							"|                                |",
							"|  You've converted from using a |",
							"| xml based config file to using |",
							"| an yml based configuration file|",
							"|                                |",
							"| To convert back simply change  |",
							"|    the option to xml in your   |",
							"|  data-option.txt file and then |",
							"|  restart your server to start  |",
							"|the automatic conversion process|",
							"|                                |",
							"|--------------------------------|",
							"|                                |",
							"|  ~Thanks for choosing Commons! |",
							"[================================]"
					);

					return;
				}

				/*
				They've previously chosen to use YML as a configuration style and have an existing config file
				their plugins/Commons/ folder; Loading this file for configuration.
				 */
				if (ymlConfigFile.exists()) {

					CommonsYamlConfiguration yamlConfiguration = new CommonsYamlConfiguration();
					try {
						yamlConfiguration.load(ymlConfigFile);
						globalConfig = yamlConfiguration;
					} catch (InvalidConfigurationException e) {
						e.printStackTrace();
					}
				}

				/*
					They've chosen to use yml as their config file, though don't currently have a config.yml file!
					Send them a notice saying they've likely deleted their previous version of configuration
					and create a new one for them!
					 */
				debug(
						"[===============================]",
						"|        COMMONS NOTICE         |",
						"|-------------------------------|",
						"|                               |",
						"|   Commons now support both    |",
						"|   XML and YAML (YML) Config   |",
						"|    as of release 1.8.8-3.     |",
						"|                               |",
						"| It seems you no config.yml in |",
						"|  your Commons plugin folder   |",
						"|  but have yml as your chosen  |",
						"| data type in data-option.txt  |",
						"|                               |",
						"| A new config.yml file will be |",
						"| generated for you to use, and |",
						"| configure accordingly to your |",
						"|  likings, and servers desire. |",
						"|                               |",
						"|  To prevent this issue from   |",
						"| happening again, please don't |",
						"|  delete your config.yml file! |",
						"|                               |",
						"|-------------------------------|",
						"|                               |",
						"|  Thanks for choosing Commons! |",
						"[===============================]"
				);

				CommonsYamlConfiguration yamlConfiguration = new CommonsYamlConfiguration();
				try {
					yamlConfiguration.init(ymlConfigFile);
					globalConfig = yamlConfiguration;
				} catch (InvalidConfigurationException e) {
					e.printStackTrace();
					//Error initializing initial configutration!? SHOULD NEVER HAPPEN
				}
			} else {
			}

		}
	}
}