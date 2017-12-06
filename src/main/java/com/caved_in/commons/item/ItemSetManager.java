package com.caved_in.commons.item;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.yml.Path;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Internally used to manage item sets for Commons /set command.
 */
public class ItemSetManager {

    private Map<String, ItemSet> itemSets = new HashMap<>();

    public ItemSetManager() {

    }

    /**
     * Add the item set to this item set manager.
     *
     * @param set set to add to the manager.
     */
    public void addSet(ItemSet set) {
        itemSets.put(set.getName().toLowerCase(), set);
        save(set);
    }

    /**
     * Create and add the itemset based on the name given, and the inventory object that was passed.
     *
     * @param name name to give the set.
     * @param inv  inventory to save the contents to a set for.
     */
    public void addSet(String name, Inventory inv) {
        ItemSet set = new ItemSet(name, inv);

        itemSets.put(name.toLowerCase(), set);
        save(set);
    }

    /**
     * Check whether or not a set exists with the given name.
     *
     * @param name name of the set to check for.
     * @return true if an itemset with the given name is being held by this manager.
     */
    public boolean setExists(String name) {
        return itemSets.containsKey(name.toLowerCase());
    }

    /**
     * Get an itemset via its name.
     *
     * @param name name of the itemset.
     * @return itemset with the given name; null if it doesn't exist.
     */
    public ItemSet getSet(String name) {
        return itemSets.get(name.toLowerCase());
    }

    public Set<String> getSetNames() {
        return itemSets.keySet();
    }

    private void save(ItemSet set) {
        String fileName = String.format("%s%s.xml", Commons.ITEM_SET_DATA_FOLDER, set.getName());
        File itemSetFile = new File(fileName);

        Serializer serializer = new Persister();

        try {
            serializer.write(set, itemSetFile);
            Chat.debug("Saved item-set %s to file");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ItemSet {
        @Path("name")
        private String setName;

        @Path("inventory")
        private Inventory inventory;

        public ItemSet(String name, Inventory inv) {
            this.setName = name;
            this.inventory = inv;
        }

        public Map<Integer, ItemStack> getInventoryContents() {
            return Inventories.getContentsAsMap(this.inventory);
        }

        public String getName() {
            return setName;
        }
    }

}
