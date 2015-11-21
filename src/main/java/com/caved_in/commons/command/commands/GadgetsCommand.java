package com.caved_in.commons.command.commands;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.menu.menus.gadgetmenu.GadgetsMenu;
import org.bukkit.entity.Player;

public class GadgetsCommand {

    @Command(identifier = "gadgets",permissions = "commons.admin")
    public void onGadgetsCommand(Player player) {
        try {
            GadgetsMenu.GadgetMenu menu = GadgetsMenu.getMenu(0);
            menu.openMenu(player);
        } catch (IndexOutOfBoundsException e) {
            Chat.message(player, "&cUnable to open the gadgets menu due to an error.");
        }
    }

    @Command(identifier = "gadgets get", permissions = "commons.admin")
    public void onGadgetsGetCommand(Player player, @Arg(name = "id") int id) {
        if (!Gadgets.isGadget(id)) {
            Chat.format(player, "&cGadget &e%s&c does not exist", id);
            return;
        }

        Gadget gadget = Gadgets.getGadget(id);

    }
}
