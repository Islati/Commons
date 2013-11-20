package com.caved_in.commons.handlers.menus;

import java.util.HashMap;
import java.util.Map;

import me.xhawk87.PopupMenuAPI.MenuItem;
import me.xhawk87.PopupMenuAPI.PopupMenu;

public abstract class MenuWrapper
{
	private String menuTitle = "";
	private Map<XYMenuItemWrapper,MenuItem> menuItems = new HashMap<XYMenuItemWrapper,MenuItem>();
	
	public MenuWrapper()
	{
		
	}
	
	public abstract PopupMenu getMenu();
	
	public static int getRows(int ItemCount)
	{
		return ((int)Math.ceil(ItemCount / 9.0D));
	}
}
