package com.caved_in.commons.config;

import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Minecraft colors attached with permissions; Used to color the name-tags and titles of players (internally)
 */
public enum ColorCode {
    BLACK("commons.color.black", ChatColor.BLACK),
    DARK_BLUE("commons.color.darkblue", ChatColor.DARK_BLUE),
    BLUE("commons.color.blue", ChatColor.BLUE),
    GREEN("commons.color.green", ChatColor.GREEN),
    DARK_GREEN("commons.color.darkgreen", ChatColor.DARK_GREEN),
    GOLD("commons.color.gold", ChatColor.GOLD),
    GRAY("commons.color.gray", ChatColor.GRAY),
    DARK_GRAY("commons.color.darkgray", ChatColor.DARK_GRAY),
    RED("commons.color.red", ChatColor.RED),
    DARK_RED("commons.color.darkred", ChatColor.DARK_RED),
    LIGHT_PURPLE("commons.color.lightpurple", ChatColor.LIGHT_PURPLE),
    DARK_PURPLE("commons.color.darkpurple", ChatColor.DARK_PURPLE),
    YELLOW("commons.color.yellow", ChatColor.YELLOW),
    WHITE("commons.color.white", ChatColor.WHITE);

    private String permission;
    private ChatColor color;

    private static Map<String, ChatColor> permissionColors = new HashMap<>();
    private static Map<String, ChatColor> namedColors = new HashMap<>();

    static {
        for (ColorCode colorCode : EnumSet.allOf(ColorCode.class)) {
            permissionColors.put(colorCode.permission, colorCode.color);
            namedColors.put(colorCode.name(), colorCode.getColor());
        }
    }

    ColorCode(String permission, ChatColor color) {
        this.color = color;
        this.permission = permission;
    }

    public String getPermission() {
        return this.permission;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public static ChatColor getColorForPermission(String permission) {
        return permissionColors.get(permission.toLowerCase());
    }

    public static ChatColor getColorByName(String name) {
        return namedColors.get(name);
    }

    public static boolean isColor(String name) {
        return namedColors.containsKey(name);
    }

    public static Collection<String> colorNames() {
        return namedColors.keySet();
    }
}
