package com.caved_in.commons.listeners;

import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.game.item.Weapon;
import com.caved_in.commons.player.Players;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDamageEntityListener implements Listener {

	@EventHandler
	public void onPlayerDamageEntity(EntityDamageByEntityEvent e) {
		Entity attacker = e.getDamager();
		Entity attacked = e.getEntity();

		//Assure that we've got a player attacking, and a living entity was attacked.
		if (!(attacker instanceof Player) || !(attacked instanceof LivingEntity)) {
			return;
		}

		LivingEntity entity = (LivingEntity) attacked;
		Player player = (Player) attacker;

		//If the player has nothing in their hands, quit; we require gadgetsS
		if (Players.handIsEmpty(player)) {
			return;
		}

		ItemStack hand = player.getItemInHand();

		//If the item in their hand isn't a gadget then quit; we require gadgets!
		if (!Gadgets.isGadget(hand)) {
			return;
		}

		Gadget gadget = Gadgets.getGadget(hand);

		//In this case, we only need to worry about weapons; if it's not a weapon, quit.
		if (!(gadget instanceof Weapon)) {
			return;
		}

		Weapon weapon = (Weapon) gadget;

		//If the player wielding the weapon isn't able to damage this entity, then quit!
		if (!weapon.canDamage(player, entity)) {
			e.setCancelled(true);
			return;
		}

		//Lastly, attack the mob if all is well!
		weapon.onAttack(player, entity);
	}
}
