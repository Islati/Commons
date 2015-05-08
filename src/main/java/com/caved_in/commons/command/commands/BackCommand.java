package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.location.PreTeleportLocation;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.MinecraftPlayer;
import com.caved_in.commons.player.Players;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BackCommand {

    private Players players = Commons.getInstance().getPlayerHandler();

    public BackCommand() {

    }

    @Command(identifier = "back", onlyPlayers = true, permissions = {Perms.COMMAND_BACK})
    public void onBackCommand(Player player) {
        MinecraftPlayer minecraftPlayer = players.getData(player);
        Location location = minecraftPlayer.getPreTeleportLocation();

        if (location == null) {
            Chat.message(player, Messages.NO_TELEPORT_BACK_LOCATION);
            return;
        }

        PreTeleportLocation preTeleLoc = minecraftPlayer.getPreTeleportLocation();

        if (!preTeleLoc.hasPermission(player)) {
            Chat.message(player, Messages.insufficientPreTeleportPermissions(preTeleLoc));
            return;
        }

        Players.teleport(player, preTeleLoc);
    }
}
