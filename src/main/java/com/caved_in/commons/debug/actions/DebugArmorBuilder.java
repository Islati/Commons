package com.caved_in.commons.debug.actions;

import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.inventory.ArmorBuilder;
import com.caved_in.commons.inventory.ArmorInventory;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class DebugArmorBuilder implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        ArmorInventory tieredArmor = new ArmorBuilder()
                .withHelmet(Items.makeItem(Material.DIAMOND_HELMET))
                .withBoots(Items.makeItem(Material.IRON_BOOTS))
                .toInventory();

        Players.setArmor(player, tieredArmor);
    }

    @Override
    public String getActionName() {
        return "armor_builder";
    }
}
