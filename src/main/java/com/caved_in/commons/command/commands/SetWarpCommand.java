package com.caved_in.commons.command.commands;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.Wildcard;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.warp.Warp;
import com.caved_in.commons.warp.Warps;
import org.bukkit.entity.Player;

import static com.caved_in.commons.Messages.duplicateWarp;
import static com.caved_in.commons.Messages.warpCreated;

public class SetWarpCommand {
    @Command(identifier = "setwarp", permissions = Perms.COMMAND_SETWARP)
    public void onSetWarpCommand(Player player, @Wildcard @Arg(name = "warp name") String warpName) {
        if (Warps.isWarp(warpName)) {
            Chat.message(player, duplicateWarp(warpName));
            return;
        }

        Warps.addWarp(new Warp(warpName, player.getLocation()), true);
        Chat.message(player, warpCreated(warpName));
    }
}
