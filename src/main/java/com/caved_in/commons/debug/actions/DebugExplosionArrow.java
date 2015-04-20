package com.caved_in.commons.debug.actions;

import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.debug.gadget.ProtoExplosionArrow;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugExplosionArrow implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        if (!Gadgets.isGadget(13996)) {
            Gadgets.registerGadget(ProtoExplosionArrow.getInstance());
        }

        ItemStack arrow = ProtoExplosionArrow.getInstance().getItem();
        arrow.setAmount(64);
        Players.giveItem(player, Items.makeItem(Material.BOW), arrow);
    }

    @Override
    public String getActionName() {
        return "explosion_arrow";
    }
}
