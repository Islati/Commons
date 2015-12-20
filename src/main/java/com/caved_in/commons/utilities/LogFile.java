package com.caved_in.commons.utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LogFile {

    private boolean autosave;
    private String header;
    private File file;
    private List<String> log;

    public LogFile(File file) {

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //todo implement auto load.
        }

        this.file = file;
        this.log = new ArrayList<>();
        this.autosave = false;
        this.header = null;
    }

    public void addLines(List<String> lines) {
        this.log.addAll(lines);
        if (autosave) {
            save();
        }
    }

    public void addLine(String line) {
        this.log.add(line);
        if (autosave) {
            save();
        }
    }

    public void removeLine(int index) {
        this.log.remove(index);
        if (autosave) {
            save();
        }
    }

    public void setLine(int index, String line) {
        this.log.set(index, line);
        if (autosave) {
            save();
        }
    }

    public void insertLine(int index, String line) {
        this.log.add(index, line);
        if (autosave) {
            save();
        }
    }

    public void insertLines(int index, List<String> lines) {
        this.log.addAll(index, lines);
        if (autosave) {
            save();
        }
    }

    public void save() {
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            if (header != null) {
                bufferedWriter.write(header);
                bufferedWriter.newLine();
            }

            for (String l : log) {
                bufferedWriter.write(l);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            FileReader fileReader = new FileReader(file.getAbsolutePath());

            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine();

            log = new ArrayList<>();
            String line = "";

            while ((line = bufferedReader.readLine()) != null) {
                log.add(line);
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public List<String> getLog() {
        List<String> clonelog = new ArrayList<>();
        clonelog.addAll(log);
        return clonelog;
    }

    public void setAutoSave(boolean autosave) {
        this.autosave = autosave;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}