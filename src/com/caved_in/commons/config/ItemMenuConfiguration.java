package com.caved_in.commons.config;

import com.caved_in.commons.config.ItemMenu.XmlMenuItem;
import org.simpleframework.xml.ElementList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemMenuConfiguration {
	@ElementList(name = "menuItems")
	private List<XmlMenuItem> menuItems = new ArrayList<XmlMenuItem>();

	public ItemMenuConfiguration(@ElementList(name = "menuItems", type = XmlMenuItem.class) List<XmlMenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	public ItemMenuConfiguration() {
		this.menuItems = new ArrayList<XmlMenuItem>();
		addMenuItem("Test", new String[]{"This is line one", "This is line two", "this is line 3", "this is line 4!"}, "0", 0, 1, "lobby");
	}

	public List<XmlMenuItem> getXmlItems() {
		return this.menuItems;
	}

	public void addMenuItem(String name, String[] Lore, String itemID, int x, int y, String bungeeServer) {
		//ServerMenuItem serverItem = new ServerMenuItem(name, new MaterialData(Material.getMaterial(itemID)), Arrays.asList(Lore), command);
		XmlMenuItem menuItem = new XmlMenuItem(y, x, itemID, name, Arrays.asList(Lore), bungeeServer);
		this.menuItems.add(menuItem);
	}

}
