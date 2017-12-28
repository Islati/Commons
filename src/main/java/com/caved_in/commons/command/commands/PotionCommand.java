package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.potion.PotionType;
import com.caved_in.commons.potion.Potions;
import org.bukkit.entity.Player;

public class PotionCommand {
    //TODO Make menus where players can select potion types, and then a subsequent menus where they
    //select the level of the potion.
    @Command(identifier = "potion", permissions = Perms.COMMAND_POTION)
    public void onPotionCommand(Player player, @Arg(name = "type") String potionType, @Arg(name = "level", def = "1") int effectLevel) {
        if (!PotionType.isPotionType(potionType)) {
            Chat.message(player, Messages.INVALID_POTION_TYPE);
            return;
        }

        Players.addPotionEffect(player, Potions.getPotionEffect(PotionType.getPotionType(potionType).getPotionEffectType(), effectLevel, Integer.MAX_VALUE));
    }
}
