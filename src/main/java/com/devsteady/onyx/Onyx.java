package com.devsteady.onyx;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.chat.menu.ChatMenuCommandListener;
import com.devsteady.onyx.config.Configuration;
import com.devsteady.onyx.item.ItemSetManager;
import com.devsteady.onyx.listeners.*;
import com.devsteady.onyx.nms.NMS;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.plugin.BukkitPlugin;
import com.devsteady.onyx.plugin.Plugins;
import com.devsteady.onyx.reflection.ReflectionUtilities;
import com.devsteady.onyx.world.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class Onyx extends BukkitPlugin {
    private static Onyx plugin;

    public static final String WARP_DATA_FOLDER = "plugins/Onyx/Warps/";
    public static final String PLUGIN_DATA_FOLDER = "plugins/Onyx/";
    public static final String DEBUG_DATA_FOLDER = "plugins/Onyx/Debug/";
    public static final String ITEM_DATA_FOLDER = "plugins/Onyx/Items";
    public static final String ITEM_SET_DATA_FOLDER = "plugins/Onyx/ItemSets/";
    public static final String RULES_LOCATION = "plugins/Onyx/rules.txt";
    public static final String TELEPORT_MENU_DISABLED_LOCATION = "plugins/Onyx/disabled-teleport-menus.txt";
    public static final String DATA_OPTION_FILE = "plugins/Onyx/data-option.txt";

    private static Configuration globalConfig = null;

    /*
    A(n) instance of the worlds class
    to internally manage worlds and apply their settings.
     */
    private Worlds worlds;

    /*
    A(n) instance of the players class
    to internally manage data of players for Onyx.
     */
    private Players players;

    /*
    Create the item-set manager, which allows us to save a players inventories
    and swap them out at any time; Useful for creative, kits, etc.

    Initialized on a class-level so there's no discrepancies before the setup method is called.
     */
    private ItemSetManager itemSetManager = new ItemSetManager();

    private ChatMenuCommandListener chatMenuListener = null;

    public static synchronized Onyx getInstance() {
        if (plugin == null) {
            plugin = (Onyx) Plugins.getPlugin("Onyx");
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

        chatMenuListener = new ChatMenuCommandListener(this);


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

        //Register the debugger actions and triggers to 'case test' features in-game
        try {
            registerDebugActionsByPackage("com.devsteady.onyx.debug.actions");
        } catch (Exception e) {
            e.printStackTrace();
            Chat.debug("Unable to register all debug actions. To register via class and reflection it requires a constructor with 0 arguments");
        }

        registerListeners(); // Register all our event listeners

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
    }

    @Override
    public String getAuthor() {
        return "Brandon Curtis";
    }
    private void prepForCustomEnchantments() {
        ReflectionUtilities.setField(Enchantment.class, "acceptingNew", null, true);
    }

    private void registerListeners() {

        registerListeners(
                new BlockBreakPlaceListener(),
                new MenuInventoryListener(),
                //Listen to the command pre-process event so we can spit params at debuggers, and drop disabled commands
                new CommandPreProcessListener(),
                //Used with the Weapons API.
                new EntityDamageEntityListener(),
                new EntityDamageListener(),
                new DebugModeListener(),
                new GadgetListener(),
                new PlayerConnectionListener(this)
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
		/*
		Check if the debug data folder exists, and if not then create it!
		 */
        File debugFolder = new File(DEBUG_DATA_FOLDER);
        if (!debugFolder.exists()) {
            debugFolder.mkdirs();
        }
    }
}