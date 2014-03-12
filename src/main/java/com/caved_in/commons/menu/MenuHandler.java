package com.caved_in.commons.menu;

public class MenuHandler {
	public static int getRows(int ItemCount) {
		return ((int) Math.ceil(ItemCount / 9.0D));
	}
}
