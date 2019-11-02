package com.caved_in.commons.command.commands;

import com.caved_in.commons.Messages;
import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.Arg;
import com.caved_in.commons.command.Command;
import com.caved_in.commons.command.FlagArg;
import com.caved_in.commons.command.Flags;
import com.caved_in.commons.entity.Entities;
import com.caved_in.commons.permission.Perms;
import com.caved_in.commons.world.Worlds;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Set;

public class CleanCommand {

    @Command(identifier = "clean", permissions = Perms.COMMAND_CLEAN)
    public void onCleanCommand(Player player) {
        Chat.message(
                player,
                "&6There's several actions that can be performed",
                "&e- &aCleaning dropped items",
                "&e    *&b /clean items (radius)",
                "&e    *&b /clean items --all"
        );
    }

    //TODO: Implement the /clean fluid sub-command

    @Command(identifier = "clean items", permissions = Perms.COMMAND_CLEAN_ENTITIES)
    @Flags(identifier = "-all")
    public void onCleanItemsCommand(Player p, @Arg(name = "radius", def = "0") int radius, @FlagArg("-all") boolean all) {
        if (!all && radius < 1) {
            Chat.message(p,
                    "&6Proper usage for &e/clean items&6 is:",
                    "&e    *&b /clean items (radius)",
                    "&e    *&b /clean items --all"
            );
            return;
        }

        int cleaned = 0;

        if (all) {
            cleaned = Worlds.clearDroppedItems(p.getWorld());
        } else {
            cleaned = Worlds.clearDroppedItems(p.getLocation(), radius);
        }

        Chat.message(p, String.format("&eCleaned &a%s&e dropped items!", cleaned));
    }

    @Command(identifier = "clean mobs", permissions = Perms.COMMAND_CLEAN_MOBS)
    @Flags(identifier = {"a", "-world"})
    public void onCleanMobCommand(Player p, @Arg(name = "radius", def = "0") int radius, @FlagArg("a") boolean all, @FlagArg("-world") @Arg(name = "world", def = "?sender") World world) {
        boolean sameWorld = p.getWorld().getName().equals(world.getName());

		/*
        If the player used the command with no arguments, then we need to send them the proper usage.
		 */
        if (!all && (sameWorld && radius < 1)) {
            Chat.message(p, "&6Proper usage for &e/clean mobs&6 is:",
                    "&e    *&b /clean mobs (radius)",
                    "&e    *&b /clean mobs --world (world)"
            );
            return;
        }

		/*
		If they're wanting to clear all the entities, then let's do this! :D
		 */

        int slayed = 0;

        if (all) {
            slayed = Worlds.cleanAllEntities(world);
        } else {
            if (radius < 1) {
                Chat.message(p, Messages.invalidCommandUsage("radius"));
                return;
            }

            Set<LivingEntity> entities = Entities.getLivingEntitiesNear(p, radius);

            for (LivingEntity mob : entities) {
                mob.remove();
                slayed++;
            }
        }

        Chat.message(p, String.format("&eRemoved &a%s&e mobs", slayed));
    }
}
