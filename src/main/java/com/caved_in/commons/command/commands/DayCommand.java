package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.world.Worlds;
import org.bukkit.World;
import org.bukkit.entity.Player;


public class DayCommand {
    @Command(identifier = "day", permissions = Perms.COMMAND_TIME)
    public void onDayCommand(Player player) {
        World world = player.getWorld();
        Worlds.setTimeDay(world);
        Chat.message(player, Messages.timeUpdated(world.getName(), "day"));
    }
}
