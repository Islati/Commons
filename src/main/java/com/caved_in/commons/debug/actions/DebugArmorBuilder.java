package com.caved_in.commons.debug.actions;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.debug.DebugAction;
import com.caved_in.commons.inventory.ArmorBuilder;
import com.caved_in.commons.inventory.ArmorInventory;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugArmorBuilder implements DebugAction {
    @Override
    public void doAction(Player player, String... args) {
        ArmorInventory tieredArmor = new ArmorBuilder()
                .withHelmet(Items.makeItem(Material.DIAMOND_HELMET))
                .withBoots(Items.makeItem(Material.IRON_BOOTS))
                .withChest(Items.makeItem(Material.LEATHER_CHESTPLATE))
                .withHand(Items.makeItem(Material.WOOD_SWORD))
                .withLeggings(Items.makeItem(Material.LEATHER_LEGGINGS))
                .toInventory();

        Players.setArmor(player, tieredArmor);

        ItemStack[] armor = player.getInventory().getArmorContents();

        for (int i = 0; i < armor.length; i++) {
            Chat.message(player, String.format("&7[%s]&r: &7%s", i, Items.getName(armor[i])));
        }
    }

    @Override
    public String getActionName() {
        return "armor_builder";
    }
}
