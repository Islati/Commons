package com.caved_in.commons.yml.base;

import com.caved_in.commons.utilities.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Util {
    public static String readFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }

            return sb.toString().replace("\r", "");
        }
    }

    public static String readFile(File file, boolean clean) throws IOException {
        String fileContents = readFile(file);
        if (clean) {
            return fileContents.replace("\n", "");
        }
        return fileContents;
    }

    public static String[] readFileSplit(File file) throws IOException {
        return StringUtil.splitOnNewline(readFile(file));
    }
}
