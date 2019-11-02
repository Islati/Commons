package com.caved_in.commons.debug.actions;

import com.caved_in.commons.Commons;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.entity.CreatureBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.time.TimeHandler;
import com.caved_in.commons.time.TimeType;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DebugCreatureBuilder implements DebugAction {

    @Override
    public void doAction(Player player, String... args) {
        LivingEntity zombie = CreatureBuilder.of(EntityType.ZOMBIE)
                .armor()
                .withHelmet(Items.makeItem(Material.IRON_HELMET))
                .withBoots(Items.makeItem(Material.GOLDEN_BOOTS))
                .withMainHand(Items.makeItem(Material.WOODEN_SWORD))
                .parent()
                .spawn(player.getLocation());

        LivingEntity skeleton = CreatureBuilder.of(EntityType.SKELETON)
                .armor()
                .withMainHand(Items.makeItem(Material.DIAMOND_SWORD))
                .parent()
                .spawn(player.getLocation());

        Chat.message(player,
                "&eSpawned an adult zombie with an iron helmet, gold boots, and a wooden sword",
                "Spawned a wither skeleton with a diamond sword"
        );

        Commons.getInstance().getThreadManager().runTaskLater(new BukkitRunnable() {
            @Override
            public void run() {
                skeleton.remove();
                zombie.remove();
            }
        }, TimeHandler.getTimeInTicks(3, TimeType.SECOND));
    }

    @Override
    public String getActionName() {
        return "creature_builder";
    }
}
