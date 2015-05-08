package com.caved_in.commons.listeners;

import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDamageListener implements Listener {

    @EventHandler
    public void itemDamageEvent(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if (!Gadgets.isGadget(item)) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(item);
        if (!gadget.properties().isBreakable()) {
            e.setCancelled(true);
        }

        //TODO Implement pseudo durability codes based on gadget properties
    }
}
