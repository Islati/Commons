package com.caved_in.commons.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;

public class FolderHandler {

	private String folderLocation = "";
	private Map<String, String> filesInFolder = new HashMap<>();

	public FolderHandler(String folderLocation) {
		this.folderLocation = folderLocation;
		initData();
	}

	private void initData() {
		for (File folderFile : FileUtils.listFiles(new File(folderLocation), new String[]{"txt"}, false)) {
			filesInFolder.put(folderFile.getName(), folderLocation + folderFile.getName());
		}
	}

	/**
	 * Gets all the files in the folderhandlers location
	 *
	 * @return
	 */
	public List<String> getFiles() {
		return new ArrayList<String>(filesInFolder.values());
	}

	/**
	 * Checks if the file exists, ignoring all case
	 * @param fileName
	 * @return True if it does, false otherwise
	 */
	public boolean fileExists(String fileName) {
		return filesInFolder.containsValue(folderLocation + fileName);
	}

}
