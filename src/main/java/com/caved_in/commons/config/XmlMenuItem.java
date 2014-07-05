package com.caved_in.commons.config;

import com.caved_in.commons.exceptions.InvalidMaterialName;
import com.caved_in.commons.item.Items;
import com.caved_in.commons.menu.menus.serverselection.ServerMenuItem;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "menu-item")
public class XmlMenuItem {
	@Attribute(name = "y")
	private int itemY = 0;

	@Attribute(name = "x")
	private int itemX = 0;

	@Attribute(name = "item-id")
	private String itemIconID = "0";

	@Attribute(name = "display-name")
	private String itemName = "";

	@Element(name = "server-to-join")
	private String serverToJoin = "";

	@ElementList(name = "lore", inline = true, required = false)
	private List<String> itemLore = new ArrayList<>();

	private ServerMenuItem menuItem;

	public XmlMenuItem(@Attribute(name = "y") int itemY,
					   @Attribute(name = "x") int itemX,
					   @Attribute(name = "item-id") String itemIconID,
					   @Attribute(name = "display-name") String itemName,
					   @ElementList(name = "lore", inline = true, required = false) List<String> itemLore,
					   @Element(name = "server-to-join") String bungeeServer) {
		this.itemY = itemY;
		this.itemX = itemX;
		this.itemIconID = itemIconID;
		this.itemName = itemName;
		this.itemLore = itemLore;
		this.serverToJoin = bungeeServer;
	}

	public XmlMenuItem() {
		itemIconID = String.valueOf(Material.DIAMOND_SWORD.getId());
		itemName = "Example Item";
		serverToJoin = "lobby";
	}

	public int getY() {
		return itemY;
	}

	public void setY(int y) {
		this.itemY = y;
	}

	public int getX() {
		return itemX;
	}

	public void setX(int x) {
		this.itemX = x;
	}

	public String getItemIconID() {
		return itemIconID;
	}

	public void setItemIconID(String itemIconID) {
		this.itemIconID = itemIconID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public List<String> getItemLore() {
		return itemLore;
	}

	public void setItemLore(List<String> list) {
		this.itemLore = list;
	}

	public String getBungeeServer() {
		return this.serverToJoin;
	}

	public void setBungeeServer(String itemCommand) {
		this.serverToJoin = itemCommand;
	}

	public ServerMenuItem getMenuItem() {
		if (menuItem == null) {
			String itemIconData = getItemIconID();
			MaterialData materialData = null;
			try {
				materialData = Items.getMaterialDataFromString(itemIconData);
			} catch (InvalidMaterialName invalidMaterialName) {
				invalidMaterialName.printStackTrace();
				return null;
			}
			menuItem = new ServerMenuItem(getItemName(), materialData, getItemLore(), getBungeeServer());
		}
		return menuItem;
	}
}
