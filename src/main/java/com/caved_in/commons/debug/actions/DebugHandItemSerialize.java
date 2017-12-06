package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.SerializableItemStack;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.yml.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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


        ItemStack playerHand = player.getItemInHand();
        Items.setName(playerHand, "&bThe Debugger");
        Items.setLore(playerHand, Arrays.asList("&eDebugging le hand item", "&6By adding lines of lore"));
        File itemFile = new File(String.format(Commons.DEBUG_DATA_FOLDER + "%s.yml", String.valueOf(System.currentTimeMillis())));

        if (!itemFile.exists()) {
            try {
                if (itemFile.createNewFile()) {
                    debug("Created item file at " + itemFile.getPath());
                } else {
                    debug("Failed to create item file at " + itemFile.getPath());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        SerializableItemStack serialItem = new SerializableItemStack(playerHand);
        try {
            serialItem.save(itemFile);
            debug("Item saved to file successfully!");
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            debug("Failed to save file. Check console for error log");
        }
    }

    @Override
    public String getActionName() {
        return "hand_item_serialize";
    }
}
