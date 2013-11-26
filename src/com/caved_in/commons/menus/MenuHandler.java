package com.caved_in.commons.menus;

public class MenuHandler {
	public static int getRows(int ItemCount) {
		return ((int) Math.ceil(ItemCount / 9.0D));
	}
}
