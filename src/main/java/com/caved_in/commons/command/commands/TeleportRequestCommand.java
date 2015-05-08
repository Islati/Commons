package com.caved_in.commons.command.commands;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.menu.menus.confirmation.ConfirmationMenu;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;

public class TeleportRequestCommand {
    private static Commons commons = Commons.getInstance();

    @Command(identifier = "tpa", permissions = "commons.tpa")
    public void onTpaCommand(Player player, @Arg(name = "target") Player target) {
        ConfirmationMenu.of(String.format("Teleport %s to you?", player.getName()))
                .onConfirm((menu, p) -> {
                    Players.teleport(player, p);
                    menu.closeMenu(p);
                }).onDeny((menu, p) -> {
            Chat.message(player, String.format("&c%s&e has denied your teleport request", p.getName()));
            menu.closeMenu(p);
        }).exitOnClickOutside(true)
                .onOpen((menu, p) -> {
                    commons.getPlayerHandler().getData(p).setGodMode(true);
                }).onClose((menu, p) -> {
            commons.getPlayerHandler().getData(p).setGodMode(false);
        }).openMenu(target);
    }

    @Command(identifier = "tpahere", permissions = "commons.tpa")
    public void onTpaHereCommand(Player player, @Arg(name = "target") Player target) {
        ConfirmationMenu.of(String.format("Teleport to %s?", player.getName()))
                .onConfirm((menu, p) -> {
                    Players.teleport(p, player);
                    menu.closeMenu(p);
                }).onDeny((menu, p) -> {
            Chat.message(player, String.format("&c%s&e has denied your teleport request", p.getName()));
            menu.closeMenu(p);
        }).exitOnClickOutside(true)
                .onOpen((menu, p) -> {
                    commons.getPlayerHandler().getData(p).setGodMode(true);
                }).onClose((menu, p) -> {
            commons.getPlayerHandler().getData(p).setGodMode(false);
        }).openMenu(target);
    }
}
