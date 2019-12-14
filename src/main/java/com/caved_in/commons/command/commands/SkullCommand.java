package com.caved_in.commons.command.commands;

import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.item.SkullCreator;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.sound.Sounds;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.codehaus.plexus.util.Base64;

public class SkullCommand {
    @Command(identifier = "skull", permissions = Perms.COMMAND_SKULL)
    public void getPlayerSkullCommand(Player player, @Arg(name = "value") String value) {
        ItemStack newSkull = null;

        if (Base64.isArrayByteBase64(value.getBytes())) {
            newSkull = SkullCreator.itemFromBase64(value);
        } else {
            newSkull = Items.getSkull(value);
        }
        player.getInventory().addItem(newSkull);
        Sounds.playSound(player, Sound.BLOCK_NOTE_BLOCK_HARP);
    }
}
