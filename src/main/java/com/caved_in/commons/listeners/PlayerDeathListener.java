package com.caved_in.commons.listeners;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.location.PreTeleportType;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    private static Players players;

    public PlayerDeathListener() {
        players = Commons.getInstance().getPlayerHandler();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();

        MinecraftPlayer minecrafter = players.getData(player);

        minecrafter.setPreTeleportLocation(player.getLocation(), PreTeleportType.DEATH);

        if (minecrafter.hasForceRespawn()) {
            try {
                Players.forceRespawn(player);
                Chat.message(player,"&7Forced respawn!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
