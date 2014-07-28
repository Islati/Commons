package com.caved_in.commons.plugin.game.event;

import com.caved_in.commons.plugin.game.gadget.Gadget;
import com.caved_in.commons.plugin.game.gadget.Gadgets;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class GadgetUseEvent extends Event implements Cancellable {
	public static final HandlerList handler = new HandlerList();

	private boolean cancelled = false;

	private Player player;
	private Gadget gadget;

	public GadgetUseEvent(Player player, ItemStack item) {
		this.player = player;
		this.gadget = Gadgets.getGadget(item);
	}


	public GadgetUseEvent(Player player, Gadget gadget) {
		this.player = player;
		this.gadget = gadget;
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

	public Player getPlayer() {
		return player;
	}

	public Gadget getGadget() {
		return gadget;
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

		gadget.perform(player);
	}
}
