package com.caved_in.commons.command.commands;

import com.caved_in.commons.block.Blocks;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class GrassCommand {
    @Command(identifier = "grass", permissions = Perms.COMMAND_GRASS)
    public void onGrassCommand(Player player, @Arg(name = "size") int size, @Arg(name = "density", def = "37") int density) {
        Blocks.regrowGrass(Players.getTargetLocation(player), size, density);
        Chat.actionMessage(player, "&aGrass hath been grown!");
    }
}
