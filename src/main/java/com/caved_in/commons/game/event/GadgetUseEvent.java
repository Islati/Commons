package com.caved_in.commons.game.event;

import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.game.guns.BaseGun;
import com.caved_in.commons.game.item.Weapon;
import com.caved_in.commons.plugin.Plugins;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * An event that's called whenever a(n) {@link Gadget}, or any deriving class, is used / interacted with.
 */
public class GadgetUseEvent extends Event implements Cancellable {
    public static final HandlerList handler = new HandlerList();

    private Action action;

    private boolean cancelled = false;

    private Player player;
    private Gadget gadget;

    public GadgetUseEvent(Player player, Action action, ItemStack item) {
        this.player = player;
        this.gadget = Gadgets.getGadget(item);
        this.action = action;
    }


    public GadgetUseEvent(Player player, Action action, Gadget gadget) {
        this.player = player;
        this.gadget = gadget;
        this.action = action;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handler;
    }

    public static HandlerList getHandlerList() {
        return handler;
    }

    /**
     * @return the player using the gadget.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @return gadget being used by the player.
     */
    public Gadget getGadget() {
        return gadget;
    }

    /**
     * @return action that was performed / used to call the event.
     */
    public Action getAction() {
        return action;
    }

    public static void handle(GadgetUseEvent e) {
        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        Gadget gadget = e.getGadget();

        if (player == null || gadget == null) {
            return;
        }

        //TODO Handle the usage of gadgets that have durability that's not an item based durability.


        //If the gadget's a hand-held weapon, then handle it respectively
        if (gadget instanceof Weapon) {
            Weapon weapon = (Weapon) gadget;

			/* When the player interacts (right or left click) call respective actions. */
            switch (e.getAction()) {
                case RIGHT_CLICK_AIR:
                case RIGHT_CLICK_BLOCK:
                    weapon.onActivate(player);
                    return;
                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
//					weapon.onSwing(player);
                default:
                    break;
            }
        }

        if (gadget instanceof BaseGun) {
            LauncherFireEvent event = new LauncherFireEvent(player, (BaseGun) gadget);
            Plugins.callEvent(event);
            LauncherFireEvent.handle(event);
            return;
        }

        gadget.perform(player);
    }
}
