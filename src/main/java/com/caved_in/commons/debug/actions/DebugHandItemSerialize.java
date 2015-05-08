package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.XmlItemStack;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class DebugHandItemSerialize implements DebugAction {

    @Override
    public void doAction(Player player, String... args) {
        if (!Players.hasItemInHand(player)) {
            Chat.message(player, Messages.DEBUG_ACTION_REQUIRES_HAND_ITEM);
            return;
        }

        Serializer serializer = new Persister();
        ItemStack playerHand = player.getItemInHand();
        Items.setName(playerHand, "&bThe Debugger");
        Items.setLore(playerHand, Arrays.asList("&eDebugging le hand item", "&6By adding lines of lore"));
        File itemFile = new File(String.format(Commons.DEBUG_DATA_FOLDER + "%s.xml", String.valueOf(System.currentTimeMillis())));

        if (!itemFile.exists()) {
            try {
                if (itemFile.createNewFile()) {
                    Chat.message(player, "Created item file at " + itemFile.getPath());
                } else {
                    Chat.message(player, "Failed to create item file at " + itemFile.getPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            serializer.write(new XmlItemStack(playerHand), itemFile);
            Chat.message(player, "Saved item to " + itemFile.toString());
        } catch (Exception e) {
            Chat.message(player, "Error saving item to " + itemFile.toString());
            e.printStackTrace();
        }
    }

    @Override
    public String getActionName() {
        return "hand_item_serialize";
    }
}
