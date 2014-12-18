package com.caved_in.commons;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.commands.*;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.config.WarpConfig;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.debug.actions.*;
import com.caved_in.commons.item.ItemSetManager;
import com.caved_in.commons.item.SavedItemManager;
import com.caved_in.commons.listeners.*;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.sql.ServerDatabaseConnector;
import com.caved_in.commons.warp.Warps;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public class Commons extends BukkitPlugin {
    private static Commons plugin;

    public static final String WARP_DATA_FOLDER = "plugins/Commons/Warps/";
    public static final String PLUGIN_DATA_FOLDER = "plugins/Commons/";
    public static final String DEBUG_DATA_FOLDER = "plugins/Commons/Debug/";
    public static final String ITEM_DATA_FOLDER = "plugins/Commons/Items";

    private static Configuration globalConfig = new Configuration();

    /*
    A(n) instance of the players class
    to internally manage data of players for Commons.
     */
    private Players players;

    /*
    The item set manager; Manages interchangable
    sets that players can select
    */
    private ItemSetManager itemSetManager;

    /*
    Instance of the chat class,
    used to manage private messages between players.
     */
    private Chat chat;

    /*
    Instance of the Commons database connector; Handles
    saving and loading of data to and from the database.
     */
    private ServerDatabaseConnector database = null;

    public static synchronized Commons getInstance() {
        if (plugin == null) {
            plugin = (Commons) Plugins.getPlugin("Commons");
        }
        return plugin;
    }


    public void startup() {
        chat = new Chat();

		/*
        Create the item-set manager, which allows us to save a players inventories
		and swap them out at any time; Useful for creative, kits, etc.
		 */
        itemSetManager = new ItemSetManager();

        /*
        Create the players instance, used internally to track commons-required
        player data for methods, and tasks.

        Externally the Players class provides a static API
         */
        players = new Players();

        //If the SQL Backend is enabled, then register all the database interfaces
        if (hasSqlBackend()) {
            SqlConfiguration sqlConfig = globalConfig.getSqlConfig();
            //Create the database connection
            database = new ServerDatabaseConnector(sqlConfig);
        }

        //If the commands are to be registered: do so.
        if (getConfiguration().registerCommands()) {
            registerCommands(
                    new AddCurrencyCommand(),
                    new ArmorCommand(),
                    new BackCommand(),
                    new BlockTextCommand(),
                    new BuyPremiumCommand(),
                    new ClearInventoryCommand(),
                    new DayCommand(),
                    new DebugModeCommand(),
                    new EnchantCommand(),
                    new FeedCommand(),
                    new FireworksCommand(),
                    new FlyCommand(),
                    new GamemodeCommand(),
                    new HatCommand(),
                    new HealCommand(),
                    new IdCommand(),
                    new ItemCommand(),
                    new MaintenanceCommand(),
                    new MessageCommand(),
                    new MoreCommand(),
                    new NightCommand(),
                    new PotionCommand(),
                    new QuickResponseCommand(),
                    new RecipeCommand(),
                    new RemovePremiumCommand(),
                    new RenameCommand(),
                    new RepairCommand(),
                    new SetSpawnCommand(),
                    new SetWarpCommand(),
                    new SilenceCommand(),
                    new SkullCommand(),
                    new SlayCommand(),
                    new SpawnCommand(),
                    new SpawnMobCommand(),
                    new SpeedCommand(),
                    new TeleportAllCommand(),
                    new TeleportCommand(),
                    new TeleportPositionCommand(),
                    new TeleportHereCommand(),
                    new TimeCommand(),
                    new TunnelsXPCommand(),
                    new UnbanCommand(),
                    new UnsilenceCommand(),
                    new WarpCommand(),
                    new WarpsCommand(),
                    new WorkbenchCommand()
            );
        }

        //Register the debugger actions and triggers to 'case test' features in-game
        registerDebugActions();

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
        for (Player player : Players.allPlayers()) {
            UUID playerId = player.getUniqueId();
            Players.removeData(playerId);
        }
        Warps.saveWarps();
    }

    @Override
    public String getVersion() {
        return "1.7.0";
    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }

    @Override
    public void initConfig() {
        Serializer configSerializer = new Persister();


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

    public static int getServerId() {
        return 0;
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
                new DebugCreatureBuilder(),
                new DebugFishCannon(),
                new DebugFlamingEnderSword(),
                new DebugScoreboardBuilder(),
                new DebugDefaultScoreboard(),
                new DebugThrowableBrick(),
                new DebugKickStick(),
                new DebugTitle()
        );
    }

    private void registerListeners() {
        WorldConfiguration worldConfig = globalConfig.getWorldConfig();

        if (!worldConfig.hasExternalChatHandler()) {
            registerListeners(new ChatListener());
            debug("&aUsing Commons Chat Listener");
        }

        if (worldConfig.hasLaunchpadPressurePlates()) {
            registerListeners(new LauncherListener()); // Register fire pad listener if its enabled
            debug("&aRegistered the fire pad listener");
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

        if (!worldConfig.isItemPickupEnabled()) {
            registerListeners(new ItemPickupListener());
            debug("&aRegistered the item-pickup listener");
        }

        if (!worldConfig.isFoodChangeEnabled()) {
            registerListeners(new FoodChangeListener());
            debug("&aRegistered the food change listener");
        }

        //If the server is backed by SQL, then push the specific listeners
        if (hasSqlBackend()) {
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
                new EntityDamageEntityListener()
        );
    }

    public void reloadConfiguration() {
        getInstance().initConfig();
    }

    public Configuration getConfiguration() {
        return globalConfig;
    }

    public WorldConfiguration getWorldConfig() {
        return globalConfig.getWorldConfig();
    }

    public WarpConfig getWarpConfig() {
        return globalConfig.getWarpConfig();
    }

    public static boolean hasSqlBackend() {
        return globalConfig.hasSqlBackend();
    }

    public static boolean bukkitVersionMatches(String versionNumber) {
        return Bukkit.getVersion().contains(versionNumber);
    }

    public ItemSetManager getItemSetManager() {
        return itemSetManager;
    }

    public Chat getChat() {
        return chat;
    }

    public ServerDatabaseConnector getServerDatabase() {
        return database;
    }

    public Players getPlayerManager() {
        return players;
    }
}