package com.caved_in.commons.listeners;

import com.caved_in.commons.game.event.GadgetUseEvent;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteracted(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();

        if (!Gadgets.isGadget(itemInHand)) {
            return;
        }

        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(itemInHand);
        GadgetUseEvent gadgetEvent = new GadgetUseEvent(player, event.getAction(), gadget);
        GadgetUseEvent.handle(gadgetEvent);
    }
}
