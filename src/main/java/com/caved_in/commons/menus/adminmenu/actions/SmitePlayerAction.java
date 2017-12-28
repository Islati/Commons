package com.caved_in.commons.menus.adminmenu.actions;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.effect.Effects;
import org.bukkit.entity.Player;

public class SmitePlayerAction implements AdminAction {
    @Override
    public void perform(Player admin, Player target) {
        Effects.strikeLightning(target.getLocation(), false);

        Chat.message(target, "&6&oTHOU HATH BEEN SMITTEN!");
        //Message all players except the target player
        Chat.messageAllExcept(Messages.playerSmited(target, admin), target);
    }
}
