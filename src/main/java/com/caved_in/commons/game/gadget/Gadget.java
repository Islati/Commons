package com.caved_in.commons.game.gadget;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Gadgets attach actions to specific item-stacks, so all you need to worry about is specifying what happens when they're interacted with, and
 * when actions happen to them!
 *
 * Each gadget requires a unique ID, which is used to cache it and handle all actions related to the gadget itself.
 * Registering 2 gadgets with the same ID will cause the previously registered gadget to be overridden.
 *
 * For examples of gadgets, or abstractions built around gadgets, check any of the following:
 * <ul>
 *     <li>The most generic implementation of Gadget, used to attach actions to items: {@link ItemGadget}</li>
 *     <li>An extension of ItemGadget, which limits the amount of times the gadget can be used: {@link LimitedGadget}</li>
 *     <li>An extension of ItemGadget that has a(n) {@link com.caved_in.commons.menu.ItemMenu} attached, so when interacted the menu opens.</li>
 *     <li>An extension of the Gadget interface for Guns: {@link com.caved_in.commons.game.guns.Gun}</li>
 *     <li>An elaborate extension of ItemGadget that provides actions related to guns: {@link com.caved_in.commons.game.guns.BaseGun}</li>
 *     <li>An elaborate extension of ItemGadget and Implementation of Weapon that provides a base for weapons: {@link com.caved_in.commons.game.item.BaseWeapon}</li>
 *     <li>An extension interface of Gadget, which provides methods related to creating weapons (Ie. Sword, Wand, Etc): {@link com.caved_in.commons.game.item.Weapon}</li>
 *     <li>An implementation of the above Weapon Interface, using {@link com.caved_in.commons.game.item.BaseWeapon} for a base, which will kill enderman with one swift hit: {@link com.caved_in.commons.debug.gadget.FlamingEnderSword}</li>
 *     <li>An extension of {@link ItemGadget}, that allows items to be thrown: {@link com.caved_in.commons.game.item.ThrowableItem}</li>
 *     <li>An extension of {@link ItemGadget} that provides custom actions for arrows: {@link com.caved_in.commons.game.guns.BaseArrow}</li>
 *     <li>A {@link com.caved_in.commons.game.guns.BaseGun} implementation that shoots flaming fish: {@link com.caved_in.commons.debug.gadget.FishCannon}</li>
 *     <li>A {@link com.caved_in.commons.game.item.ThrowableItem} implementation of bricks, that explode after being thrown: {@link com.caved_in.commons.debug.gadget.ThrowableBrick}</li>
 * </ul>
 *
 * The above shows the absolute magnitude of this abstraction & attaching of actions. Take a stab and see what you can make!
 */
public interface Gadget extends Listener {

    /**
     * @return The itemstack that represents this gadget
     */
    ItemStack getItem();

    /**
     * Will take place whenever the performs an interaction with the gadget.
     *
     * @param holder player using the gadget.
     */
    void perform(Player holder);

    /**
     * Operations to perform whenever the gadget breaks for the player.
     * @param p player using the gadget
     */
    default void onBreak(Player p) {

    }

    /**
     * Actions to perform whenever the player drops the gadget.
     * @param player player dropping the item.
     * @param item item that was dropped.
     */
    default void onDrop(Player player, Item item) {

    }

    /**
     * Properties specific to the Gadget!
     * Extensions like {@link com.caved_in.commons.game.item.Weapon} and {@link com.caved_in.commons.game.guns.BaseGun} contain an extension
     * of the {@link GadgetProperties} class, each containing data specific to the respective implementations.
     * @param <T> Properties for the gadget.
     * @return properties specific to the gadget / implementation.
     */
    <T extends GadgetProperties> T properties();

    /**
     * Unique identifier for the gadget.
     * <b>Each gadget must have a unique ID, otherwise the previously registered gadget will be overridden.</b>
     * @return Unique identifier for the gadget.
     */
    int id();
}
