package com.devsteady.onyx.listeners;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.Debugger;
import com.devsteady.onyx.player.MinecraftPlayer;
import com.devsteady.onyx.utilities.StringUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandPreProcessListener implements Listener {


    private static Onyx commons = Onyx.getInstance();

    public CommandPreProcessListener() {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();
        MinecraftPlayer minecraftPlayer = commons.getPlayerHandler().getData(player);

        if (minecraftPlayer.isInDebugMode()) {
            Debugger.debugCommandPreProcessEvent(player, e);
        }
    }
}
