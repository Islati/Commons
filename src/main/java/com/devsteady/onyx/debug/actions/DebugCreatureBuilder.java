package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.entity.CreatureBuilder;
import com.devsteady.onyx.item.Items;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DebugCreatureBuilder implements DebugAction {

    @Override
    public void doAction(Player player, String... args) {
        LivingEntity zombie = CreatureBuilder.of(EntityType.ZOMBIE)
                .armor()
                .withHelmet(Items.makeItem(Material.IRON_HELMET))
                .withBoots(Items.makeItem(Material.GOLD_BOOTS))
                .withHand(Items.makeItem(Material.WOOD_SWORD))
                .parent()
                .spawn(player.getLocation());

        LivingEntity skeleton = CreatureBuilder.of(EntityType.SKELETON)
                .wither()
                .armor()
                .withHand(Items.makeItem(Material.DIAMOND_SWORD))
                .parent()
                .spawn(player.getLocation());

        Chat.message(player,
                "&eSpawned an adult zombie with an iron helmet, gold boots, and a wooden sword",
                "Spawned a wither skeleton with a diamond sword"
        );
    }

    @Override
    public String getActionName() {
        return "creature_builder";
    }
}
