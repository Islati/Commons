package com.caved_in.commons.listeners;

import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ItemBreakListener implements Listener {
    @EventHandler
    public void onItemBreakEvent(PlayerItemBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack broken = e.getBrokenItem();

        if (broken == null || !Gadgets.isGadget(broken)) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(broken);
        gadget.onBreak(p);
    }
}
