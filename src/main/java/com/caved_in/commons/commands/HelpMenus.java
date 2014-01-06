package com.caved_in.commons.commands;

import com.caved_in.commons.menu.HelpScreen;
import org.bukkit.ChatColor;

import java.util.Map;

public class HelpMenus {

	enum ItemFormat {
		DOUBLE_DASH("<name> -- <desc>"),
		SINGLE_DASH("<name> - <desc>");

		private String formatting;
		private ItemFormat(String formatting) {
			this.formatting = formatting;
		}

		@Override
		public String toString() {
			return this.formatting;
		}
	}

	enum PageDisplay {
		DEFAULT("<name> (Page <page> of <maxpage>"),
		SHORTHAND("<name> (P.<page>/<maxpage>)");

		private String formatting;
		private PageDisplay(String formatting) {
			this.formatting = formatting;
		}

		@Override
		public String toString() {
			return this.formatting;
		}
	}

	private static HelpScreen generateHelpScreen(String menuName, PageDisplay pageDisplay, ItemFormat itemFormat, ChatColor flipColorEven, ChatColor flipColorOdd){
		HelpScreen helpScreen = new HelpScreen(menuName);
		helpScreen.setHeader(pageDisplay.toString());
		helpScreen.setFormat(itemFormat.toString());
		helpScreen.setFlipColor(flipColorEven,flipColorOdd);
		return helpScreen;
	}

	private static HelpScreen generateHelpScreen(String menuName, PageDisplay pageDisplay, ItemFormat itemFormat, ChatColor flipColorEven, ChatColor flipColorOdd, Map<String,String> helpItems){
		HelpScreen helpScreen = new HelpScreen(menuName);
		helpScreen.setHeader(pageDisplay.toString());
		helpScreen.setFormat(itemFormat.toString());
		helpScreen.setFlipColor(flipColorEven,flipColorOdd);
		for(Map.Entry<String, String> menuItem : helpItems.entrySet()) {
			helpScreen.setEntry(menuItem.getKey(),menuItem.getValue());
		}
		return helpScreen;
	}

}
