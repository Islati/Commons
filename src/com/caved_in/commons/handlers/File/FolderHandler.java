package com.caved_in.commons.handlers.File;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderHandler
{
	
	private String folderLocation = "";
	
	public FolderHandler(String folderLocation)
	{
		this.folderLocation = folderLocation;
	}
	
	/**
	 * Gets all the files in the folderhandlers location
	 * @return
	 */
	public List<String> getFiles()
	{
		List<String> folderFiles = new ArrayList<String>();
		for(File folderFile : FileUtils.listFiles(new File(folderLocation), new String[] { "txt" }, false))
		{
			folderFiles.add(this.folderLocation + folderFile.getName());
		}
		return folderFiles;
	}
	
	/**
	 * Checks if the file exists, ignoring all case and looping through this.getFiles()
	 * @param fileName
	 * @return True if it does, false otherwise
	 */
	public boolean fileExists(String fileName)
	{
		for(String File : this.getFiles())
		{
			if (File.equalsIgnoreCase(folderLocation + fileName))
			{
				return true;
			}
		}
		return false;
		//return this.getFiles().contains(this.Folder + Name);
	}
	
	public String getExactFileName(String fileName)
	{
		for (String file : getFiles())
		{
			if (file.equalsIgnoreCase(folderLocation + fileName))
			{
				return file;
			}
		}
		return null;
	}

}
