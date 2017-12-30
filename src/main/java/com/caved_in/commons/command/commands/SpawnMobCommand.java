package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.entity.CreatureBuilder;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.player.Players;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnMobCommand {
    //todo implement options for max-health / health, name.
    @Command(identifier = "spawnmob", permissions = Perms.COMMAND_MOB_SPAWN)
    @Flags(identifier = {"baby", "age", "size", "villager", "powered", "wither", "helmet", "chest", "legs", "boots", "hand"})
    public void onSpawnMobCommand(Player player, @Arg(name = "mob type") EntityType type, @Arg(name = "amount", def = "1") int amount,
                                  @FlagArg("baby") boolean baby,
                                  @FlagArg("age") @Arg(name = "age", def = "0") int age,
                                  @FlagArg("size") @Arg(name = "size", def = "0") int size,
                                  @FlagArg("villager") boolean villager,
                                  @FlagArg("powered") boolean powered,
                                  @FlagArg("wither") boolean wither,
                                  @FlagArg("helmet") @Arg(name = "helmet", def = "0") ItemStack helmet,
                                  @FlagArg("chest") @Arg(name = "chest", def = "0") ItemStack chest,
                                  @FlagArg("legs") @Arg(name = "legs", def = "0") ItemStack legs,
                                  @FlagArg("boots") @Arg(name = "boots", def = "0") ItemStack boots,
                                  @FlagArg("hand") @Arg(name = "hand", def = "0") ItemStack mainHand) {
        if (type == null) {
            return;
        }

        if (type == EntityType.ENDER_DRAGON && !Players.hasPermission(player, Perms.COMMAND_MOB_SPAWN_ENDERDRAGON)) {
            Chat.message(player, Messages.permissionRequired(Perms.COMMAND_MOB_SPAWN_ENDERDRAGON));
            return;
        }

        CreatureBuilder spawner = CreatureBuilder.of(type)
                .asBaby(baby)
                .age(age)
                .size(size)
                .asVillager(villager);

        if (powered) {
            spawner.powered();
        }

        if (wither) {
            spawner.wither();
        }

        if (Items.isArmor(helmet)) {
            spawner.armor().withHelmet(helmet);
        }

        if (Items.isArmor(chest)) {
            spawner.armor().withChest(chest);
        }

        if (Items.isArmor(legs)) {
            spawner.armor().withLeggings(legs);
        }

        if (Items.isArmor(boots)) {
            spawner.armor().withBoots(boots);
        }

        if (mainHand != null && mainHand.getType() != Material.AIR) {
            spawner.armor().withHand(mainHand);
        }

        spawner.spawn(Players.getTargetLocation(player), amount);
    }
}
