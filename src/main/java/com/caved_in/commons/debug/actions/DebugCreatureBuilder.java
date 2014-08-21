package com.caved_in.commons.debug.actions;

import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.entity.CreatureBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
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
				.withWeapon(Items.makeItem(Material.WOOD_SWORD))
				.parent()
				.spawn(player.getLocation());

		LivingEntity skeleton = CreatureBuilder.of(EntityType.SKELETON)
				.wither()
				.armor()
				.withWeapon(Items.makeItem(Material.DIAMOND_SWORD))
				.parent()
				.spawn(player.getLocation());

		Players.sendMessage(player,
				"&eSpawned an adult zombie with an iron helmet, gold boots, and a wooden sword",
				"Spawned a wither skeleton with a diamond sword"
		);
	}

	@Override
	public String getActionName() {
		return "creature_builder";
	}
}
