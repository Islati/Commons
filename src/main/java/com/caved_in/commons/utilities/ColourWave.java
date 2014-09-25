package com.caved_in.commons.utilities;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColourWave {

	private String str;
	private List<Colour> colours;

	public ColourWave(String str) {
		this.str = str;
		this.colours = new ArrayList<>();
	}

	public ChatColor getLastColour() {
		if (colours.size() <= 0) {
			return null;
		} else {
			return colours.get(0).getColour();
		}
	}

	private String insert(String str, String insert, int index) {
		if (index < 0) {
			index = 0;
		}

		String a = str.substring(0, index);
		String b = str.substring(index);
		return a + insert + b;
	}

	private void cleanColours() {
		int i = 0;
		while (i < colours.size()) {
			if (colours.get(i).getIndex() > str.length()) {
				colours.remove(i);
			} else {
				i++;
			}
		}
	}

	public String toString() {
		String str = "" + this.str;

		int before = 0;
		for (Colour c : colours) {
			if (c.getIndex() < 0) {
				before++;
			}
		}

		int add = 0;
		for (Colour colour : colours) {
			if (before > 0) {
				before--;
			}
			if (before <= 0) {
				str = insert(str, colour.getColour().toString(), colour.getIndex() + add);
				add += 2;
			}
		}

		return str;
	}

	public String next() {
		String str = "" + this.str;

		int before = 0;
		for (Colour c : colours) {
			if (c.getIndex() < 0) {
				before++;
			}
		}

		int add = 0;
		for (Colour colour : colours) {
			if (before > 0) {
				before--;
			}
			if (before <= 0) {
				str = insert(str, colour.getColour().toString(), colour.getIndex() + add);
				colour.next();
				add += 2;
			}
		}

		cleanColours();

		return str;
	}

	public void addColour(ChatColor colour) {
		Colour c = new Colour(colour, -str.length());
		colours.add(0, c);
	}

	private class Colour {
		private ChatColor colour;
		private int index;

		public Colour(ChatColor colour, int index) {
			this.colour = colour;
			this.index = index;
		}

		public ChatColor getColour() {
			return colour;
		}

		public int getIndex() {
			return index;
		}

		public void next() {
			index++;
		}
	}

}