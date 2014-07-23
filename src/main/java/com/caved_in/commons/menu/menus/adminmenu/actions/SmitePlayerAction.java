package com.caved_in.commons.menu.menus.adminmenu.actions;

import com.caved_in.commons.Messages;
import com.caved_in.commons.effect.Effects;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class SmitePlayerAction implements AdminAction {
	@Override
	public void perform(Player admin, Player target) {
		Effects.strikeLightning(target.getLocation(), false);

		Players.sendMessage(target, "&6&oTHOU HATH BEEN SMITTEN!");
		//Message all players except the target player
		Players.messageAllExcept(Messages.playerSmited(target, admin), target);
	}
}
