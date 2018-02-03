package com.devsteady.onyx.game.item;

import com.devsteady.onyx.Onyx;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.game.gadget.GadgetProperties;
import com.devsteady.onyx.game.gadget.ItemGadget;
import com.devsteady.onyx.item.ItemBuilder;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.time.TimeHandler;
import com.devsteady.onyx.time.TimeType;
import com.devsteady.onyx.world.Worlds;
import com.devsteady.onyx.yml.Comment;
import com.devsteady.onyx.yml.Path;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public abstract class ThrowableItem extends ItemGadget {

    private Properties properties = new Properties();

    public ThrowableItem(ItemStack item) {
        super(item);
    }

    public ThrowableItem(ItemStack item, int delay) {
        super(item);
        properties().delay(delay);
    }

    public ThrowableItem(ItemBuilder builder) {
        super(builder);
    }

    public ThrowableItem(ItemBuilder builder, int delay) {
        super(builder);
        properties().delay(delay);
    }

    @Override
    public void perform(Player holder) {
        ItemStack gadgetItem = getItem();
//
//        /*
//        With Dual-Wielding available we need to check the hand slot of where the player has the firstPageEnabled.
//         */
//        if (Players.hasItemInHand(holder, gadgetItem, HandSlot.MAIN_HAND)) {
//            Players.removeFromHand(holder, 1, HandSlot.MAIN_HAND);
//        } else {
//            Players.removeFromHand(holder, 1, HandSlot.OFF_HAND);
//        }
        
        //Remove an item from the players hand, taking it out of their total amount for the throwable firstPageEnabled.
        if (properties().takeItem()) {
            Players.removeFromHand(holder, 1);
        }

        Location eyeLoc = holder.getEyeLocation();

        final Item thrownItem = Worlds.dropItem(eyeLoc, gadgetItem);

        //If the firstPageEnabled's not meant to be picked up, then assure it
        //wont be picked up
        if (!properties().canPickup()) {
            thrownItem.setPickupDelay(Integer.MAX_VALUE);
        }

        thrownItem.setVelocity(eyeLoc.getDirection().multiply(properties().force()));

        Action action = properties().action();
        switch (action) {
            case DELAY:
                //After the delay is up, we want to handle the firstPageEnabled!
                Onyx.getInstance().getThreadManager().runTaskLater(() -> {
                    //Call the handle, for any implementations to do as they wish!
                    handle(holder, thrownItem);

                    if (properties().action() == Action.CANCEL) {
                        Chat.actionMessage(holder, properties().cancelMessage());
                        properties().action(Action.DELAY);
                        return;
                    }

                    //Remove the item after the handle is called!
                    if (properties().removeItem()) {
                        thrownItem.remove();
                    }
                }, TimeHandler.getTimeInTicks(properties().delay(), properties().delayType()));
                break;
            case REPEAT_TICK:
                long reTicks;
                if (properties().isTicks()) {
                    reTicks = properties().delay();
                } else {
                    reTicks = TimeHandler.getTimeInTicks(properties().delay(), properties().delayType());
                }

                Onyx.getInstance().getThreadManager().registerSyncRepeatTask("Gadget[" + thrownItem.getUniqueId().toString() + "-TICK]", new BukkitRunnable() {
                    @Override
                    public void run() {
                        //Handle the thrown item just as specified
                        handle(holder, thrownItem);

                        //Though if the item is no longer available, then cancel the task!!
                        //This means that the item must be removed within the handle method, to cancel this task.
                        if (!thrownItem.isValid()) {
                            cancel();
                        }
                    }
                }, reTicks, reTicks);
                break;
            case EXECUTE:
                long exTicks = properties().isTicks() ? properties().delay() : 50l;
                //Execute the task 2.5 seconds later, as it gives the item time to travel!
                Onyx.getInstance().getThreadManager().runTaskLater(() -> {
                    handle(holder, thrownItem);

                    if (properties().action() == Action.CANCEL) {
                        Chat.actionMessage(holder, properties().cancelMessage());
                        properties().action(Action.EXECUTE);
                    }

                    if (properties().removeItem()) {
                        if (thrownItem.isValid()) {
                            thrownItem.remove();
                        }
                    }
                }, exTicks);
                break;
            case CANCEL:
                Chat.actionMessage(holder, properties().cancelMessage());
                break;

        }

    }

    public abstract void handle(Player holder, Item thrownItem);

    @Override
    public Properties properties() {
        return properties;
    }

    public enum Action {
        DELAY,
        REPEAT_TICK,
        EXECUTE,
        CANCEL
    }

    public class Properties extends GadgetProperties {
        @Path("force")
        private double force;

        @Path("delay")
        private int delay = 40;

        @Path("delay-in-ticks")
        private boolean ticks = false;

        @Path("time-type")
        private String timeTypeString = TimeType.SECOND.name();

        @Path("pickupable")
        private boolean pickupable = false;

        @Path("remove-firstPageEnabled")
        private boolean removeItem = true;

        @Path("take-firstPageEnabled")
        @Comment("Whether or not the item is taken once thrown (on interact / right click)")
        private boolean takeItem = true;

        @Path("action")
        @Comment("What action to perform after the item has been thrown")
        private String action = Action.EXECUTE.name();

        @Path("cancel-message")
        private String cancelMessage = "";


        public Properties(File file) {
            super(file);
        }

        public Properties() {
            super();
        }


        public int delay() {
            return delay;
        }

        public Properties delay(int delay) {
            this.delay = delay;
            return this;
        }

        public Properties delayType(TimeType type) {
            this.timeTypeString = type.name();
            return this;
        }

        public Properties useTicks(boolean val) {
            this.ticks = val;
            return this;
        }

        public Properties removeItem(boolean val) {
            this.removeItem = val;
            return this;
        }

        public TimeType delayType() {
            return TimeType.valueOf(timeTypeString);
        }

        public double force() {
            return force;
        }

        public Properties force(double force) {
            this.force = force;
            return this;
        }

        public boolean canPickup() {
            return pickupable;
        }

        public Properties canPickup(boolean value) {
            this.pickupable = value;
            return this;
        }

        public Properties action(Action action) {
            this.action = action.name();
            return this;
        }

        public Properties cancel(String message) {
            this.cancelMessage = message;
            this.action = Action.CANCEL.name();
            return this;
        }

        public Properties cancelMessage(String message) {
            this.cancelMessage = message;
            return this;
        }

        public Action action() {
            return Action.valueOf(action);
        }

        public String cancelMessage() {
            return cancelMessage;
        }

        public boolean removeItem() {
            return removeItem;
        }

        public boolean isTicks() {
            return ticks;
        }

        public boolean takeItem() {
            return takeItem;
        }

        public Properties takeItem(boolean value) {
            this.takeItem = value;
            return this;
        }
    }

}
