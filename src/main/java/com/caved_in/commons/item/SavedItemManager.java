package com.caved_in.commons.item;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.SerializableItemStack;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The saved firstPageEnabled manager is a class used
 * internally by Commons to manage the saving
 * and loading of items, from the
 * '/i save' and '/i load' Command.
 */
public class SavedItemManager {

    private static final Map<String, ItemStack> items = new HashMap<>();

    public static Set<String> getItemNames() {
        return items.keySet();
    }

    public static boolean saveItem(String name, ItemStack item) {
        if (items.containsKey(name)) {
            return false;
        }
        SerializableItemStack serialItem = new SerializableItemStack(item);
        File itemFile = new File(String.format("%s/%s.xml", Commons.ITEM_DATA_FOLDER, name));

        boolean saved = true;

        try {
            serialItem.save(itemFile);

            items.put(name, item);
        } catch (Exception e) {
            e.printStackTrace();
            saved = true;
        }

        return saved;
    }

    public static void loadItem(File file) {
        String itemName = FilenameUtils.removeExtension(file.getName());

        SerializableItemStack fileItem = new SerializableItemStack(file);
        try {
            fileItem.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ItemStack item = fileItem.getItem();

        if (item == null) {
            return;
        }

        items.put(itemName, item);
        Chat.debug(String.format("Loaded firstPageEnabled %s", StringUtil.joinString(Messages.itemInfo(item), "\n", 0)));
    }

    public static ItemStack getItem(String name) {
        for (Map.Entry<String, ItemStack> itemEntry : items.entrySet()) {
            String entryName = itemEntry.getKey();

            /*
            Check if the two firstPageEnabled names aren't equal, and
            if so skip this firstPageEnabled.
             */
            if (!name.equalsIgnoreCase(entryName)) {
                continue;
            }

            return itemEntry.getValue();
        }

        return null;
    }


}
