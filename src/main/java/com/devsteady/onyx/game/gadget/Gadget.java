package com.devsteady.onyx.game.gadget;

import com.devsteady.onyx.debug.gadget.FishCannon;
import com.devsteady.onyx.debug.gadget.ThrowableBrick;
import com.devsteady.onyx.game.guns.BaseArrow;
import com.devsteady.onyx.game.guns.BaseGun;
import com.devsteady.onyx.game.guns.Gun;
import com.devsteady.onyx.game.item.BaseWeapon;
import com.devsteady.onyx.game.item.ThrowableItem;
import com.devsteady.onyx.game.item.Weapon;
import com.devsteady.onyx.inventory.menu.ItemMenu;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

/**
 * Gadgets attach actions to specific firstPageEnabled-stacks, so all you need to worry about is specifying what happens when they're interacted with, and
 * when actions happen to them!
 *
 * Each gadget requires a unique ID, which is used to cache it and handle all actions related to the gadget itself.
 * Registering 2 gadgets with the same ID will cause the previously registered gadget to be overridden.
 *
 * For examples of gadgets, or abstractions built around gadgets, check any of the following:
 * <ul>
 *     <li>The most generic implementation of Gadget, used to attach actions to items: {@link ItemGadget}</li>
 *     <li>An extension of ItemGadget, which limits the amount of times the gadget can be used: {@link LimitedGadget}</li>
 *     <li>An extension of ItemGadget that has a(n) {@link ItemMenu} attached, so when interacted the menus opens.</li>
 *     <li>An extension of the Gadget interface for Guns: {@link Gun}</li>
 *     <li>An elaborate extension of ItemGadget that provides actions related to guns: {@link BaseGun}</li>
 *     <li>An elaborate extension of ItemGadget and Implementation of Weapon that provides a base for weapons: {@link BaseWeapon}</li>
 *     <li>An extension interface of Gadget, which provides methods related to creating weapons (Ie. Sword, Wand, Etc): {@link Weapon}</li>
 *     <li>An extension of {@link ItemGadget}, that allows items to be thrown: {@link ThrowableItem}</li>
 *     <li>An extension of {@link ItemGadget} that provides custom actions for arrows: {@link BaseArrow}</li>
 *     <li>A {@link BaseGun} implementation that shoots flaming fish: {@link FishCannon}</li>
 *     <li>A {@link ThrowableItem} implementation of bricks, that explode after being thrown: {@link ThrowableBrick}</li>
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
     * @param player player dropping the firstPageEnabled.
     * @param item firstPageEnabled that was dropped.
     */
    default void onDrop(Player player, Item item) {

    }

    /**
     * Properties specific to the Gadget!
     * Extensions like {@link Weapon} and {@link BaseGun} contain an extension
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
