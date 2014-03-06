package com.caved_in.commons.player;

import com.caved_in.commons.npc.NPCPacketHandler;
import org.bukkit.entity.Player;

public class PlayerInjector {

	public static void injectPlayer(Player player) {
		PlayerHandler.getChannel(player).pipeline().addBefore("packet_handler", "npc_handler", new NPCPacketHandler(player));
	}

	// TODO: Fix...
	public static void uninjectPlayer(Player player) {
		PlayerHandler.getChannel(player).pipeline().remove("npc_handler");
	}
}
