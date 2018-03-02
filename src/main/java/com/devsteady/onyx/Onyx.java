package com.devsteady.onyx;

import com.devsteady.onyx.commands.DebugModeCommand;
import com.devsteady.onyx.listeners.*;
import com.devsteady.onyx.nms.NMS;
import com.devsteady.onyx.player.OnyxPlayerManager;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.plugin.BukkitPlugin;
import com.devsteady.onyx.utilities.ReflectionUtilities;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import java.util.*;

public class Onyx extends BukkitPlugin {
    private static Onyx plugin;

    /*
    A(n) instance of the players class
    to internally manage data of players for Onyx.
     */
    private OnyxPlayerManager players;

    public static synchronized Onyx getInstance() {
        return plugin;
    }

    @Override
    public void startup() {
        /*
        Initialize all NMS!
         */
        NMS.init();

        //Use reflection to prepare for custom enchants.
        prepForCustomEnchantments();

        registerListeners(); // Register all our event listeners

        registerCommands(new DebugModeCommand());

        //Load all the players data
        if (Players.getOnlineCount() > 0) {
            for (Player player : Players.allPlayers()) {
                players.addUser(player);
            }
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
        return "TheGamersCave";
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

    @Override
    public void initConfig() {
        plugin = this;

        /*
        Create the players instance, used internally to track commons-required
        player data for methods, and tasks.

        Externally the Players class provides a static API
         */
        players = new OnyxPlayerManager(plugin);
    }
}