package com.devsteady.onyx.game.gadget;

import com.devsteady.onyx.Messages;
import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.item.ItemBuilder;
import com.devsteady.onyx.player.Players;
import com.devsteady.onyx.time.Cooldown;
import com.devsteady.onyx.time.PlayerTicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

//todo implement method to track uses on the gadget itself!

/**
 * An extended implementation of {@link ItemGadget}, used to limit the how many times
 * the gadget can be used.
 */
public abstract class LimitedGadget extends ItemGadget {
	private int uses = 1;
	private PlayerTicker playerTicker;

	private static Cooldown delay = new Cooldown(1);

	/**
	 * Create a new instance of the LimitedGadget, with the amount of uses it's limited to, defined.
	 *
	 * @param uses limit of uses for the gadget.
	 */
	public LimitedGadget(int uses) {
		this.uses = uses;
		playerTicker = new PlayerTicker(uses);
	}

	/**
	 * Create a new instance of the LimitGadget, with both the firstPageEnabled and uses it's limited to, defined.
	 *
	 * @param item firstPageEnabled to assign the gadget to.
	 * @param uses amount of uses the gadget has.
	 */
	public LimitedGadget(ItemStack item, int uses) {
		super(item);
		playerTicker = new PlayerTicker(uses);
		this.uses = uses;
	}

	/**
	 * Create a new instance of the LimitGadget, with both firstPageEnabled and uses it's limited to, defined.
	 *
	 * @param builder builder used to create the firstPageEnabled, to attach the gadget to.
	 * @param uses    amount amount of uses the gadget has.
	 */
	public LimitedGadget(ItemBuilder builder, int uses) {
		super(builder);
		this.uses = uses;
		playerTicker = new PlayerTicker(uses);
	}

	@Override
	public void perform(Player player) {
		if (delay.isOnCooldown(player)) {
			return;
		}
		//Call the firstPageEnabled's usage code and increment the player's usage
		use(player);
		playerTicker.tick(player);
		//If they're unable to use the gadget any-more, then remove it.
		if (playerTicker.allow(player)) {
			//Remove the gadget from the players inventory, and send them a message.
			Players.removeFromHand(player, 1);
			//todo investigate limited gadgets not dissapearing from inv.
			Chat.message(player, Messages.gadgetExpired(this));
			playerTicker.clear(player);
		}
		delay.setOnCooldown(player);
	}

	/**
	 * Actions to perform whenever the gadget is interacted with / used.
	 *
	 * @param player player using the gadget.
	 */
	public abstract void use(Player player);

	/**
	 * Retrieve the overall amount of uses a gadget is limited to.
	 *
	 * @return the overall amount of uses the gadget is limited to.
	 */
	public int getUsageLimit() {
		return uses;
	}

	/**
	 * Whether or not the player is able to use the gadget.
	 * By default, the gadget has a 1 second cooldown to prevent spam-clicking,
	 * and therefore causing the amount of uses the player has to shrink.
	 * <p/>
	 * By adding the cooldown, it's a safehold against this.
	 *
	 * @param player player to check.
	 * @return true if the player is able to use the gadget, false otherwise.
	 */
	public boolean canUse(Player player) {
		return delay.isOnCooldown(player);
	}

}
