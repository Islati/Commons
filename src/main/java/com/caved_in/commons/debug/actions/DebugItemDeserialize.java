package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.SerializableItemStack;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.yml.InvalidConfigurationException;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DebugItemDeserialize implements DebugAction {

    @Override
    public void doAction(Player player, String... args) {
        File debugFolder = new File(Commons.DEBUG_DATA_FOLDER);

        if (!debugFolder.exists()) {
            debugFolder.mkdirs();
        }

        Set<ItemStack> deserializedItems = new HashSet<>();

        for (File file : FileUtils.listFiles(debugFolder, null, false)) {

            SerializableItemStack item = new SerializableItemStack(file);

            try {
                item.load();
                debug(String.format("Item '%s' loaded", Items.getName(item.getItem())));
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
                debug(String.format("Error loading item from file: '%s'", file.getName()));
                continue;
            }

            deserializedItems.add(item.getItem());
            Chat.message(player,Messages.itemInfo(item.getItem()));
        }

        if (deserializedItems.size() > 0) {
            for (ItemStack item : deserializedItems) {
                Players.giveItem(player, item, true);
            }
        }
    }

    @Override
    public String getActionName() {
        return "item_deserialize";
    }
}
