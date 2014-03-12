package com.caved_in.commons.menu.serverselection;

import com.caved_in.commons.menu.XYMenuItemWrapper;
import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ServerMenuWrapper {
	private Map<XYMenuItemWrapper, ServerMenuItem> menuItems = new HashMap<XYMenuItemWrapper, ServerMenuItem>();
	private String menuName = "";


	public ServerMenuWrapper(String menuName, Map<XYMenuItemWrapper, ServerMenuItem> menuItems) {
		this.menuName = menuName;
		this.menuItems = menuItems;
	}

	public PopupMenu getMenu() {
		PopupMenu serverMenu = PopupMenuAPI.createMenu(this.menuName, getRowCount());
		for (Entry<XYMenuItemWrapper, ServerMenuItem> menuItem : this.menuItems.entrySet()) {
			serverMenu.addMenuItem(menuItem.getValue(), menuItem.getKey().getX(), menuItem.getKey().getY());
		}

		serverMenu.setExitOnClickOutside(false);
		return serverMenu;
	}


	private int getRowCount() {
		int rowCount = 1;
		for (XYMenuItemWrapper wrapper : this.menuItems.keySet()) {
			int wrapperY = wrapper.getY() + 1;
			if (wrapperY > rowCount) {
				rowCount = wrapperY;
			}
		}
		return rowCount;
	}
}
