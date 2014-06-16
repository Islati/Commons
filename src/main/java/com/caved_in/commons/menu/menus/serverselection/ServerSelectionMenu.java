package com.caved_in.commons.menu.menus.serverselection;

import com.caved_in.commons.config.XmlMenuItem;
import com.caved_in.commons.menu.ItemMenu;
import com.caved_in.commons.menu.Menus;

import java.util.Collection;

public class ServerSelectionMenu extends ItemMenu {

	public ServerSelectionMenu(String title, Collection<XmlMenuItem> menuItems) {
		super(title, Menus.getRows(menuItems.size()));
		menuItems.forEach(item -> addMenuItem(item.getMenuItem(), item.getX(), item.getY()));
	}
}
