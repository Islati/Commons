package com.devsteady.onyx.debug.gadget;

import com.devsteady.onyx.effect.Effects;
import com.devsteady.onyx.game.gadget.Gadgets;
import com.devsteady.onyx.game.item.ThrowableItem;
import com.devsteady.onyx.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class ThrowableBrick extends ThrowableItem {

    private static ThrowableBrick instance = null;

    public static ThrowableBrick getInstance() {
        if (instance == null) {
            instance = new ThrowableBrick();
            Gadgets.registerGadget(instance);
        }
        return instance;
    }

    public ThrowableBrick() {
        super(ItemBuilder.of(Material.CLAY_BRICK).lore("&eYou'll bash your eye out!"));

        properties().delay(2).action(Action.DELAY);
    }

    @Override
    public void handle(Player holder, Item thrownItem) {
        Effects.explode(thrownItem.getLocation(), 1.0f, false, false);
    }
}
