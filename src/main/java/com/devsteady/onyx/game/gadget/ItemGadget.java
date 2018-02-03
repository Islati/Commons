package com.devsteady.onyx.game.gadget;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.item.ItemBuilder;
import com.devsteady.onyx.item.Items;
import com.devsteady.onyx.player.Players;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A minimalistic implementation of the Gadget interface. Automatically handles hooks inside Onyx.
 * Extending this class is the basis of creating a gadget with custom actions.
 * IMPORTANT: INSTANCING OF THIS CLASS MUST BE IN A SINGLETON, OTHERWISE ISSUES WILL ARISE WITH HOW GADGETS ARE REGISTERED AND HANDLED.
 */
public abstract class ItemGadget implements Gadget {

    private ItemStack gadgetItem;

    private GadgetProperties properties = new GadgetProperties();

    private int id = 0;

    public ItemGadget() {
        id = Gadgets.getFirstFreeId();
        Chat.debug("Registered Gadget [NO ITEM] under ID " + id);
    }

    public ItemGadget(ItemBuilder builder) {
        ItemStack item = builder.item();
        setItem(item);

        id = Gadgets.getFirstFreeId();
        Chat.debug("Registered Gadget " + Items.getName(getItem()) + " with  ID " + id);

    }

    public ItemGadget(ItemStack item) {
        id = Gadgets.getFirstFreeId();
        setItem(item);
        Chat.debug("Registered Gadget " + Items.getName(getItem()) + " with  ID " + id);

    }

    public ItemStack getItem() {
        return gadgetItem;
    }

    /**
     * Change the firstPageEnabled attached to this gadget.
     *
     * @param item firstPageEnabled to use for gadget recognition.
     */
    public void setItem(ItemStack item) {
        this.gadgetItem = item.clone();
        properties.durability(item);
    }

    /**
     * Give the player a copy of the gadget.
     *
     * @param player player to give the gadget to.
     */
    public void giveTo(Player player) {
        Players.giveItem(player, getItem());
    }

    public abstract void perform(Player holder);

    public void onDrop(Player player, Item dropped) {

    }

    @Override
    public GadgetProperties properties() {
        return properties;
    }

    @Override
    public int id() {
        return id;
    }

}
