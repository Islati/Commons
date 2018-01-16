package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.*;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.SavedItemManager;
import com.caved_in.commons.chat.menu.HelpScreen;
import com.caved_in.commons.chat.menu.ItemFormat;
import com.caved_in.commons.inventory.menu.Menus;
import com.caved_in.commons.chat.menu.PageDisplay;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemCommand {
    private HelpScreen itemHelpScreen = Menus.generateHelpScreen("Item Command - Command Usage", PageDisplay.DEFAULT, ItemFormat.SINGLE_DASH, ChatColor.GOLD, ChatColor.YELLOW)
            .addEntry("/i <firstPageEnabled> [amount]", "Create and receive item from name, id, name:data, or id:data values")
            .addEntry("/i save <file name>", "Save the item in your hand to file!")
            .addEntry("/i list", "List all the items saved to file")
            .addEntry("/i load <file name>", "Load the item from file, into your inventory!")
            .addEntry("/i rename <item name>", "Rename the item in your hand!")
            .addEntry("/i lore", "Compact help menus for lore commands, same as below", Perms.COMMAND_ITEM_LORE)
            .addEntry("/i lore add <lore with spaces>", "Add lore to the item in your hand")
            .addEntry("/i lore set <line> <lore with spaces>", "Set the lore on your hand item at a 0-based index on the firstPageEnabled. (first line = 0, second = 1, etc)")
            .addEntry("/i lore clear", "Remove all the lore from the item in your hand");

    @Command(identifier = "i ?", permissions = Perms.COMMAND_ITEM)
    public void onItemHelpCommand(Player p, @Arg(name = "page", def = "1") int page) {
        itemHelpScreen.sendTo(p, page, 5);
    }

    @Command(identifier = "i", permissions = {Perms.COMMAND_ITEM})
    public void onItemCommand(Player player, @Arg(name = "firstPageEnabled") ItemStack item, @Arg(name = "amount", def = "1") int amount) {
        item.setAmount(amount);
        Players.giveItem(player, item);

        Chat.message(player, Messages.playerItemsGiven(Items.getFormattedMaterialName(item.getType()), amount));
    }

    @Command(identifier = "i save", permissions = {Perms.COMMAND_ITEM})
    public void onItemSaveCommand(Player player, @Wildcard @Arg(name = "file name") String name) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name.trim())) {
            Chat.message(player, "The items name must not be empty!");
            return;
        }

        if (Players.handIsEmpty(player)) {
            Chat.message(player, Messages.ITEM_IN_EITHER_HAND_REQUIRED);
            return;
        }

        ItemStack hand = null;

        if (!Players.hasItemInHand(player)) {
            Chat.message(player,Messages.ITEM_IN_HAND_REQUIRED);
            return;
        }

        hand = player.getItemInHand();

        boolean saved = SavedItemManager.saveItem(name, hand);

        if (saved) {
            Chat.message(player, String.format("&e%s&a has been saved!", name));
        } else {
            Chat.message(player, String.format("&cFailed to save &e%s", name));
        }
    }

    @Command(identifier = "i load", permissions = {Perms.COMMAND_ITEM})
    public void onItemLoadCommand(Player player, @Wildcard @Arg(name = "file name") String name) {
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(name.trim())) {
            Chat.message(player, "The items name must not be empty!");
            return;
        }

        ItemStack item = SavedItemManager.getItem(name);

        if (item == null) {
            Chat.message(player, String.format("&eThe item &c%s&e doesn't exist.", name));
            return;
        }

        Players.giveItem(player, item);
        Chat.message(player, "&aEnjoy!");
    }

    @Command(identifier = "i list", permissions = {Perms.COMMAND_ITEM})
    public void onItemListCommand(Player player) {
        for (String item : SavedItemManager.getItemNames()) {
            Chat.message(player, item);
        }
    }

    @Command(identifier = "i rename", permissions = Perms.COMMAND_ITEM)
    public void onRenameCommand(Player player, @Wildcard @Arg(name = "name") String itemName) {

        if (!Players.hasItemInHand(player)) {
            Chat.message(player, Messages.ITEM_IN_HAND_REQUIRED);
            return;
        }

        ItemStack handItem = player.getItemInHand();
        Items.setName(handItem , itemName);
        Chat.message(player, String.format("&aItem Re-Named to %s", itemName));
    }

    private HelpScreen itemLoreHelpScreen = Menus.generateHelpScreen("Item - Lore Editing Commands", PageDisplay.DEFAULT, ItemFormat.SINGLE_DASH, ChatColor.YELLOW, ChatColor.GOLD)
            .addEntry("/i lore add <lore with spaces>", "Add lore to the item in your hand")
            .addEntry("/i lore set <line> <lore with spaces>", "Set the lore on your hand item at a 0-based index on the firstPageEnabled. (first line = 0, second = 1, etc)")
            .addEntry("/i lore clear", "Remove all the lore from the item in your hand");

    @Command(identifier = "i lore", permissions = Perms.COMMAND_ITEM)
    public void onLoreCommand(Player p) {
        itemLoreHelpScreen.sendTo(p, 1, 3);
    }

    @Command(identifier = "i lore add", permissions = Perms.COMMAND_ITEM)
    public void onLoreAddCommand(Player player, @Wildcard @Arg(name = "lore", description = "Lore to add to the given firstPageEnabled") String lore) {
        if (Players.handIsEmpty(player)) {
            Chat.message(player,"&c&lTo perform &e&l/i lore&c&l you require an item in your hand.");
            return;
        }

        ItemStack handItem = player.getItemInHand();

        Items.addLore(handItem, lore);
        Chat.message(player, "&a&lNew lore has been added to your firstPageEnabled!");
    }

    @Command(identifier = "i lore clear", permissions = Perms.COMMAND_ITEM)
    public void onLoreClearCommand(Player player) {
        if (Players.handIsEmpty(player)) {
            Chat.message(player, "&c&lTo perform &e&l/i lore&c&l you require an item in your %s-hand.");
            return;
        }

        ItemStack handItem = player.getItemInHand();

        Items.clearLore(handItem);

        Chat.message(player, "&e&lYour items lore has been cleared!");
    }

    @Command(identifier = "i lore set", permissions = Perms.COMMAND_ITEM)
    public void onLoreSetLineCommand(Player player, @Arg(name = "line") int line, @Wildcard @Arg(name = "lore", description = "lore to set at the given line") String lore) {

        if (Players.handIsEmpty(player)) {
            Chat.message(player, "&c&lTo perform &e&l/i lore&c&l you require an item in your %s-hand.");
            return;
        }


        ItemStack handItem = player.getItemInHand();

        if (!Items.hasLoreAtLine(handItem, line)) {
            Chat.message(player, "&c&lUnable to edit lore at line &e&l" + line + "&6&l:&c&l Only " + Items.getLore(handItem).size() + " lines available");
            return;
            }

        Items.setLore(handItem, line, lore);
        Chat.message(player, "&a&lYour items lore has been edited");
    }
}
