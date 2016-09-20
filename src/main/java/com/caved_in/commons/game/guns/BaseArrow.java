package com.caved_in.commons.game.guns;

import com.caved_in.commons.Commons;
import com.caved_in.commons.game.gadget.ItemGadget;
import com.caved_in.commons.inventory.ArmorSlot;
import com.caved_in.commons.inventory.HandSlot;
import com.caved_in.commons.inventory.Inventories;
import com.caved_in.commons.item.ItemBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class BaseArrow extends ItemGadget {
    private static Commons commons = null;

    private static Set<UUID> infinityIds = new HashSet<>();

    //todo find a way to equip mobs with custom gear and apply effects to it (hda)

    public BaseArrow(ItemBuilder builder) {
        super(builder);

        if (commons == null) {
            commons = Commons.getInstance();
        }
    }

    public BaseArrow(ItemStack item) {
        super(item);

        if (commons == null) {
            commons = Commons.getInstance();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerFireBow(EntityShootBowEvent e) {
        LivingEntity entity = e.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        ItemStack bow = e.getBow();

        if (Items.hasEnchantment(bow, Enchantment.ARROW_INFINITE)) {
            infinityIds.add(entity.getUniqueId());
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        Entity entityDamager = e.getDamager();
        Entity entityDamaged = e.getEntity();

        if (e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
            return;
        }

        if (!(entityDamaged instanceof LivingEntity)) {
            return;
        }

        if (!(entityDamager instanceof Arrow)) {
            return;
        }

        Arrow shotArrow = (Arrow) entityDamager;
        ProjectileSource arrowShooter = shotArrow.getShooter();

        if (!(arrowShooter instanceof Player)) {
            return;
        }

        Player player = (Player) arrowShooter;
        //todo implement get-first-arrow-method

        int arrowSlot = Inventories.getFirst(player.getInventory(), Material.ARROW);

        if (arrowSlot == -1) {
            return;
        }

        ItemStack arrowStack = Players.getItem(player, arrowSlot);

        if (arrowStack == null) {
            return;
        }

        if (!arrowStack.isSimilar(getItem())) {
            return;
        }

        if (!onDamage((LivingEntity) entityDamaged, player)) {
            e.setCancelled(true);
            return;
        }


        if (infinityIds.contains(player.getUniqueId())) {
            arrowStack = Items.removeFromStack(arrowStack, 1);
            Players.setItem(player, arrowSlot, arrowStack);
            player.updateInventory();
            infinityIds.remove(player.getUniqueId());
        }
    }

    //todo implement method to assign the custom arrows to the players selected arrow.
    //todo implement the optional selection arrow system.

    public abstract boolean onDamage(LivingEntity entity, Player shooter);

    @Override
    public void perform(Player player) {
        ItemStack arrow = getItem();
        if (!Players.hasItemInHand(player,arrow, HandSlot.OFF_HAND)) {
            Players.setArmor(player, ArmorSlot.OFF_HAND,getItem());
            //todo implement potential sound effects / etc.
        } else {
            Players.setItemInHand(player,null,HandSlot.OFF_HAND);
            Players.giveItem(player,arrow);
            //todo check if unequip is right.
        }

    }
}
