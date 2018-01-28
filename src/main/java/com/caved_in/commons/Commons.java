package com.caved_in.commons;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.chat.PrivateMessageManager;
import com.caved_in.commons.command.RegisterCommandMethodException;
import com.caved_in.commons.config.CommonsYamlConfiguration;
import com.caved_in.commons.config.Configuration;
import com.caved_in.commons.file.TextFile;
import com.caved_in.commons.item.ItemSetManager;
import com.caved_in.commons.item.SavedItemManager;
import com.caved_in.commons.listeners.*;
import com.caved_in.commons.chat.menu.ChatMenuCommandListener;
import com.caved_in.commons.nms.NMS;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.BukkitPlugin;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.reflection.ReflectionUtilities;
import com.caved_in.commons.warp.Warps;
import com.caved_in.commons.world.Worlds;
import com.caved_in.commons.yml.InvalidConfigurationException;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.mcstats.metrics.Metrics;

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
    to internally manage worlds and apply their settings.
     */
    private Worlds worlds;

    /*
    A(n) instance of the players class
    to internally manage data of players for Commons.
     */
    private Players players;

    /*
    Create the firstPageEnabled-set manager, which allows us to save a players inventories
    and swap them out at any time; Useful for creative, kits, etc.

    Initialized on a class-level so there's no discrepancies before the setup method is called.
     */
    private ItemSetManager itemSetManager = new ItemSetManager();

    /*
    Used to manage private messages between players.
     */
    private PrivateMessageManager privateMessageManager;

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

        //If the commands are to be registered: do so.
        try {
            registerCommandsByPackage("com.caved_in.commons.command.commands");
        } catch (RegisterCommandMethodException e) {
            e.printStackTrace();
            debug("Unable to register commands; If you're using the no-commands version of commons assure '<register-commands>' inside of Config.xml is set to false. Otherwise, send the stack trace to our developers for assistance.");
        } catch (Exception e) {
            e.printStackTrace();
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
            debug("&aRegistered the firstPageEnabled-pickup listener");
        }

        if (!globalConfig.enableFoodChange()) {
            registerListeners(new FoodChangeListener());
            debug("&aRegistered the food change listener");
        }

        registerListeners(
                //Used for gadgets, interaction restriction, etc.
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
                new EntityDamageListener(),
                new SignEditListener(),
                new LeavesDecayListener(),
                new GadgetActionListener()
        );
    }

    public void reloadConfiguration() {
        getInstance().initConfig();
    }

    public Configuration getConfiguration() {
        return globalConfig;
    }

    public ItemSetManager getItemSetManager() {
        return itemSetManager;
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

    @Override
    public void initConfig() {
        File ymlConfigFile = new File(PLUGIN_DATA_FOLDER + "config.yml");

        globalConfig = new CommonsYamlConfiguration();


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
		Create the sets folder for the firstPageEnabled manager; used to save and load- track and update firstPageEnabled sets.
         */
        File itemSetsFolder = new File(ITEM_SET_DATA_FOLDER);
        if (!itemSetsFolder.exists()) {
            itemSetsFolder.mkdirs();
        }

        Collection<File> itemSetFiles = FileUtils.listFiles(itemSetsFolder, null, false);
        if (itemSetFiles.size() > 0) {
            /* Load all the files in the firstPageEnabled folder, into the firstPageEnabled set manager */
            for (File file : itemSetFiles) {
                try {
                    ItemSetManager.ItemSet set = new ItemSetManager.ItemSet(file);

                    set.load();

                    itemSetManager.addSet(set);
                    debug(String.format("Loaded itemset '%s' into the ItemSet Manager", set.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Collection<File> itemFiles = FileUtils.listFiles(itemsFolder, null, false);
        if (itemFiles.size() > 0) {
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

    }
}