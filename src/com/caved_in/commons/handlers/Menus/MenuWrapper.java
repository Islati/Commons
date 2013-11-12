package com.caved_in.commons.handlers.Menus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	
	protected int getRowCount(Set<XYMenuItemWrapper> menuItems)
	{
		int highestY = 1;
		for(XYMenuItemWrapper wrapper : menuItems)
		{
			int wrapperY = wrapper.getY();
			if (wrapperY > highestY)
			{
				wrapperY = (wrapperY > 9 ? 9 : wrapperY);
				highestY = wrapperY;
			}
		}
		return highestY;
	}
	
	public static int getRows(int ItemCount)
	{
		return ((int)Math.ceil(ItemCount / 9.0D));
	}
}
