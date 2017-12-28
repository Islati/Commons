package com.caved_in.commons.command.commands;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.menus.gadgetmenu.GadgetsMenu;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class GadgetsCommand {

    @Command(identifier = "gadgets", permissions = Perms.COMMAND_GADGETS)
    public void onGadgetsCommand(Player player) {
        try {
            GadgetsMenu.GadgetMenu menu = GadgetsMenu.getMenu(0);
            menu.openMenu(player);
        } catch (IndexOutOfBoundsException e) {
            Chat.message(player, "&cUnable to open the gadgets menus due to an error. Do you have any gadgets registered?");
        }
    }

    @Command(identifier = "gadgets get", permissions = Perms.COMMAND_GADGETS)
    public void onGadgetsGetCommand(Player player, @Arg(name = "id") int id) {
        if (!Gadgets.isGadget(id)) {
            Chat.format(player, "&cGadget &e%s&c does not exist", id);
            return;
        }
        Gadget gadget = Gadgets.getGadget(id);
        Players.giveItem(player,gadget.getItem());
    }
}
