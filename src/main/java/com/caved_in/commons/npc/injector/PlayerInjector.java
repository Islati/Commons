package com.caved_in.commons.npc.injector;

import org.bukkit.entity.Player;

import com.caved_in.commons.npc.utils.PlayerUtil;

public class PlayerInjector {

    public static void injectPlayer(Player player) {
        PlayerUtil.getChannel(player).pipeline().addBefore("packet_handler", "npc_handler", new NPCHandler(player));
    }

    // TODO: Fix...
    public static void uninjectPlayer(Player player) {
        PlayerUtil.getChannel(player).pipeline().remove("npc_handler");
    }
}
