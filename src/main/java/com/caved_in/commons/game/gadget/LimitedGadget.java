package com.caved_in.commons.game.gadget;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.time.Cooldown;
import com.caved_in.commons.time.PlayerTicker;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

//todo implement method to track uses on the gadget itself!
public abstract class LimitedGadget extends ItemGadget {
    private int uses = 1;
    private PlayerTicker playerTicker;

    private static Cooldown delay = new Cooldown(1);

    public LimitedGadget(int uses) {
        this.uses = uses;
        playerTicker = new PlayerTicker(uses);
    }

    public LimitedGadget(ItemStack item, int uses) {
        super(item);
        playerTicker = new PlayerTicker(uses);
        this.uses = uses;
    }

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
        //Call the item's usage code and increment the player's usage
        use(player);
        playerTicker.tick(player);
        //If they're unable to use the gadget any-more, then remove it.
        if (playerTicker.allow(player)) {
            //Remove the gadget from the players inventory, and send them a message.
            Players.removeFromHand(player, 1);
            Chat.message(player, Messages.gadgetExpired(this));
            playerTicker.clear(player);
        }
        delay.setOnCooldown(player);
    }

    public abstract void use(Player player);

    public abstract int id();

    public int getUsageLimit() {
        return uses;
    }

    public boolean canUse(Player player) {
        return delay.isOnCooldown(player);
    }

}
