package com.caved_in.commons.debug.gadget;

import com.caved_in.commons.effect.Effects;
import com.caved_in.commons.game.item.ThrowableItem;
import com.caved_in.commons.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

public class ThrowableBrick extends ThrowableItem {
    private int id = 0;

    public ThrowableBrick(int id) {
        super(ItemBuilder.of(Material.CLAY_BRICK).lore("&eYou'll bash your eye out!"));
        this.id = id;

        properties().delay(2).action(Action.DELAY);
    }

    @Override
    public void handle(Player holder, Item thrownItem) {
        Effects.explode(thrownItem.getLocation(), 1.0f, false, false);
    }

    @Override
    public int id() {
        return id;
    }
}
