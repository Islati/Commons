package com.caved_in.commons.game.item;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.GadgetProperties;
import com.caved_in.commons.game.gadget.ItemGadget;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import com.caved_in.commons.world.Worlds;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.simpleframework.xml.Element;

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
        //Remove an item from the players hand, taking it out of their total amount for the throwable item.
        Players.removeFromHand(holder, 1);

        Location eyeLoc = holder.getEyeLocation();

        final Item thrownItem = Worlds.dropItem(eyeLoc, getItem());

        //If the item's not meant to be picked up, then assure it
        //wont be picked up
        if (!properties().canPickup()) {
            thrownItem.setPickupDelay(Integer.MAX_VALUE);
        }

        thrownItem.setVelocity(eyeLoc.getDirection().multiply(properties().force()));

        Action action = properties().action();
        switch (action) {
            case DELAY:
                //After the delay is up, we want to handle the item!
                Commons.getInstance().getThreadManager().runTaskLater(() -> {
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

                Commons.getInstance().getThreadManager().registerSyncRepeatTask("Gadget[" + thrownItem.getUniqueId().toString() + "-TICK]", new BukkitRunnable() {
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
                Commons.getInstance().getThreadManager().runTaskLater(() -> {
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
        private int delay = 40;

        private int force;

        private boolean pickupable = false;

        private boolean ticks = false;

        private boolean removeItem = true;

        private Action action = Action.EXECUTE;

        private String cancelMessage = "";

        private TimeType timeType = TimeType.SECOND;

        public Properties() {
            super();
        }

        public Properties(@Element(name = "durability") int durability, @Element(name = "breakable") boolean isBreakable, @Element(name = "droppable") boolean isDroppable) {
            super(durability, isBreakable, isDroppable);
        }

        public int delay() {
            return delay;
        }

        public Properties delay(int delay) {
            this.delay = delay;
            return this;
        }

        public Properties delayType(TimeType type) {
            this.timeType = type;
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
            return timeType;
        }

        public int force() {
            return force;
        }

        public Properties force(int force) {
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
            this.action = action;
            return this;
        }

        public Properties cancel(String message) {
            this.cancelMessage = message;
            this.action = Action.CANCEL;
            return this;
        }

        public Properties cancelMessage(String message) {
            this.cancelMessage = message;
            return this;
        }

        public Action action() {
            return action;
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
    }

}
