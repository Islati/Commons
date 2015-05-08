package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.MinecraftPlayer;
import org.bukkit.entity.Player;

public class GodCommand {
    @Command(identifier = "god", permissions = Perms.COMMAND_GOD_MODE, description = "Become the almighty!!")
    public void onGodCommand(Player player) {
        MinecraftPlayer user = Commons.getInstance().getPlayerHandler().getData(player);
        boolean god = user.hasGodMode();
        user.setGodMode(!god);

        if (user.hasGodMode()) {
            Chat.message(player, Messages.GOD_MODE_ENABLED);
        } else {
            Chat.message(player, Messages.GOD_MODE_DISABLED);
        }
    }
}
