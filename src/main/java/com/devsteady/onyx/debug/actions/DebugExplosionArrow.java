package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.debug.gadget.ProtoExplosionArrow;
import com.devsteady.onyx.game.gadget.Gadgets;
import com.devsteady.onyx.item.Items;
import com.devsteady.onyx.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugExplosionArrow implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        if (!Gadgets.hasBeenRegistered(ProtoExplosionArrow.getInstance())) {
            Gadgets.registerGadget(ProtoExplosionArrow.getInstance());
        }

        ItemStack arrow = ProtoExplosionArrow.getInstance().getItem().clone();
        arrow.setAmount(64);
        Players.giveItem(player, Items.makeItem(Material.BOW), arrow);
    }

    @Override
    public String getActionName() {
        return "explosion_arrow";
    }
}
