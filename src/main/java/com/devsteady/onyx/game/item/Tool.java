package com.devsteady.onyx.game.item;

import com.devsteady.onyx.game.gadget.Gadget;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Tools are items that have actions bound to individual interactions with the gadget
 * and various scenarios / objects.
 */
public interface Tool extends Gadget {

    /**
     * The action that's performed when the user simply right clicks.
     *
     * @param holder player using the gadget.
     */
    void perform(Player holder);

    /**
     * Action to perform when the player begins to "mine" a block
     *
     * @param holder the player who's damaging the block.
     * @param block block the player is mining.
     * @return true if the block damage is allowed, false to cancel the block damage.
     */
    boolean onBlockDamage(Player holder, Block block);

    /**
     * Action to perform when the player breaks a block using this gadget.
     *
     * @param holder the player that has broken the block.
     * @param block block that was broken.
     * @return true if the block break is allowed, false to cancel the block breaking.
     */
    boolean onBlockBreak(Player holder, Block block);

    /**
     * Action to perform when the player interacts (right clicks) with a block.
     *
     * @param holder the player that's performing the actions.
     * @param block  block that the player interacted with.
     * @return true to perform the action, false otherwise.
     */
    boolean onInteract(Player holder, Block block);

    /**
     * Action to perform when the player interacts (right clicks) an entity.
     *
     * @param holder player interacting with the entity.
     * @param entity entity that was interacted with.
     * @return
     */
    boolean onInteract(Player holder, Entity entity);

    /**
     * Action to perform when the player swings their tool (left clicks air).
     * @param holder player that's swinging the tool.
     */
    void onSwing(Player holder);
}
