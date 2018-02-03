package com.devsteady.onyx.yml;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class YMLIO {
    private FileConfiguration yml = new YamlConfiguration();

    private File file = null;

    private boolean force = false;

    public YMLIO(File file) throws IOException, InvalidConfigurationException {
        this.file = file;
        if (file.exists()) {
            yml.load(file);
        }
    }

    public boolean contains(String path) {
        return hasPath(path, "");
    }

    public boolean get(String path, boolean def) {
        boolean obj = yml.getBoolean(path, def);
        if (force) {
            obj = def;
        }
        set(path, Boolean.valueOf(obj));
        return obj;
    }

    public double get(String path, double def) {
        double obj = yml.getDouble(path, def);
        if (force) {
            obj = def;
        }
        set(path, Double.valueOf(obj));
        return obj;
    }

    public int get(String path, int def) {
        int obj = yml.getInt(path, def);
        if (force) {
            obj = def;
        }
        set(path, Integer.valueOf(obj));
        return obj;
    }

    public ItemStack get(String path, ItemStack def) {
        ItemStack obj = yml.getItemStack(path, def);
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public long get(String path, long def) {
        long obj = yml.getLong(path, def);
        if (force) {
            obj = def;
        }
        set(path, Long.valueOf(obj));
        return obj;
    }

    public Object get(String path, Object def) {
        Object obj = yml.get(path, def);
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public OfflinePlayer get(String path, OfflinePlayer def) {
        OfflinePlayer obj = yml.getOfflinePlayer(path, def);
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public String get(String path, String def) {
        String obj = yml.getString(path, def);
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public Map<String, Object> getAsMap(String path) {
        Map<String, Object> map = new HashMap<String, Object>();
        if ((yml.contains(path)) && (yml.isConfigurationSection(path))) {
            Set<?> keys = yml.getConfigurationSection(path).getKeys(false);
            if (keys.size() > 0) {
                Object[] key = keys.toArray();
                for (Object element : key) {
                    map.put(path + "." + element, getObject(path + "." + element));
                }
            }
        }
        return map;
    }

    public List<String> getAsFullPathList(String path) {
        List<String> list = new ArrayList<String>();
        if ((yml.contains(path)) && (yml.isConfigurationSection(path))) {
            Set<?> keys = yml.getConfigurationSection(path).getKeys(false);
            if (keys.size() > 0) {
                Object[] key = keys.toArray();
                for (Object element : key) {
                    list.add(path + "." + element);
                }
            }
        }
        return list;
    }

    public List<String> getAsPathList(String path) {
        List<String> list = new ArrayList<String>();
        if ((yml.contains(path)) && (yml.isConfigurationSection(path))) {
            Set<?> keys = yml.getConfigurationSection(path).getKeys(false);
            if (keys.size() > 0) {
                Object[] key = keys.toArray();
                for (Object element : key) {
                    list.add((String) element);
                }
            }
        }
        return list;
    }

    public List<Object> getAsValueList(String path) {
        List<Object> list = new ArrayList<Object>();
        if ((yml.contains(path)) && (yml.isConfigurationSection(path))) {
            Set<?> keys = yml.getConfigurationSection(path).getKeys(false);
            if (keys.size() > 0) {
                Object[] key = keys.toArray();
                for (Object element : key) {
                    list.add(getObject(path + "." + element));
                }
            }
        }
        return list;
    }

    public boolean getBoolean(String path) {
        return yml.getBoolean(path);
    }

    public List<Boolean> getBooleanList(String path) {
        return yml.getBooleanList(path);
    }

    public List<Boolean> getBooleanList(String path, List<Boolean> def) {
        List<Boolean> obj = def;
        if (yml.contains(path)) {
            obj = getBooleanList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public List<Byte> getByteList(String path) {
        return yml.getByteList(path);
    }

    public List<Byte> getByteList(String path, List<Byte> def) {
        List<Byte> obj = def;
        if (yml.contains(path)) {
            obj = getByteList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public List<Character> getCharacterList(String path) {
        return yml.getCharacterList(path);
    }

    public List<Character> getCharacterList(String path, List<Character> def) {
        List<Character> obj = def;
        if (yml.contains(path)) {
            obj = getCharacterList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public double getDouble(String path) {
        return yml.getDouble(path);
    }

    public List<Double> getDoubleList(String path) {
        return yml.getDoubleList(path);
    }

    public List<Double> getDoubleList(String path, List<Double> def) {
        List<Double> obj = def;
        if (yml.contains(path)) {
            obj = getDoubleList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getFileConfiguration() {
        return yml;
    }

    public List<Float> getFloatList(String path) {
        return yml.getFloatList(path);
    }

    public List<Float> getFloatList(String path, List<Float> def) {
        List<Float> obj = def;
        if (yml.contains(path)) {
            obj = getFloatList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public String getHeader() {
        return yml.options().header();
    }

    public String getHeader(String def) {
        String str = getHeader();
        if (str.isEmpty()) {
            str = def;
        }
        if (force) {
            str = def;
        }
        setHeader(str);
        return str;
    }

    public int getInt(String path) {
        return yml.getInt(path);
    }

    public List<Integer> getIntegerList(String path) {
        return yml.getIntegerList(path);
    }

    public List<Integer> getIntegerList(String path, List<Integer> def) {
        List<Integer> obj = def;
        if (yml.contains(path)) {
            obj = getIntegerList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public ItemStack getItemStack(String path) {
        return yml.getItemStack(path);
    }

    public List<?> getList(String path) {
        return yml.getList(path);
    }

    public List<?> getList(String path, List<?> def) {
        List<?> obj = def;
        if (yml.contains(path)) {
            obj = getList(path, def);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public long getLong(String path) {
        return yml.getLong(path);
    }

    public List<Long> getLongList(String path) {
        return yml.getLongList(path);
    }

    public List<Long> getLongList(String path, List<Long> def) {
        List<Long> obj = def;
        if (yml.contains(path)) {
            obj = getLongList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public List<Map<?, ?>> getMapList(String path) {
        return yml.getMapList(path);
    }

    public List<Map<?, ?>> getMapList(String path, List<Map<?, ?>> def) {
        List<Map<?, ?>> obj = def;
        if (yml.contains(path)) {
            obj = getMapList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public Object getObject(String path) {
        return yml.get(path);
    }

    public OfflinePlayer getOfflinePlayer(String path) {
        return yml.getOfflinePlayer(path);
    }

    public String getPath(String path) {
        return getPath(path, path, "");
    }

    private String getPath(String sPath, String sPath2, String path) {
        String pathPrefix = "";
        if (!path.isEmpty()) {
            pathPrefix = path + ".";
        }
        if (yml.isConfigurationSection(path)) {
            Set<?> key = yml.getConfigurationSection(path).getKeys(false);
            if (key.size() > 0) {
                Object[] paths = key.toArray();
                for (Object path2 : paths) {
                    String tmp = getPath(sPath, sPath2, pathPrefix + path2.toString());
                    if (tmp.equalsIgnoreCase(sPath)) {
                        return tmp;
                    }
                    sPath2 = tmp;
                }
            }
        } else {
            return path;
        }
        if (path.isEmpty()) {
            return sPath;
        }
        return sPath2;
    }

    public List<Short> getShortList(String path) {
        return yml.getShortList(path);
    }

    public List<Short> getShortList(String path, List<Short> def) {
        List<Short> obj = def;
        if (yml.contains(path)) {
            obj = getShortList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    public String getString(String path) {
        return yml.getString(path);
    }

    public List<String> getStringList(String path) {
        return yml.getStringList(path);
    }

    public List<String> getStringList(String path, List<String> def) {
        List<String> obj = def;
        if (yml.contains(path)) {
            obj = getStringList(path);
        }
        if (force) {
            obj = def;
        }
        set(path, obj);
        return obj;
    }

    private boolean hasPath(String sPath, String path) {
        String pathPrefix = "";
        if (!path.isEmpty()) {
            pathPrefix = path + ".";
        }
        if (yml.isConfigurationSection(path)) {
            Set<?> key = yml.getConfigurationSection(path).getKeys(false);
            if (key.size() > 0) {
                Object[] paths = key.toArray();
                for (Object path2 : paths) {
                    if (hasPath(sPath, pathPrefix + path2.toString())) {
                        return true;
                    }
                }
            }
        } else {
            return sPath.equalsIgnoreCase(path);
        }
        return false;
    }

    public void save() {
        try {
            yml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String path, Object obj) {
        yml.set(path, obj);
    }

    public void setFileConfiguration(File file, FileConfiguration fileConfig) {
        this.file = file;
        yml = fileConfig;
    }

    public void setForceSave(boolean force) {
        this.force = force;
    }

    public void setHeader(String str) {
        yml.options().header(str);
    }
}