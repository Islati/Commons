package com.caved_in.commons.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import com.caved_in.commons.Commons;
import com.caved_in.commons.npc.NPC;
import com.caved_in.commons.npc.injector.PlayerInjector;

public class NPCListener implements Listener {
	
	@EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Commons.getCommons(), new Runnable() {
            @Override
            public void run() {
                PlayerInjector.injectPlayer(event.getPlayer());
                Commons.getCommons().updatePlayer(event.getPlayer());
            }
        }, 1L);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
    	Commons.getCommons().updatePlayer(event.getPlayer());
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Commons.getCommons(), new Runnable() {
            @Override
            public void run() {
            	Commons.getCommons().updatePlayer(event.getPlayer());
            }
        }, 1L);
    }

    @EventHandler
    public void onLoad(ChunkLoadEvent event) {
        for(NPC npc : Commons.getCommons().LOOKUP.values()) {
            Commons.getCommons().updateNPC(npc);
        }
    }

}
