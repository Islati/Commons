package com.devsteady.onyx.debug.actions;

import com.devsteady.onyx.chat.Chat;
import com.devsteady.onyx.debug.DebugAction;
import com.devsteady.onyx.inventory.ArmorBuilder;
import com.devsteady.onyx.inventory.ArmorInventory;
import com.devsteady.onyx.item.Items;
import com.devsteady.onyx.player.Players;
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
