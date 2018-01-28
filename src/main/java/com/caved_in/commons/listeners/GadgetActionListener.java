package com.caved_in.commons.listeners;

import com.caved_in.commons.game.event.LauncherFireEvent;
import com.caved_in.commons.game.gadget.Gadget;
import com.caved_in.commons.game.gadget.Gadgets;
import com.caved_in.commons.game.guns.BaseGun;
import com.caved_in.commons.game.item.Tool;
import com.caved_in.commons.game.item.Weapon;
import com.caved_in.commons.player.Players;
import com.caved_in.commons.plugin.Plugins;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

/**
 * Listens to various events where item gadgets (tools, weapons, etc) are hooked in order
 * to process various functionality of the gadget.
 */
public class GadgetActionListener implements Listener {

    public GadgetActionListener() {

    }

    @EventHandler
    public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
        /*
        Handles the damaging of an entity by a player using gadgets. Check first if it was a projectile,
        just to assure we get our source, then it checks if the source is a player and if they have a gadget-
        Granted all these checks pass then we let the weapon instance handle it's goods, otherwise we back out!
         */

        Entity attacker = e.getDamager();
        Entity attacked = e.getEntity();

        Player player = null;

        /*
        Check if there was an arrow shot, and the shooter was a player, then we assign
        the player (damaging) to the shooter.
         */
        if (e.getEntityType() == EntityType.ARROW) {
            Arrow arrow = (Arrow) attacker;
            ProjectileSource source = arrow.getShooter();

            if (source == null) {
                return;
            }

            if (!(source instanceof LivingEntity)) {
                return;
            }

            LivingEntity shooter = (LivingEntity) source;
            if (shooter instanceof Player) {
                player = (Player) shooter;
            }
        }

        /*
        If the damaging entity was a snowball then we're going to get the player whom
        shot the snowball and assign them as the attacking player, rightfully so!
         */
        if (e.getEntityType() == EntityType.SNOWBALL) {
            Snowball snowball = (Snowball) attacker;
            ProjectileSource shooter = snowball.getShooter();

            if (shooter == null) {
                return;
            }

            if (!(shooter instanceof Player)) {
                return;
            }

            player = (Player) shooter;
        }

        if (attacker instanceof Player) {
            player = (Player) attacker;
        }

        if (player == null) {
            return;
        }

        //If the player has nothing in their hands, quit; we require gadgetsS
        if (!Players.hasItemInHand(player)) {
            return;
        }

        ItemStack hand = player.getItemInHand();

        //If the firstPageEnabled in their hand isn't a gadget then quit; we require gadgets!
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
        if (!weapon.canDamage(player, (LivingEntity) attacked)) {
            e.setCancelled(true);
            return;
        }

        //Lastly, attack the mob if all is well!
        weapon.onAttack(player, (LivingEntity) attacked);
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent e) {
        /*
        Handles when an item is broken.
         */

        Player p = e.getPlayer();
        ItemStack broken = e.getBrokenItem();

        if (broken == null || !Gadgets.isGadget(broken)) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(broken);
        gadget.onBreak(p);
    }


    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        /*
        Handles the check for "breakable" gadgets and unbreakable ones.
         */

        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        if (!Gadgets.isGadget(item)) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(item);
        if (!gadget.properties().isBreakable()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent e) {
        /*
        Handles the hooks for when a player interacts with an entity.
         */
        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();

        ItemStack item = player.getItemInHand();

        if (item == null) {
            return;
        }

        if (!Gadgets.isGadget(item)) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(item);

        if (gadget instanceof Tool) {
            Tool tool = (Tool) gadget;

            boolean allowed = tool.onInteract(player, entity);
            if (!allowed) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        /*
        Handles the hooks for tools and when a block is damaged.
         */

        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        ItemStack itemInHand = e.getItemInHand();

        if (!Gadgets.isGadget(itemInHand)) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(itemInHand);

        if (gadget instanceof Tool) {
            Tool tool = (Tool) gadget;

            boolean allowed = tool.onBlockDamage(player, e.getBlock());

            if (!allowed) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteractEvent(PlayerInteractEvent e) {
        /*
        Generic handle for interactions. If there's no specific interaction (interacts with entity), then this listener
        will be called.
         */
        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        ItemStack itemInHand = e.getItem();

        if (!Gadgets.isGadget(e.getItem())) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(itemInHand);

        /* If our gadget is a tool then we'll handle it as expected */
        if (gadget instanceof Tool) {
            Tool tool = (Tool) gadget;

            switch (e.getAction()) {
                case RIGHT_CLICK_AIR:
                    tool.perform(player);
                    return;
                case RIGHT_CLICK_BLOCK:
                    tool.onInteract(player, e.getClickedBlock());
                    return;
                case LEFT_CLICK_AIR:
                    tool.onSwing(player);
                    return;
                default:
                    return;
            }
        }

        /*
        If our gadget is a weapon then we'll handle that as expected.
         */
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

        /*
        If our gadget is a gun then we have to do some fancy stuff like creating a launcherFireEvent and such,
        but we'll still go and handle that too.
         */
        if (gadget instanceof BaseGun) {
            LauncherFireEvent event = new LauncherFireEvent(player, (BaseGun) gadget);
            Plugins.callEvent(event);
            LauncherFireEvent.handle(event);
            return;
        }

        /* Lastly if there's nothing else to check (specifically) we'll simply perform. */
        gadget.perform(player);
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        /*
        This handle checks for when a block is broken, and if the player is using a Tool / gadget to do so.

        Great for binding actions to do neat stuff!
         */


        if (e.isCancelled()) {
            return;
        }

        Player player = e.getPlayer();
        if (!Players.hasGadgetInHand(player)) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(player.getItemInHand());

		/*
		If the player has a tool in their hand, let's call the block break handler for them!
		 */
        if (gadget instanceof Tool) {
            Tool tool = (Tool) gadget;

            boolean allowed = tool.onBlockBreak(player, e.getBlock());

            if (!allowed) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        /*
        Handles the actions that are performed when the player attempts
        to drop an item gadget.

        The onDrop(*) method is called either way, though here we determine whether or not the item is dropped.
         */

        //todo check if player is in creative and has option for creative drops off, then don't drop items- Just remove them when they're dropped
        ItemStack item = event.getItemDrop().getItemStack();

        //If we're not dealing with gadgets
        if (!Gadgets.isGadget(item)) {
            return;
        }

        Gadget gadget = Gadgets.getGadget(item);

        // Not a gadget! We don't need to do anything.
        if (gadget == null) {
            return;
        }

        if (!gadget.properties().isDroppable()) {
            event.setCancelled(true);
            gadget.onDrop(event.getPlayer(), null);
            return;
        }

        gadget.onDrop(event.getPlayer(), event.getItemDrop());
    }

}
