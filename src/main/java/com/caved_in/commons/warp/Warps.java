package com.caved_in.commons.warp;

import com.caved_in.commons.Commons;
import com.caved_in.commons.file.Folder;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Warps {
	private static Map<String, Warp> warps = new HashMap<String, Warp>();
	private static Serializer serializer = new Persister();

	/**
	 * Check whether or not a warp with the given name exists.
	 *
	 * @param name name of the warp.
	 * @return true if a warp with the given name exists, false otherwise.
	 */
	public static boolean isWarp(String name) {
		return warps.containsKey(name);
	}

	/**
	 * Add the warp to the map of warps without creating a file for it.
	 *
	 * @param warp warp to add
	 */
	public static void addWarp(Warp warp) {
		warps.put(warp.getName(), warp);
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
		return warps.get(warpName);
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
		Folder folderHandler = new Folder(Commons.WARP_DATA_FOLDER);
		try {
			//Loop through all the files
			for (String fileName : folderHandler.getFiles()) {
				//Load the warp into an object
				Warp warp = serializer.read(Warp.class, new File(fileName));
				//Add the warp to the list, but don't save the file
				addWarp(warp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}
