package com.caved_in.commons.books;

import com.caved_in.commons.inventory.HandSlot;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * The event called when a book is opened trough this Util
 */
public class CustomBookOpenEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    @Getter
    @Setter
    private boolean cancelled;

    /**
     * The player
     */
    @Getter
    private final Player player;

    /**
     * The hand used to open the book (the previous item will be restored after the opening)
     */
    @Getter
    @Setter
    private HandSlot hand;

    /**
     * The actual book to be opened
     */
    @Getter
    @Setter
    private ItemStack book;

    public CustomBookOpenEvent(Player player, ItemStack book, boolean offHand) {
        this.player = player;
        this.book = book;
        this.hand = offHand ? HandSlot.OFF_HAND : HandSlot.MAIN_HAND;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
