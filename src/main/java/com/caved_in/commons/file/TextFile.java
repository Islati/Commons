package com.caved_in.commons.file;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Used to read, write, and manipulate data within text files.
 */
public class TextFile {
    private String filePath = "";

    /**
     * Create a new textfile instance with the filepath as its base.
     *
     * @param FilePath path of the text file.
     */
    public TextFile(String FilePath) {
        this.filePath = FilePath;
    }

    public TextFile() {

    }

    /**
     * Overwrite the file with the given data.
     *
     * @param data new contents of the text file.
     */
    public void overwriteFile(String data) {
        try {
            FileUtils.writeStringToFile(new File(this.filePath), data + "\r\n", false);
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

    /**
     * Get a list of substrings found in the files contents.
     *
     * @param Start opening element to search for.
     * @param End   ending element to search for.
     * @return list of all the substrings found
     */
    public List<String> getAllBetween(String Start, String End) {
        try {
            return Arrays.asList(StringUtils.substringsBetween(FileUtils.readFileToString(new File(this.filePath)), Start, End));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Check whether or not the file contains specific text
     *
     * @param text text to check for within the file.
     * @return true if the text if found within the file contents.
     */
    public boolean contains(String text) {
        return getContentsAsList().contains(text);
    }

    /**
     * @return the contents of the text file
     */
    public String getText() {
        try {
            return FileUtils.readFileToString(new File(this.filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Get a list of all the data wrapped contained between the start and end elements.
     *
     * @param data  data to search through.
     * @param start opening element to search for.
     * @param end   ending element to search for.
     * @return all substrings matched between the start and end data.
     */
    public static List<String> getAllBetween(String data, String start, String end) {
        return Arrays.asList(StringUtils.substringsBetween(data, start, end));
    }

    /**
     * Get all the string-data wrapped by the given tag.
     *
     * @param data      data to search through.
     * @param searchTag tag that wraps elements
     * @return all the data wrapped by the given tag.
     */
    public static List<String> getAllBetween(String data, Tag searchTag) {
        return getAllBetween(data, searchTag.open(), searchTag.close());
    }

    /**
     * Check whether or not a file contains specific text.
     *
     * @param filePath path of the file to check.
     * @param text     text to check for within the file.
     * @return true if the file contains the text, false otherwise.
     */
    public static boolean contains(String filePath, String text) {
        return getText(filePath).contains(text);
    }

    /**
     * Get the contents of a text file in a(n) un-parsed string.
     *
     * @param filePath path of the file.
     * @return contents of the text file, or null if unable to read.
     */
    public static String getText(String filePath) {
        try {
            return FileUtils.readFileToString(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Overwrite a files contents with new data.
     *
     * @param filePath path of the file to manipulate.
     * @param data     new contents of the text file.
     */
    public static void overwriteFile(String filePath, String data) {
        try {
            FileUtils.writeStringToFile(new File(filePath), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pseudo-xml element. Used in conjunction with TextFile
     */
    public static class Tag {
        private String openTag = "";
        private String closeTag = "";

        public Tag(String tag) {
            this.openTag = "<" + tag + ">";
            this.closeTag = "</" + tag + ">";
        }

        public String getOpen() {
            return this.openTag;
        }

        public String getClose() {
            return this.closeTag;
        }

        public String open() {
            return this.openTag;
        }

        public String close() {
            return this.closeTag;
        }
    }

}
