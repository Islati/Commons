package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.permission.Perms;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Set;

public class SlayCommand {
    @Command(identifier = "slay", permissions = Perms.COMMAND_SLAY)
    @Flags(identifier = {"p"}, description = {"Whether or not to slay the players aswell."})
    public void onSlayCommand(Player player, @FlagArg("p") boolean killPlayers, @Arg(name = "radius") int radius) {
        int amountRemoved = 0;
        Set<LivingEntity> entities = Entities.getLivingEntitiesNearLocation(player.getLocation(), radius);
        for (LivingEntity entity : entities) {
            //If we don't want to kill players, and the entity being looped is a player, then skip this entity
            if (!killPlayers && entity instanceof Player) {
                continue;
            }
            Entities.kill(entity);
            amountRemoved++;
        }
        Chat.message(player, Messages.entityRemovedEntities(amountRemoved));
    }
}
