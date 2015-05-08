package com.caved_in.commons.item;

import com.caved_in.commons.Commons;
import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.config.XmlItemStack;
import com.caved_in.commons.utilities.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The saved item manager is a class used
 * internally by Commons to manage the saving
 * and loading of items, from the
 * '/i save' and '/i load' Command.
 */
public class SavedItemManager {
    private static Serializer serializer = new Persister();

    private static final Map<String, ItemStack> items = new HashMap<>();

    public static Set<String> getItemNames() {
        return items.keySet();
    }

    public static boolean saveItem(String name, ItemStack item) {
        if (items.containsKey(name)) {
            return false;
        }

        XmlItemStack xmlItemStack = XmlItemStack.fromItem(item);

        File itemFile = new File(String.format("%s/%s.xml", Commons.ITEM_DATA_FOLDER, name));

        boolean saved = true;

        try {
            serializer.write(xmlItemStack, itemFile);

            items.put(name, item);
        } catch (Exception e) {
            e.printStackTrace();
            saved = true;
        }

        return saved;
    }

    public static void loadItem(File file) {
        String itemName = FilenameUtils.removeExtension(file.getName());

        XmlItemStack item = null;
        try {
            item = serializer.read(XmlItemStack.class, file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (item == null) {
            return;
        }

        items.put(itemName, item.getItemStack());
        Chat.debug(String.format("Loaded item %s", StringUtil.joinString(Messages.itemInfo(item.getItemStack()), "\n", 0)));
    }

    public static ItemStack getItem(String name) {
        for (Map.Entry<String, ItemStack> itemEntry : items.entrySet()) {
            String entryName = itemEntry.getKey();

            /*
            Check if the two item names aren't equal, and
            if so skip this item.
             */
            if (!name.equalsIgnoreCase(entryName)) {
                continue;
            }

            return itemEntry.getValue();
        }

        return null;
    }


}
