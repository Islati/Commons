package com.caved_in.commons.block;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public enum Letter {
	LETTER_A('a', new boolean[][]
			{
					{false, true, false},
					{true, false, true},
					{true, false, true},
					{true, true, true},
					{true, false, true},}),

	LETTER_B('b', new boolean[][]
			{
					{true, true, false},
					{true, false, true},
					{true, true, true},
					{true, false, true},
					{true, true, false},}),

	LETTER_C('c', new boolean[][]
			{
					{false, true, true},
					{true, false, false},
					{true, false, false},
					{true, false, false},
					{false, true, true},}),

	LETTER_D('d', new boolean[][]
			{
					{true, true, false},
					{true, false, true},
					{true, false, true},
					{true, false, true},
					{true, true, false},}),

	LETTER_E('e', new boolean[][]
			{
					{true, true, true},
					{true, false, false},
					{true, true, false},
					{true, false, false},
					{true, true, true},}),

	LETTER_F('f', new boolean[][]
			{
					{true, true, true},
					{true, false, false},
					{true, true, false},
					{true, false, false},
					{true, false, false},}),

	LETTER_G('g', new boolean[][]
			{
					{false, true, true, false},
					{true, false, false, false},
					{true, false, true, true},
					{true, false, false, true},
					{false, true, true, true},}),

	LETTER_H('h', new boolean[][]
			{
					{true, false, true},
					{true, false, true},
					{true, true, true},
					{true, false, true},
					{true, false, true},}),

	LETTER_I('i', new boolean[][]
			{
					{true},
					{true},
					{true},
					{true},
					{true},}),

	LETTER_J('j', new boolean[][]
			{
					{false, false, true},
					{false, false, true},
					{false, false, true},
					{true, false, true},
					{true, true, true},}),

	LETTER_K('k', new boolean[][]
			{
					{true, false, false, true},
					{true, false, true, false},
					{true, true, false, false},
					{true, false, true, false},
					{true, false, false, true},}),

	LETTER_L('l', new boolean[][]
			{
					{true, false, false},
					{true, false, false},
					{true, false, false},
					{true, false, false},
					{true, true, true},}),

	LETTER_M('m', new boolean[][]
			{
					{true, true, true, true, true},
					{true, false, true, false, true},
					{true, false, true, false, true},
					{true, false, false, false, true},
					{true, false, false, false, true},}),

	LETTER_N('n', new boolean[][]
			{
					{true, false, false, true},
					{true, true, false, true},
					{true, false, true, true},
					{true, false, false, true},
					{true, false, false, true},}),

	LETTER_O('o', new boolean[][]
			{
					{false, true, false},
					{true, false, true},
					{true, false, true},
					{true, false, true},
					{false, true, false},}),

	LETTER_P('p', new boolean[][]
			{
					{true, true, false},
					{true, false, true},
					{true, true, false},
					{true, false, false},
					{true, false, false},}),

	LETTER_Q('q', new boolean[][]
			{
					{false, true, true, false},
					{true, false, false, true},
					{true, false, false, true},
					{true, false, true, true},
					{false, true, true, false},}),

	LETTER_R('r', new boolean[][]
			{
					{true, true, false},
					{true, false, true},
					{true, false, true},
					{true, true, false},
					{true, false, true},}),

	LETTER_S('s', new boolean[][]
			{
					{false, true, true},
					{true, false, false},
					{false, true, false},
					{false, false, true},
					{true, true, false},}),

	LETTER_T('t', new boolean[][]
			{
					{true, true, true},
					{false, true, false},
					{false, true, false},
					{false, true, false},
					{false, true, false},}),

	LETTER_U('u', new boolean[][]
			{
					{true, false, true},
					{true, false, true},
					{true, false, true},
					{true, false, true},
					{false, true, false},}),

	LETTER_V('v', new boolean[][]
			{
					{true, false, false, false, true},
					{true, false, false, false, true},
					{false, true, false, true, false},
					{false, true, false, true, false},
					{false, false, true, false, false},}),

	LETTER_W('w', new boolean[][]
			{
					{true, false, false, false, true},
					{true, false, false, false, true},
					{true, false, false, false, true},
					{true, false, true, false, true},
					{true, true, true, true, true},}),

	LETTER_X('x', new boolean[][]
			{
					{true, false, false, false, true},
					{false, true, false, true, false},
					{false, false, true, false, false},
					{false, true, false, true, false},
					{true, false, false, false, true},}),

	LETTER_Y('y', new boolean[][]
			{
					{true, false, false, true},
					{false, true, true, false},
					{false, false, true, false},
					{false, true, false, false},
					{true, false, false, false},}),

	LETTER_Z('z', new boolean[][]
			{
					{true, true, true, true, true},
					{false, false, false, true, false},
					{false, false, true, false, false},
					{false, true, false, false, false},
					{true, true, true, true, true},}),

	LETTER_0('0', new boolean[][]
			{
					{true, true, true},
					{true, false, true},
					{true, false, true},
					{true, false, true},
					{true, true, true},}),

	LETTER_1('1', new boolean[][]
			{
					{true, true, false},
					{false, true, false},
					{false, true, false},
					{false, true, false},
					{true, true, true},}),

	LETTER_2('2', new boolean[][]
			{
					{true, true, false},
					{false, false, true},
					{false, true, false},
					{true, false, false},
					{true, true, true},}),

	LETTER_3('3', new boolean[][]
			{
					{true, true, false},
					{false, false, true},
					{true, true, false},
					{false, false, true},
					{true, true, false},}),

	LETTER_4('4', new boolean[][]
			{
					{true, false, true},
					{true, false, true},
					{true, true, true},
					{false, false, true},
					{false, false, true},}),

	LETTER_5('5', new boolean[][]
			{
					{true, true, true},
					{true, false, false},
					{true, true, true},
					{false, false, true},
					{true, true, true},}),

	LETTER_6('6', new boolean[][]
			{
					{true, true, true},
					{true, false, false},
					{true, true, true},
					{true, false, true},
					{true, true, true},}),

	LETTER_7('7', new boolean[][]
			{
					{true, true, true},
					{false, false, true},
					{false, false, true},
					{false, false, true},
					{false, false, true},}),

	LETTER_8('8', new boolean[][]
			{
					{true, true, true},
					{true, false, true},
					{true, true, true},
					{true, false, true},
					{true, true, true},}),

	LETTER_9('9', new boolean[][]
			{
					{true, true, true},
					{true, false, true},
					{true, true, true},
					{false, false, true},
					{true, true, true},}),

	LETTER_DOT('.', new boolean[][]
			{
					{true},}),

	LETTER_UNDERSCORE('_', new boolean[][]
			{
					{true, true, true},}),

	LETTER_SPACE(' ', new boolean[][]
			{
					{false, false, false},}),

	LETTER_PERCENT('%', new boolean[][]
			{
					{true, false, false, false, true},
					{false, false, false, true, false},
					{false, false, true, false, false},
					{false, true, false, false, false},
					{true, false, false, false, true},}),

	LETTER_UP_ARROW('^', new boolean[][]
			{
					{false, false, true, false, false},
					{false, true, false, true, false},
					{true, false, false, false, true},
					{false, false, false, false, false},
					{false, false, false, false, false},}),

	LETTER_LEFT_ARROW('<', new boolean[][]
			{
					{false, false, true},
					{false, true, false},
					{true, false, false},
					{false, true, false},
					{false, false, true},}),

	LETTER_RIGHT_ARROW('>', new boolean[][]
			{
					{true, false, false},
					{false, true, false},
					{false, false, true},
					{false, true, false},
					{true, false, false},}),

	LETTER_AMPERSAND('*', new boolean[][]
			{
					{true, false, true, false, true},
					{false, true, true, true, false},
					{true, true, true, true, true},
					{false, true, true, true, false},
					{true, false, true, false, true},}),

	LETTER_HASHTAG('#', new boolean[][]
			{
					{false, true, false, true, false},
					{true, true, true, true, true},
					{false, true, false, true, false},
					{true, true, true, true, true},
					{false, true, false, true, false},}),

	LETTER_COMMA(',', new boolean[][]
			{
					{true},
					{true},}),

	LETTER_COLON(':', new boolean[][]
			{
					{true},
					{false},
					{false},
					{false},
					{true},}),

	LETTER_DASH('-', new boolean[][]
			{
					{true, true, true},
					{false, false, false},
					{false, false, false},}),

	LETTER_PLUS('+', new boolean[][]
			{
					{false, false, true, false, false},
					{false, false, true, false, false},
					{true, true, true, true, true},
					{false, false, true, false, false},
					{false, false, true, false, false},}),

	LETTER_MINUS('-', new boolean[][]
			{
					{true, true, true, true, true},
					{false, false, false, false, false},
					{false, false, false, false, false},}),

	LETTER_EQUAL('=', new boolean[][]
			{
					{true, true, true, true, true},
					{false, false, false, false, false},
					{true, true, true, true, true},
					{false, false, false, false, false},}),

	LETTER_LEFT_ROUND_BRACKET('(', new boolean[][]
			{
					{false, true},
					{true, false},
					{true, false},
					{true, false},
					{false, true},}),

	LETTER_RIGHT_ROUND_BRACKET(')', new boolean[][]
			{
					{true, false},
					{false, true},
					{false, true},
					{false, true},
					{true, false},}),

	LETTER_LEFT_SQUARE_BRACKET('[', new boolean[][]
			{
					{true, true},
					{true, false},
					{true, false},
					{true, false},
					{true, true},}),

	LETTER_RIGHT_SQUARE_BRACKET(']', new boolean[][]
			{
					{true, true},
					{false, true},
					{false, true},
					{false, true},
					{true, true},}),

	LETTER_QUESTION('?', new boolean[][]
			{
					{true, true, true},
					{false, false, true},
					{false, true, true},
					{false, false, false},
					{false, true, false},}),
	LETTER_EXCLAMATION('!', new boolean[][]{
			{false, true, false},
			{false, true, false},
			{false, true, false},
			{false, false, false},
			{false, true, false}
	});

	private char character;
	private int height;
	private int width;
	private boolean[][] blocks;

	private Letter(char character, boolean[][] blocks) {
		this.character = character;

		this.blocks = blocks;

		this.height = blocks.length;
		this.width = blocks[0].length;

		boolean[][] reversed = new boolean[height][width];
		for (int i = 0; i < height; i++) {
			reversed[(height - i) - 1] = blocks[i];
		}
		this.blocks = reversed;
	}

	public static int strHeight() {
		return 5;
	}

	public static int strHeight(String[] str) {
		return (strHeight() + 1) * str.length - 1;
	}

	public static int strWidth(String str) {
		int w = 0;
		List<Letter> letters = fromString(str);
		for (Letter l : letters) {
			w += l.getWidth() + 1;
		}
		return (w > 0 ? w - 1 : w);
	}

	public static int strWidth(String[] str) {
		int width = 0;
		for (String s : str) {
			if (strWidth(s) > width) {
				width = strWidth(s);
			}
		}

		return width;
	}

	public static void centreString(String text, Material type, byte data, Location centre,
									Direction dir) {
		int width = strWidth(text);
		Location start = centre.subtract((width / 2) * dir.getX(), 0, (width / 2)
				* dir.getZ());
		drawString(text, type, data, start, dir);
	}

	public static void centreString(String[] text, Material type, byte data, Location centre,
									Direction dir) {
		int height = 0;
		for (String s : text) {
			height += 1;
			height += strHeight();
			centreString(s, type, data, centre.clone().subtract(0, height, 0), dir);
		}
	}

	public static void drawString(String str, Material type, byte data, Location loc, Direction dir) {
		List<Letter> letters = fromString(str);
		for (Letter l : letters) {
			l.draw(type, data, loc.clone(), dir);
			loc = loc.add((l.getWidth() + 1) * dir.getX(), 0, (l.getWidth() + 1)
					* dir.getZ());
		}
	}

	public static void drawString(String[] text, Material type, byte data, Location loc, Direction dir) {
		int height = 0;
		for (String s : text) {
			height += 1;
			height += strHeight();
			centreString(s, type, data, loc.clone().subtract(0, height, 0), dir);
		}
	}

	public static Letter fromCharacter(char character) {
		for (Letter l : Letter.values()) {
			if (("" + l.character).equalsIgnoreCase("" + character)) {
				return l;
			}
		}
		return null;
	}

	public static List<Letter> fromString(String string) {
		List<Letter> letters = new ArrayList<>();
		for (char character : string.toCharArray()) {
			Letter l = fromCharacter(character);
			if (l != null) {
				letters.add(l);
			}
		}
		return letters;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public char getCharacter() {
		return character;
	}

	@SuppressWarnings("deprecation")
	public void draw(Material type, byte data, Location loc, Direction dir) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Location l = loc.clone().add(x * dir.getX(), y, x * dir.getZ());
				if (l.getBlock() != null && blocks[y][x]) {
					l.getBlock().setType(type);
					l.getBlock().setData(data);
				}
			}
		}
	}
}