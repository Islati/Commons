package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.player.Players;
import org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class DebugItemDeserialize implements DebugAction {

    @Override
    public void doAction(Player player, String... args) {
        Serializer serializer = new Persister();
        File debugFolder = new File(Commons.DEBUG_DATA_FOLDER);
        if (!debugFolder.exists()) {
            debugFolder.mkdirs();
        }

        Set<ItemStack> deserializedItems = new HashSet<>();
        for (File file : FileUtils.listFiles(debugFolder, null, false)) {
            try {
                if (serializer.validate(SerializableItemStack.class, file)) {
                    SerializableItemStack xmlItem = serializer.read(SerializableItemStack.class, file);
                    ItemStack deserializedItem = xmlItem.getItemStack();
                    deserializedItems.add(deserializedItem);
                    Chat.message(player, Messages.itemInfo(deserializedItem));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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
