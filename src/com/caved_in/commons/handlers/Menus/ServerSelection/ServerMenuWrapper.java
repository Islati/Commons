package com.caved_in.commons.handlers.Menus.ServerSelection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import me.xhawk87.PopupMenuAPI.PopupMenu;
import me.xhawk87.PopupMenuAPI.PopupMenuAPI;
import com.caved_in.commons.handlers.Menus.XYMenuItemWrapper;

public class ServerMenuWrapper
{
	private Map<XYMenuItemWrapper, ServerMenuItem> menuItems = new HashMap<XYMenuItemWrapper, ServerMenuItem>();
	private String menuName = "";
	
	
	public ServerMenuWrapper(String menuName, Map<XYMenuItemWrapper, ServerMenuItem> menuItems)
	{
		this.menuName = menuName;
		this.menuItems = menuItems;
	}
	
	public PopupMenu getMenu()
	{
		PopupMenu serverMenu = PopupMenuAPI.createMenu(this.menuName, getRowCount());
		for(Entry<XYMenuItemWrapper, ServerMenuItem> menuItem : this.menuItems.entrySet())
		{
			serverMenu.addMenuItem(menuItem.getValue(),menuItem.getKey().getX(), menuItem.getKey().getY());
		}
		
		serverMenu.setExitOnClickOutside(false);
		return serverMenu;
	}
	
	private int getRowCount()
	{
		int highestY = 1;
		for(XYMenuItemWrapper wrapper :this.menuItems.keySet())
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
}
