package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.warp.Warps;
import org.bukkit.entity.Player;

public class WarpCommand {
    @Command(identifier = "warp", permissions = Perms.COMMAND_WARP)
    public void onWarpCommand(Player player, @Wildcard @Arg(name = "warp") String warpName) {
        if (!Warps.isWarp(warpName)) {
            Chat.message(player, Messages.invalidWarp(warpName));
            return;
        }

        Warp warp = Warps.getWarp(warpName);
        Players.teleport(player, warp);
        Chat.message(player, Messages.playerWarpedTo(warpName));
    }
}
