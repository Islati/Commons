package com.caved_in.commons.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class DataHandler {
	private String filePath = "";

	public DataHandler(String FilePath) {
		this.filePath = FilePath;
	}

	public DataHandler() {

	}

	public void overwriteFile(String data) {
		try {
			FileUtils.writeStringToFile(new File(this.filePath), data + "\r\n", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void overwriteFile(String filePath, String data) {
		try {
			FileUtils.writeStringToFile(new File(filePath), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void appendString(String data) {
		try {
			FileUtils.writeStringToFile(new File(this.filePath), data + "\r\n", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void appendString(String filePath, String data) {
		try {
			FileUtils.writeStringToFile(new File(filePath), data + "\r\n", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getContentsAsList() {
		try {
			return FileUtils.readLines(new File(this.filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<String> getContentsAsList(String filePath) {
		try {
			return FileUtils.readLines(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getStringBetween(String data, String start, String end) {
		return StringUtils.substringBetween(data, start, end);
	}

	public static String getStringBetween(String data, Tag searchTag) {
		return getStringBetween(data, searchTag.open(), searchTag.close());
	}

	public String getBetween(String start, String end) {
		return getStringBetween(getText(), start, end);
	}

	public String getBetween(Tag searchTag) {
		return getStringBetween(getText(), searchTag.getOpen(), searchTag.getClose());
	}

	public static List<String> getAllBetween(String data, String start, String end) {
		return Arrays.asList(StringUtils.substringsBetween(data, start, end));
	}

	public static List<String> getAllBetween(String Data, Tag searchTag) {
		return getAllBetween(Data, searchTag.open(), searchTag.close());
	}

	public List<String> getAllBetween(String Start, String End) {
		try {
			return Arrays.asList(StringUtils.substringsBetween(FileUtils.readFileToString(new File(this.filePath)), Start, End));
		} catch (IOException e) {
			return null;
		}
	}

	public boolean contains(String text) {
		return getContentsAsList().contains(text);
	}

	public static boolean contains(String filePath, String text) {
		return getText(filePath).contains(text);
	}

	public String getText() {
		try {
			return FileUtils.readFileToString(new File(this.filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getText(String filePath) {
		try {
			return FileUtils.readFileToString(new File(filePath));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
