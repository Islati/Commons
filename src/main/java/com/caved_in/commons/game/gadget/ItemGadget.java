package com.caved_in.commons.game.gadget;

import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A minimalistic implementation of the Gadget interface. Automatically handles hooks inside Commons.
 * Extending this class is the basis of creating a gadget with custom actions.
 * IMPORTANT: INSTANCING OF THIS CLASS MUST BE IN A SINGLETON, OTHERWISE ISSUES WILL ARISE WITH HOW GADGETS ARE REGISTERED AND HANDLED.
 */
public abstract class ItemGadget implements Gadget {

    private ItemStack gadgetItem;

    private GadgetProperties properties = new GadgetProperties();

    private int id = 0;

    public ItemGadget() {
        id = Gadgets.getFirstFreeId();
    }

    public ItemGadget(ItemBuilder builder) {
        this();
        ItemStack item = builder.item();
        setItem(item);
    }

    public ItemGadget(ItemStack item) {
        this();
        setItem(item);
    }

    public ItemStack getItem() {
        return gadgetItem;
    }

    /**
     * Change the item attached to this gadget.
     *
     * @param item item to use for gadget recognition.
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
