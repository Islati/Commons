package com.caved_in.commons.game.gadget;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.guns.BaseGun;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.plugin.Plugins;
import com.caved_in.commons.world.Worlds;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Gadgets {
    //    Used to assign gadgets an ID auto-magically
    private static final AtomicInteger ids = new AtomicInteger(0);

    private static final Random random = new Random();

    //todo Implement methods to get first free int for gadget registration
    private static final Map<Integer, Gadget> gadgets = new LinkedHashMap<>();

    //todo implement int method to return the registered gadgets id
    //todo Move Gadgets class to a manager for each plugin made with commons

    /**
     * Register the given gadget, enabling it's effects to be utilized whenever the associated item is used.
     * Note: Every gadget must have a unique id, if not, the previously registered gadget will be overwritten.
     *
     * @param gadget gadget to register.
     */
    public static void registerGadget(Gadget gadget) {
        gadgets.put(gadget.id(), gadget);
        Plugins.registerListener(Commons.getInstance(), gadget);
    }

    /**
     * Check whether or not the given itemstack is a gadget / has gadget data associated with it.
     *
     * @param item item to check
     * @return true if the item is a gadget, false otherwisse.
     */
    public static boolean isGadget(ItemStack item) {
        return getGadget(item) != null;
    }

    /**
     * Check whether or not a gadget with the given id exists.
     *
     * @param id id of the gadget to check for
     * @return true if a gadget exists with the given id, false otherwise.
     */
    public static boolean isGadget(int id) {
        return gadgets.containsKey(id);
    }

    /**
     * Retrieve a gadget its associated item stack.
     *
     * @param item item to get the gadget container for.
     * @return the gadget associated with the given itemstack, or null if no gadget is associated.
     */
    public static Gadget getGadget(ItemStack item) {
        for (Gadget gadget : gadgets.values()) {
            if (gadget instanceof BaseGun) {

                BaseGun gun = (BaseGun) gadget;
                if (Items.nameContains(item, gun.getItemName())) {
                    return gun;
                }
            } else if (gadget.getItem().isSimilar(item)) {
                return gadget;
            }
        }
        return null;
    }

    /**
     * Retrieve a gadget by its registered id.
     *
     * @param id id of the gadget to get
     * @return gadget registered with the given id, or null if none are registered with the given id.
     */
    public static Gadget getGadget(int id) {
        return gadgets.get(id);
    }

    /**
     * Spawn the gadget at the given location.
     *
     * @param gadget   gadget to drop at the location
     * @param location location to spawn the gadget at
     */
    public static void spawnGadget(Gadget gadget, Location location) {
        Worlds.dropItem(location, gadget.getItem());
    }

    /**
     * Retrieve a random gadget from the list of currently registered gadgets
     *
     * @return random gadget of the registered gadgets
     */
    public static Gadget getRandomGadget() {
        List<Gadget> gadgetList = Lists.newArrayList(gadgets.values());
        return gadgetList.get(random.nextInt(gadgetList.size()));
    }

    /**
     * @return a collection of all the currently registered gadgets
     */
    public static Collection<Gadget> getAllGadgets() {
        return gadgets.values();
    }

    /**
     * @return the amount of registered gadgets
     */
    public static int getGadgetCount() {
        return gadgets.size();
    }

    public static int getFirstFreeId() {
        int gadgetCount = getGadgetCount();
        if (gadgetCount == 0) {
            return 0;
        }

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (!gadgets.containsKey(i)) {
                return i;
            }
        }

        throw new IndexOutOfBoundsException("Unable to obtain a free ID");
    }

    /**
     * Determine whether or not the gadget has been registered already.
     *
     * @param gadget gadget
     * @return
     */
    public static boolean hasBeenRegistered(Gadget gadget) {
        return gadgets.containsKey(gadget.id());
    }

    /**
     * Retrieve the ID of the gadget associated with the itemstack if it's a gadget.
     *
     * @param item itemstack of the gadget to retrieve the ID for.
     * @return -1 if the item isn't a gadget, otherwise the id of the associated gadget.
     */
    public static int getId(ItemStack item) {
        if (!isGadget(item)) {
            return -1;
        }

        for (Gadget gadget : gadgets.values()) {
            if (gadget instanceof BaseGun) {

                BaseGun gun = (BaseGun) gadget;
                if (Items.nameContains(item, gun.getItemName())) {
                    return gun.id();
                }
            } else if (gadget.getItem().isSimilar(item)) {
                return gadget.id();
            }
        }

        return -1;
    }
}
