package com.caved_in.commons.command.commands;

import com.caved_in.commons.chat.Chat;
import com.caved_in.commons.command.*;
import com.caved_in.commons.player.Players;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.entity.Player;

public class TreeCommand {

    @Command(identifier = "tree", permissions = "commons.command.tree")
    @Flags(identifier = {"-cursor"})
    public void onTreeCommand(Player player, @FlagArg("-cursor") boolean cursor, @Wildcard @Arg(name = "tree-type") String treeType) {

        treeType = StringUtils.replace(treeType, "_", " ");

        TreeType type = null;

        switch (treeType.toLowerCase()) {
            case "oak":
            case "tree":
                type = TreeType.TREE;
                break;
            case "big tree":
            case "tree:big":
                type = TreeType.BIG_TREE;
                break;
            case "redwood":
            case "red wood":
                type = TreeType.REDWOOD;
                break;
            case "tall redwood":
            case "redwood:tall":
                type = TreeType.TALL_REDWOOD;
                break;
            case "birch":
                type = TreeType.BIRCH;
                break;
            case "jungle":
                type = TreeType.JUNGLE;
                break;
            case "jungle bush":
            case "jungle:bush":
                type = TreeType.JUNGLE_BUSH;
                break;
            case "small jungle":
            case "jungle:small":
                type = TreeType.SMALL_JUNGLE;
                break;
            case "cocoa tree":
            case "coconut tree":
            case "tree:coconut":
                type = TreeType.COCOA_TREE;
                break;
            case "red mushroom":
            case "mushroom:red":
                type = TreeType.RED_MUSHROOM;
                break;
            case "brown mushroom":
            case "mushroom:brown":
                type = TreeType.BROWN_MUSHROOM;
                break;
            case "swamp":
            case "tree:swamp":
                type = TreeType.SWAMP;
                break;
            case "acacia":
            case "tree:acacia":
                type = TreeType.ACACIA;
                break;
            case "dark oak":
            case "tree:darkoak":
                type = TreeType.DARK_OAK;
                break;
            case "redwood:mega":
            case "mega redwood":
                type = TreeType.MEGA_REDWOOD;
                break;
            case "tall birch":
            case "birch:tall":
                type = TreeType.TALL_BIRCH;
                break;
            default:
                StringBuilder treeTypeBuilder = new StringBuilder();
                for (TreeType tt : TreeType.values()) {
                    treeTypeBuilder.append("&e").append(StringUtils.replace(tt.name().toLowerCase(), "_", " ")).append("&r, ");
                }

                Chat.message(player, String.format("&cThe available tree types are: %s", treeTypeBuilder.toString()));
                break;
        }


        if (type == null) {

            return;
        }

        Location loc = cursor ? Players.getTargetLocation(player) : player.getLocation();

        player.getWorld().generateTree(loc, type);
    }

}
