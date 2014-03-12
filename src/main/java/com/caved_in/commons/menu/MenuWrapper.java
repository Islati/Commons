package com.caved_in.commons.menu;

import me.xhawk87.PopupMenuAPI.MenuItem;
import me.xhawk87.PopupMenuAPI.PopupMenu;

import java.util.HashMap;
import java.util.Map;

public abstract class MenuWrapper {
	private String menuTitle = "";
	private Map<XYMenuItemWrapper, MenuItem> menuItems = new HashMap<XYMenuItemWrapper, MenuItem>();

	public MenuWrapper() {

	}

	public abstract PopupMenu getMenu();
}
