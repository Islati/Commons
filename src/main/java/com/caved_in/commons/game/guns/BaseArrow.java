package com.caved_in.commons.game.guns;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.game.gadget.ItemGadget;
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

//    private Map<UUID, ItemStack> firedArrows = new HashMap<>();

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
            Chat.debug(((Player) entity).getName() + " has infinty on their bow");
        }

//        firedArrows.put(entity.getUniqueId(),)

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        Entity entityDamager = e.getDamager();
        Entity entityDamaged = e.getEntity();

        if (e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE) {
//            commons.debug("Not a custom arrow; Not a projectile!");
            return;
        }

        if (!(entityDamaged instanceof LivingEntity)) {
//            commons.debug("Entity Damaged isn't a living entity");
            return;
        }

        if (!(entityDamager instanceof Arrow)) {
//            commons.debug("Nota custom arrow; Not instance of arrow");
            return;
        }

        Arrow shotArrow = (Arrow) entityDamager;
        ProjectileSource arrowShooter = shotArrow.getShooter();

        if (!(arrowShooter instanceof Player)) {
//            commons.debug("Not shot from a player");
            return;
        }

        Player player = (Player) arrowShooter;
        //todo implement get-first-arrow-method

        int arrowSlot = Inventories.getFirst(player.getInventory(), Material.ARROW);
//        commons.debug("First arrow slot = " + arrowSlot);

        if (arrowSlot == -1) {
//            commons.debug("Player " + player.getName() + " doesn't have any '" + Items.getName(getItem()) + "'");
            return;
        }

        ItemStack arrowStack = Players.getItem(player, arrowSlot);

        if (arrowStack == null) {
            return;
        }

        if (!arrowStack.isSimilar(getItem())) {
//            Chat.debug("Player shot " + Items.getName(arrowStack) + ", not " + Items.getName(getItem()));
            return;
        }

        if (!onDamage((LivingEntity) entityDamaged, player)) {
            e.setCancelled(true);
//            commons.debug("Was unable to attack entity!");
            return;
        }

//        commons.debug(player.getName() + " shot a(n) [" + entityDamaged.getType() + "] with their '" + Items.getName(arrowStack) + "'");

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
    public void perform(Player holder) {
//        MinecraftPlayer mcPlayer = commons.getPlayerHandler().getData(holder);
//
//        if (mcPlayer.hasArrowEquipped()) {
//            ItemStack arrow = mcPlayer.getEquippedArrow();
//            mcPlayer.unequipArrow();
//            Chat.message(holder, String.format("&eYou've unequipped your '&c%s&e'", Items.getName(arrow)));
//        }
//
//        mcPlayer.equipArrow(getItem());
//        Chat.actionMessage(holder,String.format("&aYou've equipped your '&e%s&a'",Items.getName(getItem())));
        return;
    }
}
