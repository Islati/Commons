package com.caved_in.commons.config.ItemMenu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class XmlMenuItem
{
	@Attribute(name = "y")
	private int itemY = 0;
	
	@Attribute(name = "x")
	private int itemX = 0;

	@Attribute(name = "item_id")
	private int itemIconID = 0;

	@Attribute(name = "display_name")
	private String itemName = "";
	
	@Element(name = "command_to_execute")
	private String itemExecuteCommand = "";

	@ElementList(name = "lore",inline = true)
	private List<String> itemLore = new ArrayList<String>();

	public XmlMenuItem(@Attribute(name = "y")int itemY,
			@Attribute(name = "x")int itemX,
			@Attribute(name = "item_id") int itemIconID,
			@Attribute(name = "display_name") String itemName,
			@ElementList(name = "lore",inline = true) List<String> itemLore,
			@Element(name = "command_to_execute")String itemCommand)		
	{
		this.itemY = itemY;
		this.itemX = itemX;
		this.itemIconID = itemIconID;
		this.itemName = itemName;
		this.itemLore = itemLore;
		this.itemExecuteCommand = itemCommand;
	}
	
	public XmlMenuItem()
	{
		
	}

	public int getY()
	{
		return itemY;
	}

	public void setY(int y)
	{
		this.itemY = y;
	}
	
	public int getX()
	{
		return itemX;
	}
	
	public void setX(int x)
	{
		this.itemX = x;
	}

	public int getItemIconID()
	{
		return itemIconID;
	}

	public void setItemIconID(int itemIconID)
	{
		this.itemIconID = itemIconID;
	}

	public String getItemName()
	{
		return itemName;
	}

	public void setItemName(String itemName)
	{
		this.itemName = itemName;
	}

	public List<String> getItemLore()
	{
		return itemLore;
	}

	public void setItemLore(List<String> list)
	{
		this.itemLore = list;
	}
	
	public String getItemCommand()
	{
		return this.itemExecuteCommand;
	}
	
	public void setItemCommand(String itemCommand)
	{
		this.itemExecuteCommand = itemCommand;
	}
}
