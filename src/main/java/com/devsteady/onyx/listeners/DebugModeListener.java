package com.devsteady.onyx.listeners;

import com.devsteady.onyx.debug.Debugger;
import com.devsteady.onyx.player.Players;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class DebugModeListener implements Listener {

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent e) {
        if (!Players.isDebugging(e.getPlayer())) {
            return;
        }

        Debugger.debugBlockBreakEvent(e.getPlayer(), e);
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        if (!Players.isDebugging(e.getPlayer())) {
            return;
        }

        Debugger.debugCommandPreProcessEvent(e.getPlayer(),e);
    }



}
