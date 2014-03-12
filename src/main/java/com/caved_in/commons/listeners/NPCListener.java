package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.npc.NPC;
import com.caved_in.commons.npc.NpcHandler;
import com.caved_in.commons.player.PlayerInjector;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class NPCListener implements Listener {

	@EventHandler
	public void onJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		Commons.threadManager.runTaskOneTickLater(new Runnable() {
			@Override
			public void run() {
				PlayerInjector.injectPlayer(player);
				NpcHandler.getInstance().updatePlayer(player);
			}
		});
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent event) {
		NpcHandler.getInstance().updatePlayer(event.getPlayer());
	}

	@EventHandler
	public void onRespawn(final PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		Commons.threadManager.runTaskOneTickLater(new Runnable() {
			@Override
			public void run() {
				NpcHandler.getInstance().updatePlayer(player);
			}
		});
	}

	@EventHandler
	public void onLoad(ChunkLoadEvent event) {
		NpcHandler npcHandler = NpcHandler.getInstance();
		for (NPC npc : npcHandler.getLookup().values()) {
			npcHandler.updateNPC(npc);
		}
	}

}
