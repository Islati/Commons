package com.caved_in.commons.menus.serverselection;

import com.caved_in.commons.config.itemmenu.XmlMenuItem;
import com.caved_in.commons.menus.XYMenuItemWrapper;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMenuGenerator {
	public static Map<XYMenuItemWrapper, ServerMenuItem> generateMenuItems(List<XmlMenuItem> items) {
		Map<XYMenuItemWrapper, ServerMenuItem> menuItems = new HashMap<XYMenuItemWrapper, ServerMenuItem>();
		for (XmlMenuItem menuItem : items) {
			String itemIconData = menuItem.getItemIconID();
			MaterialData materialData = null;
			if (itemIconData.contains(":")) {
				String[] itemSplit = itemIconData.split(":");
				if (itemSplit.length > 1) {
					int itemID = Integer.parseInt(itemSplit[0]);
					int itemDataValue = Integer.parseInt(itemSplit[1]);
					materialData = new MaterialData(Material.getMaterial(itemID), (byte) itemDataValue);
				} else {
					materialData = new MaterialData(Material.getMaterial(Integer.parseInt(itemSplit[0])));
				}
			} else {
				materialData = new MaterialData(Material.getMaterial(Integer.parseInt(itemIconData)));
			}
			ServerMenuItem serverMenuItem = new ServerMenuItem(menuItem.getItemName(), materialData, menuItem.getItemLore(), menuItem.getBungeeServer());
			menuItems.put(new XYMenuItemWrapper(menuItem.getX(), menuItem.getY()), serverMenuItem);
		}
		return menuItems;
	}

}
