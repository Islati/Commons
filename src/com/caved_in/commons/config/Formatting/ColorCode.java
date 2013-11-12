package com.caved_in.commons.config.Formatting;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;

public enum ColorCode
{
	BLACK("tunnels.common.formatting.black", ChatColor.BLACK),
	DARK_BLUE("tunnels.common.formatting.darkblue", ChatColor.DARK_BLUE),
	BLUE("tunnels.common.formatting.blue", ChatColor.BLUE),
	GREEN("tunnels.common.formatting.green", ChatColor.GREEN),
	DARK_GREEN("tunnels.common.formatting.darkgreen", ChatColor.DARK_GREEN),
	GOLD("tunnels.common.formatting.gold", ChatColor.GOLD),
	GRAY("tunnels.common.formatting.gray", ChatColor.GRAY),
	DARK_GRAY("tunnels.common.formatting.darkgray", ChatColor.DARK_GRAY),
	RED("tunnels.common.formatting.red", ChatColor.RED),
	DARK_RED("tunnels.common.formatting.darkred", ChatColor.DARK_RED),
	LIGHT_PURPLE("tunnels.common.formatting.lightpurple", ChatColor.LIGHT_PURPLE),
	DARK_PURPLE("tunnels.common.formatting.darkpurple", ChatColor.DARK_PURPLE),
	YELLOW("tunnels.common.formatting.yellow", ChatColor.YELLOW),
	WHITE("tunnels.common.formatting.white", ChatColor.WHITE);

	private String colorPermission;
	private ChatColor color;

	private static Map<String, ChatColor> permissionColors = new HashMap<String, ChatColor>();

	static
	{
		for (ColorCode colorCode : EnumSet.allOf(ColorCode.class))
		{
			permissionColors.put(colorCode.colorPermission, colorCode.color);
		}
	}

	ColorCode(String permission, ChatColor color)
	{
		this.color = color;
		this.colorPermission = permission;
	}

	public String getPermission()
	{
		return this.colorPermission;
	}

	public ChatColor getColor()
	{
		return this.color;
	}

	public static ChatColor getColorForPermission(String permission)
	{
		return permissionColors.get(permission.toLowerCase());
	}
}
