package com.devsteady.onyx;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.chat.menu.ChatMenuCommandListener;
import com.devsteady.onyx.listeners.*;
import com.devsteady.onyx.nms.NMS;
import com.devsteady.onyx.player.OnyxPlayerManager;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.plugin.BukkitPlugin;
import com.devsteady.onyx.plugin.Plugins;
import com.devsteady.onyx.utilities.ReflectionUtilities;
import com.devsteady.onyx.world.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.*;

public class Onyx extends BukkitPlugin {
    private static Onyx plugin;

    /*
    A(n) instance of the players class
    to internally manage data of players for Onyx.
     */
    private OnyxPlayerManager players;

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
        Create the players instance, used internally to track commons-required
        player data for methods, and tasks.

        Externally the Players class provides a static API
         */
        players = new OnyxPlayerManager();

        registerListeners(); // Register all our event listeners

        //Load all the players data
        for (Player player : Players.allPlayers()) {
            players.addUser(player);
        }
    }

    @Override
    public void shutdown() {
        for (Player player : Players.allPlayers()) {
            UUID playerId = player.getUniqueId();
            players.removeUser(playerId);
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
                new MenuInventoryListener(),
                //Used with the Weapons API.
                new EntityDamageEntityListener(),
                new EntityDamageListener(),
                new DebugModeListener(),
                new GadgetListener(),
                new PlayerConnectionListener(),
                /* Will handle the join / leave events and give us gud datas. */
                players
        );
    }

    public OnyxPlayerManager getPlayerHandler() {
        return players;
    }

    public ChatMenuCommandListener getChatMenuListener() {
        return chatMenuListener;
    }

    @Override
    public void initConfig() {
        //todo add handling of messages class.
    }
}