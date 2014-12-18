package com.caved_in.commons.warp;

import com.caved_in.commons.Commons;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.*;

//TODO Move the warps class to be an instance object of Commons.
public class Warps {
	public static int pages = 0;
	private static boolean initialized = false;
	private static boolean updated = false;
	private static Map<String, Warp> warps = new HashMap<>();
	private static Serializer serializer = new Persister();

	private static Map<Integer, List<Warp>> warpPages = new HashMap<>();

	/**
	 * Check whether or not a warp with the given name exists.
	 *
	 * @param name name of the warp.
	 * @return true if a warp with the given name exists, false otherwise.
	 */
	public static boolean isWarp(String name) {
		for (String warpName : warps.keySet()) {
			if (name.equalsIgnoreCase(warpName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Add the warp to the map of warps without creating a file for it.
	 *
	 * @param warp warp to add
	 */
	public static void addWarp(Warp warp) {
		warps.put(warp.getName(), warp);
		setUpdated(true);
		Commons.getInstance().debug("Added warp " + warp.getName());
	}

	private static void initWarpPages() {
		List<List<Warp>> warpLists = Lists.partition(new ArrayList<>(warps.values()), 52);
		int i = 1;
		for (List<Warp> pages : warpLists) {
			warpPages.put(i, pages);
			i += 1;
		}
	}

	public static List<Warp> getWarpsPage(int page) {
		if (isUpdated()) {
			initWarpPages();
		}
		return warpPages.get(page);
	}

	public static int getWarpPagesCount() {
		return warpPages.size();
	}

	/**
	 * Add the warp to the map of warps, and optionally save its file so it's loaded on start-up / reload.
	 *
	 * @param warp     warp to add.
	 * @param saveFile whether or not to save the warp
	 */
	public static void addWarp(Warp warp, boolean saveFile) {
		addWarp(warp);
		if (saveFile) {
			saveWarp(warp);
		}
	}

	/**
	 * Get a warp by its name
	 *
	 * @param warpName name of the warp to retrieve
	 * @return if a warp with the given name exists it's returned, otherwise null.
	 */
	public static Warp getWarp(String warpName) {
		for (Warp warp : warps.values()) {
			if (warp.getName().equalsIgnoreCase(warpName)) {
				return warp;
			}
		}
		return null;
	}

	/**
	 * Get a set of all the warp names
	 *
	 * @return a set of all the warp names.
	 */
	public static Set<String> getWarpNames() {
		return warps.keySet();
	}

	/**
	 * Load all the warps into memory.
	 */
	public static void loadWarps() {
		Commons.getInstance().debug("Loading warps");
		Collection<File> warpFiles = FileUtils.listFiles(new File(Commons.WARP_DATA_FOLDER), null, false);
		//Loop through all the files and load warps
		for (File file : warpFiles) {
			try {
				addWarp(serializer.read(Warp.class, file));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Commons.getInstance().debug("Warps Loaded, initializing pages!");
		initWarpPages();
	}

	/**
	 * Save all the warps added to file.
	 */
	public static void saveWarps() {
		warps.values().forEach(com.caved_in.commons.warp.Warps::saveWarp);
	}

	/**
	 * Save the warp to file.
	 *
	 * @param warp the warp to save.
	 */
	public static void saveWarp(Warp warp) {
		File warpFile = new File(Commons.WARP_DATA_FOLDER + warp.getName() + ".xml");
		try {
			serializer.write(warp, warpFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<Warp> getWarps() {
		return Lists.newArrayList(warps.values());
	}

	public static int getWarpCount() {
		return warps.size();
	}

	public static boolean isUpdated() {
		return updated;
	}

	public static void setUpdated(boolean updated) {
		Warps.updated = updated;
	}
}
