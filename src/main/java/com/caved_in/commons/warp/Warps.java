package com.caved_in.commons.warp;

import com.caved_in.commons.Commons;
import com.caved_in.commons.file.Folder;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created By: TheGamersCave (Brandon)
 * Date: 30/01/14
 * Time: 9:39 PM
 */
public class Warps {
	private static Map<String, Warp> warps = new HashMap<String, Warp>();
	private static Serializer serializer = new Persister();

	public static boolean isWarp(String name) {
		return warps.containsKey(name);
	}

	public static void addWarp(Warp warp) {
		addWarp(warp, false);
	}

	public static void addWarp(Warp warp, boolean saveFile) {
		warps.put(warp.getName(), warp);
		if (saveFile) {
			saveWarp(warp);
		}
	}

	public static Warp getWarp(String warpName) {
		return warps.get(warpName);
	}

	public static Set<String> getWarps() {
		return warps.keySet();
	}

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

	public static void saveWarps() {
		for (Warp warp : warps.values()) {
			saveWarp(warp);
		}
	}

	public static void saveWarp(Warp warp) {
		File warpFile = new File(Commons.WARP_DATA_FOLDER + warp.getName() + ".xml");
		try {
			serializer.write(warp, warpFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
