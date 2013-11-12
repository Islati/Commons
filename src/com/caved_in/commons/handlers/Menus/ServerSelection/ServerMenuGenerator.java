package com.caved_in.commons.handlers.Menus.ServerSelection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.caved_in.commons.config.ItemMenu.XmlMenuItem;
import com.caved_in.commons.handlers.Menus.XYMenuItemWrapper;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class ServerMenuGenerator
{
	public static Map<XYMenuItemWrapper, ServerMenuItem> generateMenuItems(List<XmlMenuItem> items)
	{
		Map<XYMenuItemWrapper, ServerMenuItem> menuItems = new HashMap<XYMenuItemWrapper, ServerMenuItem>();
		for(XmlMenuItem menuItem : items)
		{
			ServerMenuItem serverMenuItem = new ServerMenuItem(menuItem.getItemName(), new MaterialData(Material.getMaterial(menuItem.getItemIconID())), menuItem.getItemLore(), menuItem.getItemCommand());
			menuItems.put(new XYMenuItemWrapper(menuItem.getX(),menuItem.getY()), serverMenuItem);
		}
		return menuItems;
	}
	
}
