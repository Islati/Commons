package com.caved_in.commons.menu;

import com.caved_in.commons.utilities.StringUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MenuItem {
	private static final MaterialData DEFAULT_ICON = new MaterialData(Material.PAPER);
	private ItemMenu menu;
	private int number;
	private MaterialData icon;
	private String text;
	private List<String> descriptions = new ArrayList<>();

	public MenuItem() {
		this("", DEFAULT_ICON);
	}

	public MenuItem(String text) {
		this(text, DEFAULT_ICON);
	}

	public MenuItem(String text, MaterialData icon) {
		this(text, icon, 1);
	}

	/**
	 * Create a new menu item with the given title, using the given MaterialData for its icon, and displaying the number given.
	 *
	 * @param text   the title text to display on mouse over
	 * @param icon   the material to use as its icon
	 * @param number the number to display on the item (must be greater than 1)
	 */
	public MenuItem(String text, MaterialData icon, int number) {
		this.text = StringUtil.formatColorCodes(text);
		this.icon = icon;
		this.number = number;
	}

	protected void addToMenu(ItemMenu menu) {
		this.menu = menu;
	}

	protected void removeFromMenu(ItemMenu menu) {
		if (this.menu == menu) {
			this.menu = null;
		}
	}

	public ItemMenu getMenu() {
		return menu;
	}

	public int getNumber() {
		return number;
	}

	public MaterialData getIcon() {
		return icon;
	}

	public void setIcon(MaterialData data) {
		icon = data;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = StringUtil.formatColorCodes(text);
	}

	public void setDescriptions(List<String> lines) {
		descriptions = StringUtil.formatColorCodes(lines);
	}

	public void setDescriptions(String... lines) {
		descriptions = Arrays.asList(lines);
	}

	public void addDescriptions(String line) {
		descriptions.add(StringUtil.formatColorCodes(line));
	}

	protected ItemStack getItemStack() {
		ItemStack itemStack = new ItemStack(getIcon().getItemType(), getNumber(), getIcon().getData());
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLore(descriptions);
		meta.setDisplayName(getText());
		itemStack.setItemMeta(meta);
		return itemStack;
//		return new ItemBuilder().withName(getText()).amount(number).withLore(descriptions).withMaterialData(getIcon()).toItemStack();
	}

	public abstract void onClick(Player player);
}
