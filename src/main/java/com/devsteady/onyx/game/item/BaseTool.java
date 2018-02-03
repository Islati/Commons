package com.devsteady.onyx.game.item;

import com.devsteady.onyx.game.gadget.ItemGadget;
import com.devsteady.onyx.item.ItemBuilder;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Base abstract class for creating a {@link Tool} with bindable actions for nearly every in-game interaction.
 *
 * Offers the most customization of an items behaviour.
 */
public abstract class BaseTool extends ItemGadget implements Tool {
    public BaseTool(ItemStack item) {
        super(item);
    }

    public BaseTool(ItemBuilder builder) {
        super(builder);
    }

    @Override
    public abstract void perform(Player holder);

    @Override
    public abstract void onSwing(Player holder);

    @Override
    public abstract boolean onInteract(Player holder, Block block);

    @Override
    public abstract boolean onInteract(Player holder, Entity entity);

    @Override
    public abstract boolean onBlockDamage(Player holder, Block block);

    @Override
    public abstract boolean onBlockBreak(Player player, Block block);
}
