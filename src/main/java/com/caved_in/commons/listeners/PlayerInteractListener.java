package com.caved_in.commons.listeners;

import com.caved_in.commons.game.event.GadgetUseEvent;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.inventory.HandSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteracted(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();
        EquipmentSlot eSlot = event.getHand();

        if (itemInHand == null) {
            return;
        }

        if (!Gadgets.isGadget(itemInHand)) {
            return;
        }

        if (eSlot == null) {
            return;
        }

        HandSlot hand = HandSlot.getSlot(eSlot);

        if (hand == null) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(itemInHand);
        GadgetUseEvent gadgetEvent = new GadgetUseEvent(player, event.getAction(), gadget, hand);
        GadgetUseEvent.handle(gadgetEvent);
    }
}
