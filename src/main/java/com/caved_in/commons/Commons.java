package com.caved_in.commons;

import com.caved_in.commons.chat.PrivateMessageManager;
import com.caved_in.commons.command.commands.*;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.config.SqlConfiguration;
import com.caved_in.commons.config.WorldConfiguration;
import com.caved_in.commons.debug.Debugger;
import com.caved_in.commons.debug.actions.*;
import com.caved_in.commons.file.TextFile;
import com.caved_in.commons.item.ItemSetManager;
import com.caved_in.commons.item.SavedItemManager;
import com.caved_in.commons.listeners.*;
import com.caved_in.commons.network.Bungee;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.sql.ServerDatabaseConnector;
import com.caved_in.commons.warp.Warps;
import com.caved_in.commons.world.Worlds;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.mcstats.metrics.Metrics;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Commons extends BukkitPlugin {
    private static Commons plugin;

    public static final String WARP_DATA_FOLDER = "plugins/Commons/Warps/";
    public static final String PLUGIN_DATA_FOLDER = "plugins/Commons/";
    public static final String DEBUG_DATA_FOLDER = "plugins/Commons/Debug/";
    public static final String ITEM_DATA_FOLDER = "plugins/Commons/Items";
    public static final String ITEM_SET_DATA_FOLDER = "plugins/Commons/ItemSets/";
    public static final String RULES_LOCATION = "plugins/Commons/rules.txt";
    public static final String TELEPORT_MENU_DISABLED_LOCATION = "plugins/Commons/disabled-teleport-menus.txt";

    private static Configuration globalConfig = new Configuration();

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

    public static synchronized Commons getInstance() {
        if (plugin == null) {
            plugin = (Commons) Plugins.getPlugin("Commons");
        }
        return plugin;
    }


    public void startup() {
        /* Before setting up any of the handlers, deal with the reflective / nms setup */

        //Use reflection to prepare for custom enchants.
        prepForCustomEnchantments();

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
            getLogger().info("Metrics for Commons has been Enabled!");
        } catch (IOException e) {
            getLogger().info("Metrics for Commons failed to enable!");
        }

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
            SqlConfiguration sqlConfig = globalConfig.getSqlConfig();
            //Create the database connection
            database = new ServerDatabaseConnector(sqlConfig);

            getThreadManager().runTaskAsync(() -> {
                database.updateServerOnlineStatus(true);
            });
        }

        Messenger messenger = getServer().getMessenger();
        /*
        Register the plugin message channel for BungeeCord, so we can send players around
        to other servers, and perform actions across servers!
         */
        messenger.registerOutgoingPluginChannel(this, "BungeeCord");

        /*
        Register the incoming plugin messages
         */
        messenger.registerIncomingPluginChannel(this, "BungeeCord", Bungee.getInstance());


        //If the commands are to be registered: do so.
        if (getConfiguration().registerCommands()) {
            registerCommands(
                    new AddCurrencyCommand(),
                    new ArmorCommand(),
                    new BackCommand(),
                    new BlockTextCommand(),
                    new BuyPremiumCommand(),
                    new CleanCommand(),
                    new ClearInventoryCommand(),
                    new DayCommand(),
                    new DebugModeCommand(),
                    new EnchantCommand(),
                    new FeedCommand(),
                    new FireworksCommand(),
                    new FlyCommand(),
                    //TODO Register Friend Command
                    new GadgetsCommand(),
                    new GamemodeCommand(),
                    new GrassCommand(),
                    new GodCommand(),
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
                    new RepairCommand(),
                    new RulesCommand(),
                    new SetCommand(),
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
                    new TeleportHereCommand(),
                    new TeleportOtherCommand(),
                    new TeleportPositionCommand(),
                    new TeleportRequestCommand(),
                    new TeleportMenuCommand(),
                    new TimeCommand(),
                    new TunnelsXPCommand(),
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

        try {
            File configFile = new File(PLUGIN_DATA_FOLDER + "Config.xml");
            if (!configFile.exists()) {
                configSerializer.write(new Configuration(), configFile);
            }
            globalConfig = configSerializer.read(Configuration.class, configFile);
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
		
		/*
		Initialize the rules class!
		 */
        Rules.init(new File(RULES_LOCATION));

        /*
        Initialize the Teleport Menu Settings
         */
        TeleportMenuSettings.init(TELEPORT_MENU_DISABLED_LOCATION);
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

    private void registerDebugActions() {
        Debugger.addDebugAction(
                new DebugPlayerSyncData(),
                new DebugHandItem(),
                new DebugHandItemSerialize(),
                new DebugItemDeserialize(),
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
                new DebugTitle(),
                new DebugConfirmationMenu(),
                new DebugExplosionArrow()
        );
    }

    private void registerListeners() {
        WorldConfiguration worldConfig = globalConfig.getWorldConfig();

        registerListeners(new ChatListener());
        debug("&aCreated the Chat Listener");

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
                new SignEditListener()
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
}