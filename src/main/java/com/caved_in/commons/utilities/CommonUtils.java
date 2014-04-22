package com.caved_in.commons.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CommonUtils {
	private static final Random random = new Random();
	private static Map<String, ChatColor> prevColours = new HashMap<String, ChatColor>();

	private static boolean colouredConsole = true;

	private static final Pattern colourPat = Pattern.compile("(?<!&)&(?=[0-9a-fA-Fk-oK-OrR])");

	public static void setColouredConsole(boolean coloured) {
		colouredConsole = coloured;
	}

	public static void errorMessage(CommandSender sender, String string) {
		setPrevColour(sender.getName(), ChatColor.RED);
		message(sender, ChatColor.RED + string, Level.WARNING);
	}

	public static void statusMessage(CommandSender sender, String string) {
		setPrevColour(sender.getName(), ChatColor.AQUA);
		message(sender, ChatColor.AQUA + string, Level.INFO);
	}

	public static void alertMessage(CommandSender sender, String string) {
		setPrevColour(sender.getName(), ChatColor.YELLOW);
		message(sender, ChatColor.YELLOW + string, Level.INFO);
	}

	public static void generalMessage(CommandSender sender, String string) {
		setPrevColour(sender.getName(), ChatColor.WHITE);
		message(sender, string, Level.INFO);
	}

	public static void broadcastMessage(String string) {
		CommandSender sender = Bukkit.getConsoleSender();
		setPrevColour(sender.getName(), ChatColor.YELLOW);
		Bukkit.getServer().broadcastMessage(parseColourSpec(sender, "&4::&-" + string));
	}

	private static void setPrevColour(String name, ChatColor colour) {
		prevColours.put(name, colour);
	}

	private static ChatColor getPrevColour(String name) {
		if (!prevColours.containsKey(name)) {
			setPrevColour(name, ChatColor.WHITE);
		}
		return prevColours.get(name);
	}

	public static void rawMessage(CommandSender sender, String string) {
		boolean strip = ((sender instanceof ConsoleCommandSender)) && (!colouredConsole);
		for (String line : string.split("\\n")) {
			if (strip) {
				sender.sendMessage(ChatColor.stripColor(line));
			} else {
				sender.sendMessage(line);
			}
		}
	}

	private static void message(CommandSender sender, String string, Level level) {
		boolean strip = ((sender instanceof ConsoleCommandSender)) && (!colouredConsole);
		for (String line : string.split("\\n")) {
			if (strip) {
				LogUtils.log(level, ChatColor.stripColor(parseColourSpec(sender, line)));
			} else {
				sender.sendMessage(parseColourSpec(sender, line));
			}
		}
	}

	public static String formatLocation(Location loc) {
		return String.format("%d,%d,%d,%s", loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName());
	}

	public static Location parseLocation(String arglist) {
		return parseLocation(arglist, null);
	}

	public static Location parseLocation(String arglist, CommandSender sender) {
		String s = (sender instanceof Player) ? "" : ",worldname";
		String[] args = arglist.split(",");
		try {
			int x = Integer.parseInt(args[0]);
			int y = Integer.parseInt(args[1]);
			int z = Integer.parseInt(args[2]);
			World w = (sender instanceof Player) ? findWorld(args[3]) : ((Player) sender).getWorld();
			return new Location(w, x, y, z);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("You must specify all of x,y,z" + s + ".");
		} catch (NumberFormatException e) {
		}
		throw new IllegalArgumentException("Invalid number in " + arglist);
	}

	public static String parseColourSpec(String spec) {
		return parseColourSpec(null, spec);
	}

	public static String parseColourSpec(CommandSender sender, String spec) {
		String who = sender == null ? "*" : sender.getName();
		String res = colourPat.matcher(spec).replaceAll("�");
		return res.replace("&-", getPrevColour(who).toString()).replace("&&", "&");
	}

	public static String unParseColourSpec(String spec) {
		return spec.replaceAll("�", "&");
	}

	public static World findWorld(String worldName) {
		World w = Bukkit.getServer().getWorld(worldName);
		if (w != null) {
			return w;
		}
		throw new IllegalArgumentException("World " + worldName + " was not found on the server.");
	}

	public static List<String> splitQuotedString(String s) {
		List<String> matchList = new ArrayList();

		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher regexMatcher = regex.matcher(s);

		while (regexMatcher.find()) {
			if (regexMatcher.group(1) != null) {
				matchList.add(regexMatcher.group(1));
			} else if (regexMatcher.group(2) != null) {
				matchList.add(regexMatcher.group(2));
			} else {
				matchList.add(regexMatcher.group());
			}
		}

		return matchList;
	}

	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		List list = new ArrayList(c);
		Collections.sort(list);
		return list;
	}

	public static String[] listFilesinJAR(File jarFile, String path, String ext) throws IOException {
		ZipInputStream zip = new ZipInputStream(new FileInputStream(jarFile));
		ZipEntry ze = null;

		List<String> list = new ArrayList<String>();
		while ((ze = zip.getNextEntry()) != null) {
			String entryName = ze.getName();
			if ((entryName.startsWith(path)) && (ext != null) && (entryName.endsWith(ext))) {
				list.add(entryName);
			}
		}
		zip.close();

		return list.toArray(new String[list.size()]);
	}

	public static YamlConfiguration loadYamlUTF8(File file) throws InvalidConfigurationException, IOException {
		StringBuilder sb = new StringBuilder((int) file.length());

		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		char[] buf = new char[1024];
		int l;
		while ((l = in.read(buf, 0, buf.length)) > -1) {
			sb = sb.append(buf, 0, l);
		}
		in.close();

		YamlConfiguration yaml = new YamlConfiguration();
		yaml.loadFromString(sb.toString());

		return yaml;
	}

	/**
	 * Check against a random integer between 0 and 100 and return if it's less than or equal to
	 * the inputted percent
	 *
	 * @param chance
	 * @return
	 */
	public static boolean percentCheck(double chance) {
		return random.nextInt(101) <= chance;
	}

	public static UUID uuidFromUnparsedString(String id) {
		return UUID.fromString(String.format("%s-%s-%s-%s-%s", id.substring(0, 8), id.substring(8, 12), id.substring(12, 16), id.substring(16, 20), id.substring(20, 32)));
	}
}