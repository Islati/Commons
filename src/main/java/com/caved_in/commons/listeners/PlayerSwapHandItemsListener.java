package com.caved_in.commons.listeners;


import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.inventory.HandSlot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerSwapHandItemsListener implements Listener {
    @EventHandler
    public void onPlayerSwapHandItem(PlayerSwapHandItemsEvent e) {
        if (e.isCancelled()) {
            return;
        }

        ItemStack offHandItem = e.getOffHandItem(); //Item Switched to the offHand
        Player player = e.getPlayer();


        /*
        Check if the item going to the offHand is able to be put into that slot.
         */
        if (Gadgets.isGadget(offHandItem)) {
            Gadget gadgetGoingOffhand = Gadgets.getGadget(offHandItem);
            if (!gadgetGoingOffhand.properties().isOffhandEquippable()) {
                Chat.actionMessage(player, Messages.gadgetEquipError(gadgetGoingOffhand, HandSlot.OFF_HAND));
                e.setCancelled(true);
            }
        }
    }
}
