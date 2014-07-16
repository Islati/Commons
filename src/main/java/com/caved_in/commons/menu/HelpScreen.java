package com.caved_in.commons.menu;

import com.caved_in.commons.utilities.StringUtil;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permissible;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that allows to create a Helpscreen
 *
 * @author Leo Schäfer
 */
public class HelpScreen {

	private HashMap<String, HelpScreenEntry> helpMenuMap = new HashMap<>();
	private final String menuName;
	private String menuHeader = "<name> Help (<page> / <maxpage>)";
	private String menuFormat = "<name> -> <desc>";
	private ChatColor oddItemColor = ChatColor.GRAY;
	private ChatColor evenItemColor = ChatColor.GRAY;

	public HelpScreen(String name) {
		menuName = name;
	}

	/**
	 * Allowed parameters: <name> = The entries name <desc> = The entries
	 * description
	 */
	public void setFormat(String format) {
		this.menuFormat = format;
	}

	/**
	 * Set the color that the entries will have The color will change for each
	 * entry
	 *
	 * @param oddItemColor  The first Color
	 * @param evenItemColor The second Color
	 */
	public void setFlipColor(ChatColor oddItemColor, ChatColor evenItemColor) {
		if (oddItemColor == null) {
			oddItemColor = ChatColor.GRAY;
		}

		if (evenItemColor == null) {
			evenItemColor = ChatColor.GRAY;
		}
		this.oddItemColor = oddItemColor;
		this.evenItemColor = evenItemColor;
	}

	/**
	 * Set the color that the entries will have The colorwill stay the same for
	 * all entries
	 *
	 * @param itemColor
	 */
	public void setSimpleColor(ChatColor itemColor) {
		setFlipColor(itemColor, itemColor);
	}

	/**
	 * Allowed parameters: <name> = The screens name <page> = The current page
	 * <maxpage> = The amount of pages that are aviable
	 */
	public void setHeader(String header) {
		if (header == null) {
			header = "";
		}
		this.menuHeader = header;
	}

	/**
	 * @param name        The name that will be displayed
	 * @param description The description
	 */
	public void setEntry(String name, String description) {
		HelpScreenEntry entry = new HelpScreenEntry(name, description);
		helpMenuMap.put(name.toLowerCase(), entry);
	}

	public void setEntry(String name, String description, String... permissions) {
		HelpScreenEntry entry = new HelpScreenEntry(name, description);
		entry.setPermissions(permissions);
		helpMenuMap.put(name.toLowerCase(), entry);
	}

	public HelpScreen addEntry(String name, String item) {
		helpMenuMap.put(name.toLowerCase(), new HelpScreenEntry(name, item));
		return this;
	}

	public HelpScreen addEntry(String name, String item, String... permissions) {
		HelpScreenEntry entry = new HelpScreenEntry(name, item);
		entry.setPermissions(permissions);
		helpMenuMap.put(name.toLowerCase(), entry);
		return this;
	}

	public HelpScreenEntry getEntry(String name) {
		return helpMenuMap.get(name.toLowerCase());
	}

	/**
	 * @param p The user
	 * @return The HelpEntries that the user is allowed to see
	 */
	public List<HelpScreenEntry> toSend(Permissible p) {
		ArrayList<HelpScreenEntry> toSend = new ArrayList<>();

		for (HelpScreenEntry e : helpMenuMap.values()) {
			if (e.isPermitted(p)) {
				toSend.add(e);
			}
		}

		return toSend;
	}

	/**
	 * @param s       The reciver of the helpscreen
	 * @param page    The page to send
	 * @param perPage The amount of entries per Page
	 */
	public void sendTo(CommandSender s, int page, int perPage) {
		//Create a list of lists (the pages of the help menu)
		List<List<HelpScreenEntry>> helpScreenPages = Lists.partition(toSend(s), perPage);
		//Get the page of entries we desire
		List<HelpScreenEntry> helpScreenEntries = helpScreenPages.get(page - 1);
		int entryAmount = helpScreenEntries.size();
		String[] messages = new String[entryAmount + 1];

		//Set the header of the message
		messages[0] = menuHeader.replaceAll("<name>", menuName).replaceAll("<page>", page + "").replaceAll("<maxpage>", helpScreenPages.size() + "");

		int i = 1;
		boolean col = false;
		//Loop through all the entries and format them accordingly
		for (HelpScreenEntry e : helpScreenEntries) {
			messages[i++] = ((col = !col) ? oddItemColor : evenItemColor) + e.fromFormat(menuFormat);
		}
		s.sendMessage(messages);
//
//		List<HelpScreenEntry> helpScreenEntries = toSend(s);
//		//The amount of elements to send
//		int entryAmount = helpScreenEntries.size();
//		//The amount of pages in the help menu
//		int maxPage = (int) (entryAmount / (float) perPage + 0.999);
//		//Position at which to start parsing the list
//		int subListStart = (page - 1) * perPage;
//		//Position to stop parsing the list
//		int subListEnd = subListStart + perPage;
//		if (subListStart >= entryAmount) {
//			subListStart = subListEnd = 0;
//		}
//		if (subListEnd > entryAmount) {
//			subListEnd = entryAmount;
//		}
//
//		if (subListStart == subListEnd || entryAmount == 0) {
//			helpScreenEntries = new ArrayList<>();
//		} else {
//			helpScreenEntries = helpScreenEntries.subList(subListStart, subListEnd);
//		}
//
//
//		String[] messages = new String[entryAmount + 1];
//
//		messages[0] = menuHeader.replaceAll("<name>", menuName).replaceAll("<page>", page + "").replaceAll("<maxpage>", maxPage + "");
//
//		int i = 1;
//
//		boolean col = false;
//		for (HelpScreenEntry e : helpScreenEntries) {
//			messages[i++] = ((col = !col) ? oddItemColor : evenItemColor) + e.fromFormat(menuFormat);
//		}
//		s.sendMessage(messages);
	}

	public class HelpScreenEntry {

		private final String NAME;
		private final String DESC;

		private String[] perms;

		public HelpScreenEntry(String name, String description) {
			this.NAME = name;
			this.DESC = description;
			this.perms = new String[0];
		}

		/**
		 * Set the permission needed to see the entry Only one of the
		 * permissions is needed
		 *
		 * @param perms The permissions
		 */
		public void setPermissions(String... perms) {
			this.perms = perms;
		}

		/**
		 * @param p The user
		 * @return If the user is allowed to see the entry
		 */
		public boolean isPermitted(Permissible p) {
			boolean b = true;
			for (String s : perms) {
				if (p.hasPermission(s)) {
					b = true;
					break;
				} else {
					b = false;
				}
			}
			return b;
		}

		/**
		 * @param format The format to use
		 * @return The phrased format
		 */
		public String fromFormat(String format) {
			return StringUtil.formatColorCodes(format.replaceAll("<name>", NAME).replaceAll("<desc>", DESC));
		}

		@Override
		public String toString() {
			return NAME + " -> " + DESC;
		}
	}

}