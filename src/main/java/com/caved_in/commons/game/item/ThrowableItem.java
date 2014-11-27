package com.caved_in.commons.game.item;

import com.caved_in.commons.Commons;
import com.caved_in.commons.game.gadget.ItemGadget;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ThrowableItem extends ItemGadget {

    private int delay;

    private int force = 1;

    private boolean canPickup = false;

    public ThrowableItem(ItemStack item) {
        super(item);
    }

    public ThrowableItem(ItemStack item, int delay) {
        super(item);
        this.delay = delay;
    }

    @Override
    public void perform(Player holder) {
        //Remove an item from the players hand, taking it out of their total amount for the throwable item.
        Players.removeFromHand(holder, 1);

        Location eyeLoc = holder.getEyeLocation();

        Item thrownItem = Worlds.dropItem(eyeLoc, getItem());

        //If the item's not meant to be picked up, then assure it
        //wont be picked up
        if (!canPickup()) {
            thrownItem.setPickupDelay(Integer.MAX_VALUE);
        }

        thrownItem.setVelocity(eyeLoc.getDirection().multiply(force));

        //After the delay is up, we want to handle the item!
        Commons.getInstance().getThreadManager().runTaskLater(() -> {
            //Call the handle, for any implementations to do as they wish!
            handle(holder, thrownItem);

            //Remove the item after the handle is called!
            thrownItem.remove();
        }, TimeHandler.getTimeInTicks(delay, TimeType.SECOND));
    }

    public abstract void handle(Player holder, Item thrownItem);

    public abstract int id();

    public void setDelay(int secondsDelay) {
        this.delay = secondsDelay;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getDelay() {
        return delay;
    }

    public int getForce() {
        return force;
    }

    public boolean canPickup() {
        return canPickup;
    }

    public void setCanPickup(boolean canPickup) {
        this.canPickup = canPickup;
    }
}
